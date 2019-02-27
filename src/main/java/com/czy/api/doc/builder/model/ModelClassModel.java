package com.czy.api.doc.builder.model;

import com.czy.api.doc.builder.entity.FieldEntity;

import java.util.List;
import java.util.Properties;

/**
 * @Author: czy
 * @Date: 2019/2/26 16:43
 * @Version 1.0
 */
public class ModelClassModel {
    private String className;
    private List<FieldEntity> fields;
    private Properties properties;
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(List<FieldEntity> fields) {
        this.fields = fields;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "ModelClassModel{" +
                "className='" + className + '\'' +
                ", fields=" + fields +
                '}';
    }
}
