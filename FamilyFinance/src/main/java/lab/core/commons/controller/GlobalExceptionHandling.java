package lab.core.commons.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.core.commons.vo.LocalBusinessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */

@ControllerAdvice
public class GlobalExceptionHandling {
	public final static int BIZ_ERROR = 380;
	private static final Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandling.class);

	@ExceptionHandler(LocalBusinessException.class)
	public ModelAndView handleEmployeeNotFoundException(
			HttpServletRequest request, Exception ex,HttpServletResponse response) {
		logger.error("Requested URL=" + request.getRequestURL());
		logger.error("全局异常处理：", ex);
		
		//设置错误消息体
		ModelAndView mav = new ModelAndView(); 
		mav.addObject("msg", ex.getMessage());
		mav.setViewName( "commons/portal/globalError");
		
		//设置状态码
		response.setStatus(BIZ_ERROR); 
		return mav;
	}
}
