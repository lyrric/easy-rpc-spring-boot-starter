package com.github.easy.rpc.client.bean;

import com.github.easy.rpc.client.SyncFutureMgr;
import com.github.easy.rpc.client.handler.MessageHandler;
import com.github.easy.rpc.client.proxy.RpcInvocationHandler;
import com.github.easy.rpc.client.service.NettyService;
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
     * 注册bean
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //实例相关bean
        SyncFutureMgr syncFutureMgr = new SyncFutureMgr(environment);
        MessageHandler messageHandler = new MessageHandler(syncFutureMgr);
        NettyService nettyService = new NettyService(messageHandler);
        RpcInvocationHandler rpcInvocationHandler = new RpcInvocationHandler(syncFutureMgr, nettyService);
        //注册bean
        beanFactory.registerSingleton(SyncFutureMgr.class.getName(),syncFutureMgr);
        beanFactory.registerSingleton(NettyService.class.getName(),nettyService);

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
