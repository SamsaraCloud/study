package com.yangyun.generator;

import lombok.Getter;

/**
 * @author yangyun
 * @Description:
 * @date 2020/5/27 10:09
 */
@Getter
public enum AnnotationEnum {

    DATA("@Data","lombok.Data"),
    APIMODELPROPERTY("@ApiModelProperty","io.swagger.annotations.ApiModelProperty"),
    NOTNULL("@NotNull","javax.validation.constraints.NotNull"),
    MIN("@Min","javax.validation.constraints.Min"),
    MAX("@Max","javax.validation.constraints.Max"),
    SIZE("@Size","javax.validation.constraints.Max"),
    ;


    private String annotationName;
    private String annotationReference;

    AnnotationEnum(String annotationName, String annotationReference){
        this.annotationName = annotationName;
        this.annotationReference = annotationReference;
    }
}
