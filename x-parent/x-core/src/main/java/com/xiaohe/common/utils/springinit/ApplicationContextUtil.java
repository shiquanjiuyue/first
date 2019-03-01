package com.xiaohe.common.utils.springinit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/** 此类保存spring上下文的对象 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

	/** spring上下文的对象 */
	private static ApplicationContext applicationcontext;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationcontext = arg0;
	}
	
	/** 获取spring上下文对象 */
	public static ApplicationContext getApplicationContext(){
		return applicationcontext;
	}
	
	/** 获取spring容器中的bean */
	public static Object getBean(String BeanName){
		return applicationcontext.getBean(BeanName);
	}

}
