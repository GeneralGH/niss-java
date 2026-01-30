package com.nanyang.academy.utils;


import com.nanyang.academy.entity.pojo.BeanField;
import com.nanyang.academy.entity.pojo.GenerateInput;

import java.util.ArrayList;
import java.util.List;

public class DetailToInput {

    public static GenerateInput getTable(List<BeanField> beanFields, GenerateInput input1) {
        GenerateInput input = new GenerateInput();
        input.setTableName(input1.getTableName());
        List<String> columnNames = new ArrayList<>();
        List<String> columnTypes = new ArrayList<>();
        List<String> columnComments = new ArrayList<>();
        //String tableName = TemplateUtil.lowerFirstChar(input.getTableName());
        input.setBeanName(input1.getTableName());
        for (BeanField b : beanFields) {
            columnNames.add(b.getName());
            columnTypes.add(b.getType());
            columnComments.add(b.getColumnComment());
        }
        input.setBeanFieldName(columnNames);
        input.setBeanFieldType(columnTypes);
        input.setBeanValue(columnComments);

        //controller service mapper bean 的包名都有了
        String packageName = input1.getPackageName();
        GenerateInput packageName1 = getPackageName(packageName, input);
        input.setControllerPackageName(packageName1.getControllerPackageName());
        input.setServicePackageName(packageName1.getServicePackageName());
        input.setMapperPackageName(packageName1.getMapperPackageName());
        input.setBeanPackageName(packageName1.getBeanPackageName());

        // controllername servicename mappername beanname
        GenerateInput beanName = getBeanName(input1.getTableName());
        input.setControllerName(beanName.getControllerName());
        input.setServiceName(beanName.getServiceName());
        input.setMapperName(beanName.getMapperName());
        input.setBeanName(input1.getTableName());

        return input;
    }

    public static GenerateInput getPackageName(String packageName, GenerateInput input) {
        input.setControllerPackageName(packageName + ".controller");
        input.setServicePackageName(packageName + ".service");
        input.setMapperPackageName(packageName + ".mapper");
        input.setBeanPackageName(packageName + ".entity");

        return input;
    }
    public static GenerateInput getBeanName(String tableName) {
        GenerateInput input = new GenerateInput();
        input.setControllerName(tableName+"Controller");
        input.setServiceName(tableName+"Service");
        input.setMapperName(tableName+"Mapper");
        input.setBeanName(tableName);
        return input;
    }
}
