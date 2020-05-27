package com.yangyun.generator;

import com.yangyun.generator.AnnotationEnum;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * @author yangyun
 * @Description:
 * @date 2020/5/26 15:50
 */
@Configuration
public class MyCommentGenerator implements CommentGenerator {

    private CompilationUnit compilationUnit;

    private List<String> fieldType = new ArrayList<>(Arrays.asList("Long", "Integer"));

    private String MAX_NUM = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";

    @Override
    public void addConfigurationProperties(Properties properties) {
    }

    /**
     * 功能描述: 根据字段信息判断添加的注解
     * @Param: [field, introspectedTable, introspectedColumn]
     * @Return: void
     * @Author: yangyun
     * @Date: 2020/5/27 14:00
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!introspectedColumn.isNullable()){
            field.addAnnotation(AnnotationEnum.NOTNULL.getAnnotationName());
            FullyQualifiedJavaType notnull = new FullyQualifiedJavaType(AnnotationEnum.NOTNULL.getAnnotationReference());
            compilationUnit.addImportedType(notnull);
        }

        // 获取字段类型
        String shortName = introspectedColumn.getFullyQualifiedJavaType().getShortName();
        if (fieldType.contains(shortName)){
            setMaxAnnotation(field, introspectedColumn);
            FullyQualifiedJavaType max = new FullyQualifiedJavaType(AnnotationEnum.MAX.getAnnotationReference());
            compilationUnit.addImportedType(max);
        } else if ("String".equals(shortName)){
            setMaxStringLength(field, introspectedColumn);
//            FullyQualifiedJavaType max = new FullyQualifiedJavaType(AnnotationEnum.SIZE.getAnnotationReference());
//            compilationUnit.addImportedType(max);
        }

        FullyQualifiedJavaType apimodelproperty = new FullyQualifiedJavaType(AnnotationEnum.APIMODELPROPERTY.getAnnotationReference());
        compilationUnit.addImportedType(apimodelproperty);
    }
    
    private String generatorMaxNum(int length){
        return MAX_NUM.substring(0, length);
    }

    private void setMaxAnnotation(Field field, IntrospectedColumn introspectedColumn){
        StringBuffer sb = new StringBuffer(AnnotationEnum.MAX.getAnnotationName());
        sb.append("(value = ").append(generatorMaxNum(introspectedColumn.getLength()))
            .append(", message = \"" + introspectedColumn.getRemarks() + "超出最大值\")");
        field.addAnnotation(sb.toString());
    }

    private void setMaxStringLength(Field field, IntrospectedColumn introspectedColumn){
        StringBuffer sb = new StringBuffer(AnnotationEnum.SIZE.getAnnotationName());
        sb.append("(max = ").append(introspectedColumn.getLength())
                .append(", message = \"").append(introspectedColumn.getRemarks())
                .append("长度不能超过" ).append(introspectedColumn.getLength())
                .append("\"");
        field.addAnnotation(sb.toString());
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // 用来添加需要导入的包
        this.compilationUnit = compilationUnit;
    }

    @Override
    public void addComment(XmlElement xmlElement) {
    }

    @Override
    public void addRootComment(XmlElement xmlElement) {
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {
    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
    }
}
