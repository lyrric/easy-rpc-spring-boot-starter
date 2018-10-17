package com.easy.rpc.client.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.util.*;

/**
 * Created on 2018/10/17.
 * class工具
 * @author wangxiaodong
 */
public class ClassUtil {

    /**
     * 返回指定包下的所有类
     * @param basePackage 包名
     * @return
     */
    public static Set<Class<?>> scanClass(String basePackage){
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);
        Set<Class<?>> classes = new HashSet<>(beanDefinitions.size());
        beanDefinitions.forEach(beanDefinition -> {
            try {
                classes.add(Thread.currentThread().getContextClassLoader().loadClass(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return classes;
    }
}
