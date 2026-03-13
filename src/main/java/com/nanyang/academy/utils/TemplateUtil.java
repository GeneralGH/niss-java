package com.nanyang.academy.utils;


import com.nanyang.academy.entity.pojo.GenerateInput;
import com.nanyang.academy.service.GenerateService;
import com.nanyang.academy.utils.file.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
public class TemplateUtil {

    @Autowired
    private GenerateService generateService;

    public static String getTemplete(String fileName) {
        return FileUtil.getText(TemplateUtil.class.getClassLoader().getResourceAsStream("generate/" + fileName));
    }

    public static void saveController(GenerateInput input) {
        String path = input.getPath();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        String serviceBeanName = input.getServiceName();
        String text = getTemplete("controller.txt");
        text = text.replace("{servicePackageName}", input.getServicePackageName());
        text = text.replace("{serviceName}", input.getServiceName());
        text = text.replace("{beanPackageName}", input.getBeanPackageName());
        text = text.replace("{beanName}", input.getBeanName());
        text = text.replace("{beanParamName}", lowerFirstChar(beanName));
        text = text.replace("{controllerName}", input.getControllerName());
        text = text.replace("{controllerPkgName}", input.getControllerPackageName());
        text = text.replace("{service}", lowerFirstChar(input.getServiceName()));
        FileUtil.saveTextFile(text, path + File.separator + "java\\" + getPackagePath(input.getControllerPackageName())
                + input.getControllerName() + ".java");
        log.debug("生成controller：{}模板", beanName);
    }

    public static void saveService(GenerateInput input) {
        String path = input.getPath();
        String text = getTemplete("service.txt");
        text = text.replace("{servicePackageName}", input.getServicePackageName());
        text = text.replace("{beanPackageName}", input.getBeanPackageName());
        text = text.replace("{beanName}", input.getBeanName());
        text = text.replace("{serviceName}", input.getServiceName());
        text = text.replace("{beanParamName}", lowerFirstChar(input.getBeanName()));
        FileUtil.saveTextFile(text, path + File.separator + "java\\" + getPackagePath(input.getServicePackageName())
                + input.getServiceName() + ".java");
        log.debug("service：{}模板", input.getBeanName());
    }

    public static void saveServiceImpl(GenerateInput input) {
        String path = input.getPath();
        String text = getTemplete("serviceImpl.txt");
        text = text.replace("{serviceImplPackageName}", input.getServicePackageName());
        text = text.replace("{beanPackageName}", input.getBeanPackageName());
        text = text.replace("{beanName}", input.getBeanName());
        text = text.replace("{mapperPackageName}", input.getMapperPackageName());
        text = text.replace("{mapperName}", input.getMapperName());
        text = text.replace("{mapper}", lowerFirstChar(input.getMapperName()));
        text = text.replace("{servicePackageName}", input.getServicePackageName());
        text = text.replace("{serviceName}", input.getServiceName());
        text = text.replace("{serviceNameImpl}", input.getServiceName().concat("Impl"));
        text = text.replace("{beanParamName}", lowerFirstChar(input.getBeanName()));
        FileUtil.saveTextFile(text, path + File.separator + "java\\" + getPackagePath(input.getServicePackageName().concat(".impl"))
                + input.getServiceName().concat("Impl") + ".java");
        log.debug("生成serviceImpl：{}模板", input.getServiceName().concat("Impl"));
    }

    public static void saveBean(GenerateInput input) {
        String path = input.getPath();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        List<String> beanFieldName = input.getBeanFieldName();
        List<String> beanFieldType = input.getBeanFieldType();
        List<String> beanValue = input.getBeanValue();
        String text = getTemplete("java.txt");
        text = text.replace("{beanPackageName}", beanPackageName).replace("{beanName}", beanName);
        String imports = "";
        if (beanFieldType.contains("BigDecimal")) {
            imports += "import " + BigDecimal.class.getName() + ";\n";
        }
        String name = Date.class.getName();
        if (beanFieldType.contains("Date")) {
            imports += "import " + Date.class.getName() + ";\n";
        }

        text = text.replace("{import}", imports);
        String fileds = getFields(beanFieldName, beanFieldType);

        text = text.replace("{filelds}", fileds);
        text = text.replace("{swaggerInfo}", input.getBeanName());
        text = text.replace("{tableinfo}", input.getBeanName());

        for(int i = 0; i < beanValue.size(); i++ ){
            text = text.replace("{msg"+i+"}", beanValue.get(i));
        }
        //text = text.replace("{getset}")
        FileUtil.saveTextFile(text, path + File.separator + "java\\" + getPackagePath(beanPackageName) + beanName + ".java");
        log.info("生成java pojo：{}模板", beanName);

    }

    private static String getFields(List<String> beanFieldName, List<String> beanFieldType) {
        StringBuffer buffer = new StringBuffer();
        int size = beanFieldName.size();

        for (int i = 0; i < size; i++) {
            String name = beanFieldName.get(i);
            // || "createTime".equals(name) || "updateTime".equals(name)
            if ("id".equals(name)) {
                String id = "\n\t@TableId(value = \"id\", type = IdType.AUTO)\n";
                buffer.append(id);
            }
            /*
             "createTime".equals(name)
             "updateTime".equals(name)
             */
            String type = beanFieldType.get(i);
            String api = "\t@ApiModelProperty(value = \"{msg" + i + "}\")\n";
            buffer.append(api);
            buffer.append("\tprivate ").append(type).append(" ").append(name);
            buffer.append(";\n");
        }

        return buffer.toString();
    }

    public static void saveMapper(GenerateInput input) {
        String path = input.getPath();
        // String tableName = input.getTableName();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        String mapperPackageName = input.getMapperPackageName();
        String mapperName = input.getMapperName();

        String text = getTemplete("mapper.txt");
        text = text.replace("{mapperPackageName}", mapperPackageName);
        text = text.replace("{beanPackageName}", beanPackageName);
        text = text.replace("{mapperName}", mapperName);
        text = text.replace("{beanName}", beanName);
        FileUtil.saveTextFile(text, path + File.separator + "java\\" + getPackagePath(mapperPackageName) + mapperName + ".java");
        log.debug("生成java dao：{}模板", beanName);

        text = getTemplete("mapper.xml");
        text = text.replace("{mapperPackageName}", mapperPackageName);
        text = text.replace("{mapperName}", mapperName);
        String a = path + File.separator + "resources\\mapper\\" + beanName + "Mapper.xml";
        //path + File.separator + getPackagePath(mapperPackageName) + beanName + "Mapper.xml";
        FileUtil.saveTextFile(text, a);
    }

    public static String lowerFirstChar(String beanName) {
        String name = StringUtil.str2hump(beanName);
        String firstChar = name.substring(0, 1);
        name = name.replaceFirst(firstChar, firstChar.toLowerCase());
        return name;
    }

    private static String getPackagePath(String packageName) {
        String packagePath = packageName.replace(".", "/");
        if (!packagePath.endsWith("/")) {
            packagePath = packagePath + "/";
        }

        return packagePath;
    }
}
