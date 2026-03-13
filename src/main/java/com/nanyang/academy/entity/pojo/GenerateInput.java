package com.nanyang.academy.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class GenerateInput implements Serializable {

	private static final long serialVersionUID = -2870071259702969061L;
	/**
	 * 保存路径
	 */
	private String path;// 1
	private String tableName;// 1
	private String packageName;// 1
	/**
	 * java类名
	 */
	private String beanName;// 1
	private String beanPackageName;// 1

	/**
	 * dao类名
	 */
	private String mapperName;// 1

	private String mapperPackageName;// 1
	/**
	 * service名字
	 */
	private String serviceName;// 1
	private String servicePackageName;// 1

	/**
	 * controller类名
	 *
	 */
	private String controllerName;// 1
	private String controllerPackageName;// 1
	/**
	 * 字段名
	 */
	private List<String> columnNames;
	/**
	 * 属性名
	 */
	private List<String> beanFieldName;// 1
	/**
	 * 成员变量类型
	 */
	private List<String> beanFieldType;// 1
	/**
	 * 默认值
	 */
	private List<String> beanFieldValue;// 1
	/**
	 * 这个是 用来生成swagger的 文档说明信息的。
	 */
	private List<String> beanValue;
}
