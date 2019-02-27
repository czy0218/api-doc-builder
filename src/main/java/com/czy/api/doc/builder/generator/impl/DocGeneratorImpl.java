package com.czy.api.doc.builder.generator.impl;

import com.czy.api.doc.builder.factory.FreemarkerConfigurationFactory;
import com.czy.api.doc.builder.generator.DocGenerator;
import com.czy.api.doc.builder.model.ApiClassModel;
import com.czy.api.doc.builder.model.ModelClassModel;
import com.czy.api.doc.builder.utils.DirectoryUtil;
import com.czy.api.doc.builder.utils.GeneratorUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.*;
import java.util.*;
import java.util.function.Function;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:40
 * @Version 1.0
 */
@Component("docGenerator")
public class DocGeneratorImpl implements DocGenerator {

    @Autowired
    String modelPath;

    @Autowired
    String voPath;

    @Autowired
    String parameterPath;

    @Autowired
    String apiPath;

    @Autowired
    String apiSubPackages;

    @Autowired
    String templatePath;

    @Autowired
    String outputPath;

    @Autowired
    GeneratorUtil generatorUtil;

    /**
     * 生成Model文档
     *
     * @throws IOException
     * @throws TemplateException
     */
    public void generateModelDoc() throws IOException, TemplateException {

        createModelDoc(modelPath, "models.html",
                fileName -> fileName.endsWith(".java") && !fileName.endsWith("Example.java"),
                file -> generatorUtil.getModelClassModel(file));
    }

    /**
     * 生成Vo文档
     *
     * @throws IOException
     * @throws TemplateException
     */
    public void generateVoDoc() throws IOException, TemplateException {

        createModelDoc(voPath, "vos.html",
                fileName -> fileName.endsWith(".java"),
                file -> generatorUtil.getVoClassModel(file));
    }

    /**
     * 生成Parameter文档
     *
     * @throws IOException
     * @throws TemplateException
     */
    public void generateParameterDoc() throws IOException, TemplateException {

        createModelDoc(parameterPath, "parameters.html",
                fileName -> fileName.endsWith("Parameter.java"),
                file -> generatorUtil.getParameterClassModel(file));
    }

    /**
     * 生成API文档
     *
     * @throws IOException
     * @throws TemplateException
     */
    public void generateApiDoc() throws IOException, TemplateException {

        Template template = getTemplate("api.ftl");
        for (String subPackage : apiSubPackages.split(",")) {
            String outFilePath = outputPath + "api/" + subPackage + "/";
            DirectoryUtil.createIfNotExists(outFilePath);
            File[] files = DirectoryUtil.getAllFiles(apiPath + subPackage);

            if (files != null) {
                List<ApiClassModel> apiEntities = new ArrayList<>();
                for (File file : files) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".java")) {

                        ApiClassModel entity = generatorUtil.getApiClassModel(file);
                        process(outFilePath + entity.getClassName() + ".html", template, entity);
                        apiEntities.add(entity);
                    }
                }
            }
        }

    }

    /**
     * 生成实体类文档
     *
     * @param path
     * @param outFileName
     * @param nameMatch
     * @param getEntity
     * @throws IOException
     * @throws TemplateException
     */
    private void createModelDoc(String path, String outFileName,
                                Function<String, Boolean> nameMatch,
                                Function<File, ModelClassModel> getEntity) throws IOException, TemplateException {

        Template template = getTemplate("models.ftl");

        List<String> classNameList = new ArrayList<>();
        List<ModelClassModel> modelList = new ArrayList<>();

        File[] files = DirectoryUtil.getAllFiles(path);
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();

                if (nameMatch.apply(fileName)) {

                    String className = fileName.substring(0, fileName.length() - 5);
                    classNameList.add(className);

                    ModelClassModel entity = getEntity.apply(file);
                    modelList.add(entity);

                    generatorUtil.MODEL_MAP.put(className, entity);
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("classNameList", classNameList);
        map.put("classList", modelList);

        process(outputPath + outFileName, template, map);
    }

    private Template getTemplate(String templateName) throws IOException {

        freemarker.template.Configuration freeMarkerConfiguration = FreemarkerConfigurationFactory.createConfiguration(templatePath);
        Template template = freeMarkerConfiguration.getTemplate(templateName, "UTF-8");

        return template;
    }

    private void process(String path, Template template, Object dataModel) throws IOException, TemplateException {

        try (OutputStream stream = new FileOutputStream(path);
             Writer out = new OutputStreamWriter(stream, "UTF-8")) {
            template.process(dataModel, out);
        }
    }
}
