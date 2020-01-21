package com.yangyun.imports;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName MyImportSelector
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 11:55
 * @Version 1.0
 **/
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // AnnotationMetadata: 标注了被修饰的类的全部注解信息
        return new String[0];
    }
}
