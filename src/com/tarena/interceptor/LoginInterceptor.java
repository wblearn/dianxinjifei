package com.tarena.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 *	登录检查拦截器，用于对资费等业务模块进行登录校验
 */
public class LoginInterceptor implements Interceptor {

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation ai) 
		throws Exception {
		Map<String,Object> session = 
			ai.getInvocationContext().getSession();
		//如果登录过，则从session中可以取到admin，否则取不到
		Object admin = session.get("admin");
		if(admin == null) {
			//session中没取到admin，说明没登录过，踢回登录页
			return "login"; //通过login找Result转到登录页
		} else {
			//session中取到admin，说明登录过，可以访问Action
			return ai.invoke();
		}
	}

}