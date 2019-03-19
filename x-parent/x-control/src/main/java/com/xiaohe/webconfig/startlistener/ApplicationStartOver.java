package com.xiaohe.webconfig.startlistener;

import com.xiaohe.entity.IUser;
import com.xiaohe.entity.Permission;
import com.xiaohe.entity.Role;
import com.xiaohe.plugs.commonutil.PackageUtil;
import com.xiaohe.plugs.permissionSecurity.HavePermission;
import com.xiaohe.plugs.permissionSecurity.PermissionUtil;
import com.xiaohe.service.PermissionService;
import com.xiaohe.service.RoleService;
import com.xiaohe.service.UserService;
import com.xiaohe.websocket.MyWebSocketChannelHandler;
import com.xiaohe.websocket.WebsocketThreadPool;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * spring容器加载完成后执行这里的方法
 *
 * @author xiezhaohe
 * @since 2019/3/5 21:22
 */
@Component
public class ApplicationStartOver implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = Logger.getLogger(ApplicationStartOver.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 防止执行两次
        if (event.getApplicationContext().getParent() == null) {
            // 系统启动后判断如果没有管理员则创建一个
            Role adminRole = roleService.findByRoleName("admin");
            IUser admin = userService.findByRole(adminRole);
            if (admin == null) {
                // 如果没有管理员这个角色就先创建
                if (adminRole == null) {
                    adminRole = new Role();
                    adminRole.setRoleName("admin");
                    roleService.addRole(adminRole);
                }
                admin = new IUser();
                admin.setLoginName("admin");
                admin.setPassword("123");
                admin.setRole(adminRole);
                userService.addIUser(admin);
                logger.info("*****************************创建系统管理员*****************************");
            }

            // 加载所有controller中的权限，保存到数据库中
            permissionInit();
            // 开启websocket连接
            startWebSocketService();
        }

    }

    /**
     * 加载所有controller中的权限，保存到数据库中
     */
    private void permissionInit() {
        try {
            // 获取数据库中已有的权限信息
            List<Permission> alreadyHavePermissionList = permissionService.findAll();
            // 存储权限表达式
            Set<String> expressionSet = new HashSet<>();
            for (Permission permission : alreadyHavePermissionList) {
                expressionSet.add(permission.getExpression());
            }

            // 获取所有Controller的集合
            Set<String> controllerNames = PackageUtil.getClassName("com.xiaohe.control", false);
            // 用户存放权限对象的集合
            List<Permission> permissionList = new ArrayList<>();
            for (String name : controllerNames) {
                // 根据包名+类名获取controller类对象
                Class<?> controllerClass = Class.forName(name);
                // 获取类中的所有方法
                Method[] methods = controllerClass.getDeclaredMethods();
                for (Method method : methods) {
                    // 判断该方法是否包含权限
                    if (method.isAnnotationPresent(HavePermission.class)) {
                        // 创建权限表达式
                        String expression = PermissionUtil.createExpression(method);
                        // 获取权限名称
                        String permissionName = method.getAnnotation(HavePermission.class).value();
                        // 加入集合,数据库中不存在该权限表达式则加入进去
                        if (!expressionSet.contains(expression)) {
                            permissionList.add(new Permission(permissionName, expression));
                        }
                    }
                }
            }
            // 批量将权限插入数据库
            permissionService.addPermissionList(permissionList);
            logger.info("*****************************系统权限加载完毕*****************************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启websocket连接
     */
    private void startWebSocketService() {
        WebsocketThreadPool.getExecutorService().execute(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workGroup);
                b.channel(NioServerSocketChannel.class);
                b.childHandler(new MyWebSocketChannelHandler());
                logger.info("服务端开启等待客户端连接....");
                Channel ch = b.bind(8888).sync().channel();
                ch.closeFuture().sync();
            } catch (Exception e) {
                logger.error("开启webSocket服务失败", e);
            } finally {
                //优雅的关闭连接
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        });
    }

}
