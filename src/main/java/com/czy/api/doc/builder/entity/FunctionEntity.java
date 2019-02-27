package com.czy.api.doc.builder.entity;

import java.util.List;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:37
 * @Version 1.0
 */
public class FunctionEntity {

    public FunctionEntity() {
        this.name = "";
        this.remark = "";
        this.author = "";
        this.type = "";
        this.desc = "";
    }

    private String name;
    private String remark;
    private String author;
    private String type;

    private ParameterTypeEntity returnType;
    private List<ParameterTypeEntity> params;

    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParameterTypeEntity getReturnType() {
        return returnType;
    }

    public void setReturnType(ParameterTypeEntity returnType) {
        this.returnType = returnType;
    }


    public List<ParameterTypeEntity> getParams() {
        return params;
    }

    public void setParams(List<ParameterTypeEntity> params) {
        this.params = params;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
