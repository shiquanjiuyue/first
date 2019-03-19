package com.xiaohe.websocket;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * websocket线程池
 *
 * @author xiezhaohe
 * @version V1.0
 * @since 2019-02-28 22:18
 */
public class WebsocketThreadPool {

    private final static ExecutorService executorService;

    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        // 1.当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程
        // 2.当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行
        // 3.当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务
        // 4.当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理
        // 5.当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程
        // 6.当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭
        int corePoolSize = 1;
        int maxPoolSize = 1;
        executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), namedThreadFactory);

    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

}
