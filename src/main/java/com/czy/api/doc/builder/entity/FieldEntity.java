package com.czy.api.doc.builder.entity;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:36
 * @Version 1.0
 */
public class FieldEntity {

    public FieldEntity() {
        this.fieldType = "";
        this.fieldName = "";
        this.remark = "";
        this.insertNotNull = false;
        this.updateNotNull = false;
    }

    private String fieldType;
    private String fieldName;
    private String remark;
    private Boolean insertNotNull;
    private Boolean updateNotNull;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getInsertNotNull() {
        return insertNotNull;
    }

    public void setInsertNotNull(Boolean insertNotNull) {
        this.insertNotNull = insertNotNull;
    }

    public Boolean getUpdateNotNull() {
        return updateNotNull;
    }

    public void setUpdateNotNull(Boolean updateNotNull) {
        this.updateNotNull = updateNotNull;
    }

    @Override
    public String toString() {
        return "FieldEntity{" +
                "fieldType='" + fieldType + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", remark='" + remark + '\'' +
                ", insertNotNull=" + insertNotNull +
                ", updateNotNull=" + updateNotNull +
                '}';
    }
}
