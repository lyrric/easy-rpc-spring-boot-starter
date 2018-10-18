package com.easy.rpc.client.util;

import com.easy.rpc.common.RpcApi;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.*;

/**
 * Created on 2018/10/17.
 * class工具
 * @author wangxiaodong
 */
@CommonsLog
public class ClassUtil {

    public static Set<Class<?>> scanAllClass(String basePackage){
        return new ClassScanProvider().scanAllClass(basePackage);
    }
}

/**
 * 原spring的ClassPathScanningCandidateComponentProvider过滤了interface
 * 重写使其支持interface
 */

class ClassScanProvider extends ClassPathScanningCandidateComponentProvider {

    ClassScanProvider() {
        super(false);
    }

    Set<Class<?>> scanAllClass(String basePackage) {
        String resourcePattern =  "**/*.class";
        Set<Class<?>> classes = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + resourcePattern;
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null).getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    try {
                        String className = getMetadataReaderFactory().getMetadataReader(resource).getAnnotationMetadata().getClassName();
                        classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                      logger.trace("Ignored because not readable: " + resource);

                }
            }
        }
        catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return classes;
    }
}
