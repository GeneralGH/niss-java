package com.nanyang.academy.test;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CodeGeneration {
        public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/nanyang?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "root";
        String parent = "com.nanyang.academy";
        String bao ="D:\\workSpace\\南洋\\nissweb-java\\src\\main\\java";
        String mapper = "D:\\workSpace\\南洋\\nissweb-java\\src\\main\\resources\\mapper";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("yiyu") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd")
                            .fileOverride() // 覆盖已生成文件
//                            .disableOpenDir()//禁止打开输出目录
//                            .enableKotlin() //开启 kotlin 模式
                            .outputDir(bao); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parent ) // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapper)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(getTables("enrollment_brochure,activity_notice"));// 设置需要生成的表名
                    builder.entityBuilder().enableLombok(); //启动lombok
                    builder.mapperBuilder().enableMapperAnnotation().build(); //启用@mapper注释
                    builder.controllerBuilder().enableHyphenStyle().enableRestStyle(); //启用驼峰转连字符样式
                    builder.addTablePrefix("m_", "c_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .templateEngine(new VelocityTemplateEngine())
//                .templateEngine(new BeetlTemplateEngine())
                .execute();
    }
    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
