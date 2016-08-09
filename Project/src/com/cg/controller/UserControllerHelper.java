package com.cg.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cg.dao.DashboardDAO;
import com.cg.dao.EmployeeDAO;
import com.cg.dao.UserEmployeeDAO;
import com.cg.dao.impl.DashboardDAOimpl;
import com.cg.model.UserDTO;


/**
 * <h1>Login Controller</h1>
 * <P>
 * 
 * This controller will take credentials from User and it will validate the User 
 * 
 *  Result Page will be shown to the User based on validation
 *  
 * </P>
 * 
 * @author Prasoona Istari & Vinay 
 * @version 1.0
 * @since 2016-05-02
 */

@Controller
@SessionAttributes({ "user"})
public class UserControllerHelper {

	@Autowired
	UserEmployeeDAO userEmployeeDAOimpl;
	@Autowired
	DashboardDAO dashboardDAOimpl;
	@Autowired
	EmployeeDAO employeeDAOimpl;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}


	@RequestMapping(value = "/Welcome", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String welcomePage(@RequestParam("userId") String userId,
			@RequestParam("password") String password, ModelMap model) {
		
		if(userId !=null || password !=null)
		
		{
		ArrayList<String> al = (ArrayList<String>) userEmployeeDAOimpl
				.loginValidation(userId, password);

		if (al != null && !(al.isEmpty())) {
			if (al.size() == 0) {
				model.addAttribute("reasonObject", al.get(0));
			} else if (al.size() == 2) {
				
				UserDTO userDTO;
				
				if ("A".equalsIgnoreCase(al.get(1))) {
					
					model.addAttribute("EmpInactiveNumber",
							dashboardDAOimpl.dashbord());
					userDTO=new UserDTO();
					
					userDTO.setUserId(userId);
					userDTO.setUserName(al.get(0));
					model.addAttribute("user", userDTO);
					
					return "Dashboard";
					
				} else if ("I".equalsIgnoreCase(al.get(1))) {
					userDTO=new UserDTO();

					model.addAttribute("EmpList",
							employeeDAOimpl.employeeList());
					
					
					userDTO.setUserId(userId);
					userDTO.setUserName(al.get(0));
					model.addAttribute("user", userDTO);
					
					return "EmployeeList";
				} else {
					model.addAttribute("reasonObject",
							"You are not authorized to login.....!");
					return "login";
				}
			}
		}

		model.addAttribute("reasonObject", "validation failed");
		return "login";

	}
		
		model.addAttribute("reasonObject", " Enter UserId and PassWord...!!!");
		return "login";
	}
	
}
