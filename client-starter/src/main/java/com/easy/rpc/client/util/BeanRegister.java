package com.easy.rpc.client.util;

import com.easy.rpc.client.model.RpcClientProperties;
import com.easy.rpc.client.proxy.RpcInvocationHandler;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * Created on 2018/10/17.
 * 手动注册代理bean
 * @author wangxiaodong
 */
@CommonsLog
//@AutoConfigureAfter({RpcClientProperties.class,RpcInvocationHandler.class})
public class BeanRegister implements BeanDefinitionRegistryPostProcessor {

    private RpcClientProperties rpcClientProperties;
    /**
     * 所有rpc接口的逻辑都是一样
     * 这里用同一个对象，节约内存
     */
    private  RpcInvocationHandler rpcInvocationHandler;


    public BeanRegister(RpcClientProperties rpcClientProperties, RpcInvocationHandler rpcInvocationHandler) {
        this.rpcClientProperties = rpcClientProperties;
        this.rpcInvocationHandler = rpcInvocationHandler;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    /**
     * 扫描所有的接口service
     * 并注册到spring中
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("注册service bean开始");
/*        String basePackage = rpcClientProperties.getBasePackage();
        if(basePackage != null && !"".equals(basePackage)){
            Set<Class<?>> classes = ClassUtil.scanClass(rpcClientProperties.getBasePackage());
            log.info("共找到service类"+classes.size()+"个");
            classes.forEach(clazz->
                beanFactory.registerSingleton(getBeanNameByClass(clazz),
                        Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},rpcInvocationHandler))
            );
        }else{
            log.info("basePackage不可用，请检查你的配置");
        }*/
        log.info("注册service bean结束");
    }

    /**
     * 将类名转换为默认的实例名(首字母小写)
     * @param clazz
     * @return
     */
    private String getBeanNameByClass(Class<?> clazz){
        String className = clazz.getSimpleName();
        return Character.toLowerCase(className.charAt(0)) +className.substring(1);
    }

}
