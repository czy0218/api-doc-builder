package com.czy.api.doc.builder.utils;

import com.czy.api.doc.builder.entity.ParameterTypeEntity;
import com.czy.api.doc.builder.entity.FieldEntity;
import com.czy.api.doc.builder.entity.FunctionEntity;
import com.czy.api.doc.builder.model.ApiClassModel;
import com.czy.api.doc.builder.model.ModelClassModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: czy
 * @Date: 2019/2/26 17:45
 * @Version 1.0
 */
@Component("generatorUtil")
public class GeneratorUtil {
    @Autowired
    String addmMatch;

    @Autowired
    String updateMatch;

    /**
     * 已经生成的所有类型Map
     */
    public Map<String, ModelClassModel> MODEL_MAP = new HashMap<>();

    /**
     * 获取Model类实体
     *
     * @param file
     * @return
     */
    public ModelClassModel getModelClassModel(File file) {

        List<FieldEntity> fields = new ArrayList<>();

        try (FileReader reader = new FileReader(file.getPath());
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            FieldEntity fieldEntity = new FieldEntity();

            //进入类内部
            while ((line = br.readLine()) != null) {

                if (line.startsWith("public class")) {
                    break;
                }
            }

            int i = 0;
            while ((line = br.readLine()) != null) {

                if ((line = line.trim()).isEmpty()) continue;

                if (line.endsWith("{")) {
                    i++;
                    continue;
                }
                if (line.endsWith("}")) {
                    i--;
                    continue;
                }
                if (i > 0) {
                    continue;
                }

                if (line.startsWith("/**")) {
                    fieldEntity.setRemark("");
                } else if (line.startsWith("*") && !line.endsWith("*/")) {
                    fieldEntity.setRemark(fieldEntity.getRemark() + " " + line.replace("*", "").trim());
                } else if (line.startsWith("@CheckFieldAnnotation")) {
                    setFieldInsertAndUpdate(fieldEntity, line);
                } else if (line.endsWith(";")) {
                    setFieldNameAndType(fieldEntity, line);
                    fields.add(fieldEntity);
                    fieldEntity = new FieldEntity();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ModelClassModel modelEntity = new ModelClassModel();
        modelEntity.setClassName(file.getName().replace(".java", ""));
        modelEntity.setFields(fields);
        return modelEntity;
    }

    /**
     * 获取Vo类实体
     *
     * @param file
     * @return
     */
    public ModelClassModel getVoClassModel(File file) {

        List<FieldEntity> fields = new ArrayList<>();

        try (FileReader reader = new FileReader(file.getPath());
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            FieldEntity fieldEntity = new FieldEntity();

            //进入类内部
            while ((line = br.readLine()) != null) {

                if (line.startsWith("public class")) {
                    break;
                }
            }

            int i = 0;
            while ((line = br.readLine()) != null) {

                if ((line = line.trim()).isEmpty()) continue;

                if (line.endsWith("{")) {
                    i++;
                    continue;
                }
                if (line.endsWith("}")) {
                    i--;
                    continue;
                }
                if (i > 0) {
                    continue;
                }

                if (line.startsWith("/**")) {
                    fieldEntity.setRemark("");
                } else if (line.startsWith("*") && !line.endsWith("*/")) {
                    fieldEntity.setRemark(fieldEntity.getRemark() + " " + line.replace("*", "").trim());
                } else if (line.endsWith(";")) {
                    setFieldNameAndType(fieldEntity, line);
                    fields.add(fieldEntity);
                    fieldEntity = new FieldEntity();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ModelClassModel modelEntity = new ModelClassModel();
        modelEntity.setClassName(file.getName().replace(".java", ""));
        modelEntity.setFields(fields);
        return modelEntity;
    }

    /**
     * 获取Parameter类实体
     *
     * @param file
     * @return
     */
    public ModelClassModel getParameterClassModel(File file) {

        List<FieldEntity> fields = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(file.getAbsolutePath());
             Reader reader = new InputStreamReader(inputStream, "UTF-8");
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            String tempRemark = ""; ////region注释
            FieldEntity fieldEntity = new FieldEntity();

            //进入类内部
            while ((line = br.readLine()) != null) {

                if (line.startsWith("public class")) {
                    break;
                }
            }

            int i = 0;
            while ((line = br.readLine()) != null) {

                if ((line = line.trim()).isEmpty()) continue;

                if (line.endsWith("{")) {
                    i++;
                    continue;
                }
                if (line.endsWith("}")) {
                    i--;
                    continue;
                }
                if (i > 0) {
                    continue;
                }

                if (line.startsWith("//region")) {
                    tempRemark = line.replace("//region", "").trim();
                } else if (line.startsWith("//endregion")) {
                    tempRemark = "";
                } else if (line.startsWith("/**")) {
                    fieldEntity.setRemark("");
                } else if (line.startsWith("*") && !line.endsWith("*/")) {
                    fieldEntity.setRemark(fieldEntity.getRemark() + " " + line.replace("*", "").trim());
                } else if (line.startsWith("private ") && line.endsWith(";") && !line.contains("orderByMap")) {
                    setFieldNameAndType(fieldEntity, line);
                    if (fieldEntity.getRemark() == null || fieldEntity.getRemark().isEmpty()) {
                        fieldEntity.setRemark(getRemark(tempRemark, fieldEntity.getFieldType(), fieldEntity.getFieldName()));
                    }

                    fields.add(fieldEntity);
                    fieldEntity = new FieldEntity();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ModelClassModel modelEntity = new ModelClassModel();
        modelEntity.setClassName(file.getName().replace(".java", ""));
        modelEntity.setFields(fields);
        return modelEntity;
    }

    /**
     * 获取API类实体
     *
     * @param file
     * @return
     */
    public ApiClassModel getApiClassModel(File file) {

        try (InputStream inputStream = new FileInputStream(file.getAbsolutePath());
             Reader reader = new InputStreamReader(inputStream, "UTF-8");
             BufferedReader br = new BufferedReader(reader)) {

            String fileName = file.getName();
            String className = fileName.substring(0, fileName.length() - 5);

            ApiClassModel apiEntity = new ApiClassModel();
            apiEntity.setClassName(className);

            List<FunctionEntity> functions = new ArrayList<>();

            String line;

            //进入接口内部
            while ((line = br.readLine()) != null) {

                if (line.startsWith("package")) {
                    String packageName = line.replace("package", "").replace(";", "").trim();
                    apiEntity.setPackageName(packageName);
                }

                if (line.startsWith("public interface")) {
                    break;
                }
            }

            FunctionEntity functionEntity = new FunctionEntity();
            while ((line = br.readLine()) != null) {

                line = line.replace("public", "").trim();

                if (line.isEmpty()) continue;

                if (line.startsWith("*") && !line.endsWith("*/")) {
                    Pattern pattern = Pattern.compile("\\*\\s?@");
                    Pattern patternAuthor = Pattern.compile("\\*\\s?@author\\s?");
                    Pattern patternVersion = Pattern.compile("\\*\\s?@version\\s?");
                    Pattern patternAuthor2 = Pattern.compile("(\\S{1,}\\s?（[^）]*）|\\S{1,}\\s?\\([^)]*\\))");
                    Pattern patternDesc = Pattern.compile("\\*\\s?@(描述|Description)(：|:)?\\s?");
                    if ((pattern.matcher(line)).find()) {
                        if ((patternAuthor.matcher(line)).find()) {
                            functionEntity.setAuthor(line.replaceAll("\\*\\s?@author\\s?", ""));
                        } else if ((patternDesc.matcher(line)).find()) {
                            functionEntity.setRemark(line.replaceAll("\\*\\s?@(描述|Description)(：|:)?\\s?", ""));
                        } else if (patternVersion.matcher(line).find()) {
                            Matcher matcher = patternAuthor2.matcher(line);
                            if (matcher.find()) {
                                functionEntity.setAuthor(matcher.group().replace("由", ""));
                            }
                        }
                    } else {
                        if (!line.trim().equals("*")) {
                            functionEntity.setRemark(functionEntity.getRemark() + " " + line.replace("*", "").trim());
                        }
                    }
                } else if (line.startsWith("Result")) {
                    if (!line.endsWith(";")) {
                        String tmpLine;
                        while ((tmpLine = br.readLine()) != null) {
                            line += tmpLine;
                            if (tmpLine.endsWith(";")) break;
                        }
                    }
                    setFunctionParams(functionEntity, line);
                    String func = line.split("\\(")[0];
                    int lastIndex = func.lastIndexOf(" ");
                    functionEntity.setName(func.substring(lastIndex).trim());
                    functionEntity.setType(getFunctionType(functionEntity.getName()));

                    //获取返回类型
                    String returnType = func.substring(0, lastIndex).trim();
                    functionEntity.setReturnType(new ParameterTypeEntity(returnType, MODEL_MAP.get(getClassType(returnType))));

                    functionEntity.setDesc(line.replaceAll("\\s{1,}", " "));

                    functions.add(functionEntity);
                    functionEntity = new FunctionEntity();
                }

            }

            apiEntity.setFunctions(functions);
            return apiEntity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 设置字段类型和名称
     *
     * @param fieldEntity
     * @param line
     */
    private void setFieldNameAndType(FieldEntity fieldEntity, String line) {

        line = line.replace("private", "").split("=")[0].replace(";", "").trim();

        int lastIndex = line.lastIndexOf(" ");

        fieldEntity.setFieldName(line.substring(lastIndex).trim());
        fieldEntity.setFieldType(line.substring(0, lastIndex).trim());
    }

    /**
     * 获取方法的类型，添加，更新
     *
     * @param functionName
     * @return
     */
    private String getFunctionType(String functionName) {
        for (String s : addmMatch.split(",")) {
            if (functionName.startsWith(s)) return "add";
        }
        for (String s : updateMatch.split(",")) {
            if (functionName.startsWith(s)) return "update";
        }
        return null;
    }

    /**
     * 设置字段是否必须
     *
     * @param field
     * @param line
     */
    private void setFieldInsertAndUpdate(FieldEntity field, String line) {
        Pattern pattern = Pattern.compile("\\(.*\\)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String matchStr = matcher.group();
            String[] array = matchStr.substring(1, matchStr.length() - 1).split(",");
            for (String param : array) {
                param = param.trim();
                if (param.startsWith("insertNotNull")) {
                    field.setInsertNotNull(param.split("=")[1].trim().equals("true"));
                }
                if (param.trim().startsWith("updateNotNull")) {
                    field.setUpdateNotNull(param.split("=")[1].trim().equals("true"));
                }
            }
        }
    }

    /**
     * 为Parameter生成字段备注
     *
     * @param remark
     * @param fieldType
     * @param fieldName
     * @return
     */
    private String getRemark(String remark, String fieldType, String fieldName) {
        if (fieldName.endsWith("List")) {
            return remark + "列表";
        } else if (fieldName.endsWith("Min")) {
            if (fieldType.equals("Date")) {
                return "开始" + remark;
            } else {
                return "最小" + remark;
            }
        } else if (fieldName.endsWith("Max")) {
            if (fieldType.equals("Date")) {
                return "结束" + remark;
            } else {
                return "最大" + remark;
            }
        } else if (fieldName.endsWith("StartWith")) {
            return remark + " (开始匹配）";
        } else if (fieldName.endsWith("Like")) {
            return remark + " (泛匹配）";
        } else if (fieldType.equals("String")) {
            return remark + " (完全匹配）";
        } else {
            return remark;
        }
    }

    /**
     * 设置参数列表
     *
     * @param function
     * @param line
     */
    private void setFunctionParams(FunctionEntity function, String line) {

        Pattern pattern = Pattern.compile("\\(.*\\)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {

            List<ParameterTypeEntity> params = new ArrayList<>();
            String matchStr = matcher.group();
            String[] array = matchStr.substring(1, matchStr.length() - 1).split(",");
            for (String param : array) {
                String paramType = param.split("\\s{1,}")[0];
                params.add(new ParameterTypeEntity(paramType, MODEL_MAP.get(getClassType(paramType))));
            }
            function.setParams(params);
        }
    }

    /**
     * 获取要展示的类型，去掉泛型
     *
     * @param type
     * @return
     */
    private String getClassType(String type) {

        Pattern pattern = Pattern.compile("<[^(<|>)]*>");
        Matcher matcher;
        while ((matcher = pattern.matcher(type)).find()) {
            String tmpStr = matcher.group();
            type = tmpStr.substring(1, tmpStr.length() - 1);
        }
        String[] typeArr = type.split("\\s?,\\s?");

        return typeArr[typeArr.length - 1];
    }
}
