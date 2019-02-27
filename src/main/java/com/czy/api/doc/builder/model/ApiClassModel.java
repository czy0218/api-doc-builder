package com.czy.api.doc.builder.model;

import com.czy.api.doc.builder.entity.FunctionEntity;

import java.util.List;
import java.util.Properties;

/**
 * @Author: czy
 * @Date: 2019/2/26 16:42
 * @Version 1.0
 */
public class ApiClassModel {
    private String className;
    private String packageName;
    private List<FunctionEntity> functions;
    private Properties properties;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<FunctionEntity> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionEntity> functions) {
        this.functions = functions;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
