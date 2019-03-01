/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.xiaohe.websocket;

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
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        int size = 88;
        executorService = new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    }
}
