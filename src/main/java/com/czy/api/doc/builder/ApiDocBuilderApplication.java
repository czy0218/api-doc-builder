package com.czy.api.doc.builder;

import com.czy.api.doc.builder.generator.DocGenerator;
import com.czy.api.doc.builder.utils.DirectoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:36
 * @Version 1.0
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class ApiDocBuilderApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApiDocBuilderApplication.class, args);
    }

    @Autowired
    DocGenerator docGenerator;

    @Autowired
    String outputPath;

    @Override
    public void run(String... strings) throws Exception {

        DirectoryUtil.createIfNotExists(outputPath);

        docGenerator.generateVoDoc();
        docGenerator.generateParameterDoc();
        docGenerator.generateApiDoc();
    }
}
