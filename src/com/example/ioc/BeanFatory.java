package com.example.ioc;

import com.example.ioc.beanenum.AutoWiter;
import com.example.ioc.beanenum.Bean;
import com.example.ioc.beanenum.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanFatory {

    private Map<String,File> fileClassSources;

    private Map<String, Object> beanMap;

    private Map<String, String> interfaceBeanMap;

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    public BeanFatory(Map<String, File> fileClassSources) {
        this.fileClassSources = fileClassSources;
        beanMap = new HashMap<>();
        interfaceBeanMap = new HashMap<>();
        loadClass();
    }

    public void loadClass(){
        for(String packPath : fileClassSources.keySet()){
//            IOCClassload iocClassload = new IOCClassload();
//            File classFile = fileClassSources.get(packPath);
//            FileInputStream in = null;
//            byte[] result = new byte[5000];
            try{
//                in = new FileInputStream(classFile);
//                int count = in.read(result);
                Class clz = loadClass(packPath);
//                Class clz = iocClassload.defineMyClass(packPath, result, 0, count);
                if(clz == null) continue;
                Class[] intfces = clz.getInterfaces();
                for (int i = 0; i < intfces.length; i++) {
                    interfaceBeanMap.put(intfces[i].getName(), clz.getName());
                }
                Field[] fields = clz.getDeclaredFields();
                Object obj = findAnnoField(fields,clz);
                beanMap.put(clz.getName(), obj);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Class loadClass(String className){
        try{
//            Class cls = Class.forName(className);
            Class cls = classLoader.loadClass(className);
            if(beanMap.get(cls.getName()) != null) return null;
            if(isClassLoad(cls)) return null;
            return cls;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isClassLoad(Class clz){
        Annotation beanAnno = clz.getAnnotation(Bean.class);
        Annotation ServiceAnno = clz.getAnnotation(Service.class);
        return beanAnno == null && ServiceAnno == null;
    }

    private boolean isFieldLoad(Field field){
        Annotation autoWriterAnno = field.getAnnotation(AutoWiter.class);
        return autoWriterAnno == null;
    }

    private Object findAnnoField(Field[] fields, Class clz) throws Exception{
        Object obj = clz.newInstance();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(isFieldLoad(field)) continue;
            String className = field.getType().getName();
            Object bean = getBeanMap(className);
            if(bean == null){
                Class clzLoad = loadClass(className);
                bean = findAnnoField(clzLoad.getDeclaredFields(),clzLoad);
            }
            field.setAccessible(true);
            field.set(obj, bean);
        }
        return obj;
    }

    public Object getBeanMap(String className) {
        try{
            return getBeanMap(Class.forName(className));
        }catch (Exception e){
            return null;
        }
    }

    public Object getBeanMap(Class clz) {
        if(clz.isInterface()){
            String implClassName = interfaceBeanMap.get(clz.getName());
            return beanMap.get(implClassName);
        }
        return beanMap.get(clz.getName());
    }
}
