package com.rpc.pojo;

public class ClassInfo {
	private static final long serialVersionUID = -8970942815543515064L;  
    
    private String className;//����  
    private String methodName;//��������  
    private Class<?>[] types;//��������    
    private Object[] objects;//�����б�    
    public String getClassName() {  
        return className;  
    }  
    public void setClassName(String className) {  
        this.className = className;  
    }  
    public String getMethodName() {  
        return methodName;  
    }  
    public void setMethodName(String methodName) {  
        this.methodName = methodName;  
    }  
    public Class<?>[] getTypes() {  
        return types;  
    }  
    public void setTypes(Class<?>[] types) {  
        this.types = types;  
    }  
    public Object[] getObjects() {  
        return objects;  
    }  
    public void setObjects(Object[] objects) {  
        this.objects = objects;  
    }  
}
