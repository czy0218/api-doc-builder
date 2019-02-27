package com.czy.api.doc.builder.entity;

import com.czy.api.doc.builder.model.ModelClassModel;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:38
 * @Version 1.0
 */
public class ParameterTypeEntity {

    public ParameterTypeEntity(String parameterType, ModelClassModel parameter) {
        this.parameterType = parameterType;
        this.parameter = parameter;
    }

    private String parameterType;
    private ModelClassModel parameter;

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public ModelClassModel getParameter() {
        return parameter;
    }

    public void setParameter(ModelClassModel parameter) {
        this.parameter = parameter;
    }
}
