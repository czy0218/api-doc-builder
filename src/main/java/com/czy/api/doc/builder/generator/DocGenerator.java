package com.czy.api.doc.builder.generator;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:41
 * @Version 1.0
 */
public interface DocGenerator {

    /**
     * 生成Vo文档
     *
     * @throws IOException
     * @throws TemplateException
     */
    void generateVoDoc() throws IOException, TemplateException;

    /**
     * 生成Parameter文档
     *
     * @throws IOException
     * @throws TemplateException
     */
    void generateParameterDoc() throws IOException, TemplateException;

    /**
     * 生成API文档
     *
     * @throws IOException
     * @throws TemplateException
     */
    void generateApiDoc() throws IOException, TemplateException;
}
