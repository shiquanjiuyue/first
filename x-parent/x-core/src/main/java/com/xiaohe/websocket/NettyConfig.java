package com.xiaohe.websocket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 存储整个工程的全局配置
 *
 * @author xiezhaohe
 * @since 2019/3/5 20:53
 */
public class NettyConfig {
	
	/**
	 * 存储每一个客户端接入进来时的channel对象
	 */
	public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 允许websocket连接的最大数量
	 */
	public static int maxConnection = 10;
}
