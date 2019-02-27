package com.czy.api.doc.builder.factory;

import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

/**
 * @Author: czy
 * @Date: 2019/2/26 15:38
 * @Version 1.0
 */
public class FreemarkerConfigurationFactory {

    public static freemarker.template.Configuration createConfiguration(String templatePath) throws IOException {

        File directory = new File(templatePath);
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        configuration.setDirectoryForTemplateLoading(directory);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }
}
