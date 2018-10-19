package com.github.easy.rpc.client.bean;

import com.github.easy.rpc.client.proxy.RpcInvocationHandler;
import com.github.easy.rpc.client.util.ClassUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * Created on 2018/10/17.
 * 手动注册代理bean
 * @author wangxiaodong
 */
@CommonsLog
@Component
public class BeanRegister implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    /**
     * 获取环境，读取配置
     */
    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    /**
     * 这里不能直接扫描注册bean，因为该方法优先于其他bean的实例化之前执行，会导致无法注入相关的bean（clientProperties）
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final RpcInvocationHandler rpcInvocationHandler = new RpcInvocationHandler();
        log.info("注册service bean开始");
        String basePackage = environment.getProperty("rpc.base-package");
        if(!"".equals(basePackage)){
            Set<Class<?>> classes = ClassUtil.scanAllClass(basePackage);
            log.info("共找到service类"+classes.size()+"个");
            classes.forEach(clazz->
                    beanFactory.registerSingleton(clazz.getName(),
                            Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, rpcInvocationHandler))
            );
        }else{
            log.info("basePackage不可用，请检查你的配置");
        }
        log.info("注册service bean结束");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
