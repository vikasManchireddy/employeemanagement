package com.cg.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;




import com.cg.dao.DashboardDAO;
import com.cg.dao.UserEmployeeDAO;


/**
 * <h1>Dashboard CONTROLLER</h1>
 * <P>
 * 
 *This controller will dispaly Employee Number who are waiting for approval
 *
 * </P>
 * 
 * @author Prasoona Istari & Vinay 
 * @version 1.0
 * @since 2016-05-02
 */

@Controller
@SessionAttributes({"EmpInactiveNumber" })
public class DashboardControllerHelper {

	@Autowired(required = true)
	DashboardDAO dashboardDAOimpl;

	

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(ModelMap model) {
		model.addAttribute("EmpInactiveNumber", dashboardDAOimpl.dashbord());

		return "Dashboard";
	}

}
