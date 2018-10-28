package com.xiaohe.webConfig.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/** 全局的异常处理 */
@ControllerAdvice
public class WebExceptionResolver {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@ExceptionHandler(value = Exception.class)
	public ModelAndView resolveException(Exception e) {
		// 日志记录
		logger.error(e.getMessage(), e);
		// 返回错误页面
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		return mv;
	}

}
