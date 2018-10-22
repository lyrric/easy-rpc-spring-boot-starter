
# easy-rpc-spring-boot-starter
easy-rpc-spring-boot-starter

简单易上手的rpc框架  
与spring boot整合，只需要简单配置即可代替服务之间http请求

Welcome to the easy-rpc-spring-boot-starter wiki!
1.写一个接口类充当rpc接口
```
public interface UserService {
    User findById(Integer id);
}
```
##2.单独把接口封装在一个模块中，服务端和客户端依赖于该模块（或者jar包）  
##3.服务端添加依赖  
```
<dependency>
    <groupId>com.github.lyrric</groupId>
	<artifactId>spring-boot-starter-rpc-server</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```
##4.服务端实现UserService接口，并添加**@RpcService**注解
```
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User findById(Integer id) {
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        return user;
    }
}
```
##5.客户端（请求端）添加依赖
```
<dependency>
	<groupId>com.github.lyrric</groupId>
	<artifactId>spring-boot-starter-rpc-client</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```
##6.配置接口路径和rpc服务端IP和端口
```
    rpc:
        server-host: 127.0.0.1
        base-package: com.demo.api.service
        server-port: 9898
        request-timeout: 20
 ```
##7.在Controller或者Service中注入
```
@Resource
private UserService userService;
```
##然后尽情的使用吧
