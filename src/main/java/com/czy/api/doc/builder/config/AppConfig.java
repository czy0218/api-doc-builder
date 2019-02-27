package com.czy.api.doc.builder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:32
 * @Version 1.0
 */
@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Value("${project.path}")
    String projectPath;

    @Value("${project.java.src}")
    String projectJavaSrc;

    @Value("${project.basePackage}")
    String projectBasePackage;

    @Value("${project.api.subPackages}")
    String projectApiSubPackages;

    @Value("${path.template}")
    String templatePath;

    @Value("${path.output}")
    String outputPath;

    @Value("${function.addmatch}")
    String addmMatch;

    @Value("${function.updatematch}")
    String updateMatch;
    @Value("${project.modelPath}")
    String modelPath;

    @Value("${project.voPath}")
    String voPath;
    @Value("${project.parameterPath}")
    String parameterPath;

    @Value("${project.api.fatherPackagesPath}")
    String fatherPackagesPath;

    private String getpackagePath() {
        return projectBasePackage.replaceAll("\\.", "/");
    }

    @Bean("modelPath")
    public String getModelPath() {
        return projectPath + projectJavaSrc + getpackagePath() + "/" + modelPath + "/";
    }

    @Bean("voPath")
    public String getVoPath() {
        return projectPath + projectJavaSrc + getpackagePath() + "/" + voPath + "/";
    }

    @Bean("parameterPath")
    public String getParameterPath() {
        return projectPath + projectJavaSrc + getpackagePath() + "/" + parameterPath + "/";
    }

    @Bean("apiPath")
    public String getApiPath() {
        return projectPath + projectJavaSrc + getpackagePath() + "/" + fatherPackagesPath + "/";
    }

    @Bean("apiSubPackages")
    public String getApiPaths() {
        return projectApiSubPackages;
    }

    @Bean("templatePath")
    public String getTemplatePath() {
        return templatePath;
    }

    @Bean("outputPath")
    public String getOutputPath() {
        return outputPath;
    }

    @Bean("addmMatch")
    public String getAddmMatch() {
        return addmMatch;
    }

    @Bean("updateMatch")
    public String getUpdateMatch() {
        return updateMatch;
    }
}
