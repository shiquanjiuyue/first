package com.xiaohe.elasticjob;

import com.dangdang.ddframe.job.api.AbstractOneOffElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;

import org.apache.log4j.Logger;

/**
 * TODO
 *
 * @author xiezhaohe
 * @version V1.0
 * @since 2019-03-13 21:03
 */
public class MyElasticJob extends AbstractOneOffElasticJob {

    private static Logger logger = Logger.getLogger(AbstractOneOffElasticJob.class);

    @Override
    protected void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
        logger.info("执行自定义任务");
    }
}
