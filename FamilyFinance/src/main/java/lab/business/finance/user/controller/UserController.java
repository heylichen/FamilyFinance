package lab.business.finance.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/finance")
public class UserController   { 
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);
	/*@Autowired
	private UserService userService;
	
	*//**
	 * 保存用户
	 * @param user
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	JSONResponse add(User user, HttpServletRequest request) {
		JSONResponse result = new JSONResponse();
		  
		userService.saveOrUpdate(user);
		 
		return result;
	}*/

	 
}
