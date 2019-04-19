package com.example.ioc;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ApplicationContext {

    private String filePath;

    private Map<String,File> fileClassSources = new HashMap<>();

    private BeanFatory beanFatory;

    public ApplicationContext(Class clz) {
        String packPath = clz.getPackage().getName();
        filePath = packPath.replace('.', '/');
        File rootDir = fileRootPath();
        getResources(rootDir,packPath);
        beanFatory = new BeanFatory(fileClassSources);
    }

    private File fileRootPath(){
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(this.filePath);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                return new File(url.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void getResources(File file, String packPath){
        if(file.exists() && file != null) {
            if(file.isDirectory()){
                File[] childFiles = file.listFiles();
                for (int i = 0; i < childFiles.length; i++) {
                    File childFile = childFiles[i];
                    getResources(childFile,packPath+"."+childFile.getName());
                }
            }else{
                String fileName = file.getName();
                if(fileName.indexOf(".class") >= 0){
                    fileClassSources.put(packPath.replaceAll(".class", ""),file);
                }else{
                    return;
                }
            }
        }
        return;
    }

    public <T> T getBean(Class<T> clz){
        return (T)getBean(clz.getName());
    }

    public Object getBean(String clz){
        return beanFatory.getBeanMap().get(clz);
    }
}
