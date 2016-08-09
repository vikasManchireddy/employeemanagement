package com.cg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cg.model.UserDTO;


@Component
public class LoginInterceptorAdapter extends HandlerInterceptorAdapter{
	





	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		 // ignore login page
			
        if ((request.getServletPath().equals("/")) || (request.getServletPath().equals("/Welcome"))) { 
        	
        	System.out.println(request.getServletPath());
        	System.out.println("welcome page at first");
            return true;
        }
        
        else{
        	System.out.println(request.getServletPath());
        	UserDTO user= (UserDTO)request.getSession().getAttribute("user");
        	System.out.println(user);
	        if(user == null)
	        {
	            
	        	
	            response.sendRedirect(request.getContextPath());
	            return false;
	        }
	        else
	        {
	        
	            return true;
	        }
        }
		
	}
	
	

}
