package com.cg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cg.dao.EmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.google.gson.Gson;

@Controller
/*
 * I annotated Create Employee Controller with @SessionAttributes to put the
 * same model attribute (myRequestObject) in Spring session.
 */
@SessionAttributes({ "UserId", "UserName", "totalEmp", "EmpList", "stateList",
		"designationList" })
public class EmployeeController {

	@Autowired
	EmployeeDAO employeeDAOimpl;

	@RequestMapping(value = "/createEmployee")
	// We use request mapping with class definition to create the base URI
	public String createNewEmpPage(ModelMap model) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		// here we can add any Collection Objects to ModelMap
		// including JSON, String, Array, Map, List, etc...
		model.addAttribute("employeeDTO", employeeDTO);
		model.addAttribute("designationList",
				employeeDAOimpl.loadActiveDesignation());
		model.addAttribute("StateList", employeeDAOimpl.loadActiveState());
		return "CreateEmployee";
	}

	@RequestMapping(value = "/saveEmployee", method = { RequestMethod.POST,
			RequestMethod.GET })
	/*
	 * @RequestMapping annotation is used to map a particular HTTP request
	 * method (GET/POST) to a specific class/method in controller which will
	 * handle the respective request.
	 */
	public String newEmployee(
			@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO) {

		if (employeeDAOimpl.checkDuplicate(employeeDTO.getUserId())) {
			employeeDAOimpl.addEmployee(employeeDTO);
			return "EmployeeList";
		}

		else
			return "CreateEmployee";
	}

	@RequestMapping(value = "/empList", method = RequestMethod.GET)
	public String empList(ModelMap model) {
		model.addAttribute("EmpList", employeeDAOimpl.employeeList());
		return "EmployeeList";
	}

	@RequestMapping("/TotalEmployeeListGrid")
	@ResponseBody
	public String TotalEmployeeListActivation(ModelMap model) {

		return setView();
	}

	/*
	 * update Employee method is to update the designation ie to activate or to
	 * inactivate or to delete
	 */

	@RequestMapping(value = "/updateEmployee", method = { RequestMethod.POST })
	@ResponseBody
	public String updateEmployee(@RequestParam("employeeData") int employeeId,
			@RequestParam("employeeStatus") String employeeStatus) {

		if ("AC".equals(employeeStatus))
			employeeStatus = "IN";
		else
			employeeStatus = "AC";

		employeeDAOimpl.updateEmployee(employeeId, employeeStatus);
		return setView();
	}

	@RequestMapping(value = "/deleteEmployee", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteEmployee(@RequestParam("employeeData") int employeeId) {
		employeeDAOimpl.updateEmployee(employeeId, "DE");
		return setView();
	}

	/* TotalEmployeeList is the URI for which this controller will be used. */
	@RequestMapping("/TotalEmployeeList")
	public String totalEmployeeList(ModelMap model) {

		return "EmployeeActivation";
	}

	// this method is used to save details of employee after submit form
	@RequestMapping(value = "/saveForm", method = RequestMethod.POST)
	protected String saveDetails(ModelMap model_map,
			@ModelAttribute("Employee") final EmployeeDTO employee)
			throws IOException {
		employeeDAOimpl.update(employee);
		/*
		 * List<EmployeeDTO> employee1 = updateEmpDAO.findAll();
		 * model_map.addAttribute("employee1", employee1);
		 */
		return "EmployeeList";
	}

	// this method takes 'id' as request parameter and retrieves the all the
	// state's & designation's details ,employee's details having emp_Id='id'
	// and displays on the updateEmployee.jsp
	@RequestMapping(value = "/editDetail", method = { RequestMethod.GET })
	protected String editDetails(
			ModelMap model_map,
			@ModelAttribute("Employee") EmployeeDTO employee,
			@RequestParam(value = "emp_state", required = false) String emp_state,
			@RequestParam(value = "id", required = false) final String[] id)
			throws IOException {
		employee = employeeDAOimpl.findById(Integer.parseInt(id[0]));
		model_map.addAttribute("employee1", employee);
		List<StateCityDTO> state = new ArrayList<StateCityDTO>();
		state = employeeDAOimpl.findState();
		Map<String, String> stateMap = new LinkedHashMap<String, String>();
		for (StateCityDTO s : state) {
			if (!stateMap.containsKey(s.getStateCityId()))
				stateMap.put(s.getStateCityId().toString(),
						s.getStateCityName());
		}
		model_map.addAttribute("stateOption", stateMap);
		List<DesignationDTO> desig = new ArrayList<DesignationDTO>();
		desig = employeeDAOimpl.findDesig();
		model_map.addAttribute("desig", desig);
		return "UpdateEmployee";
	}

	// this method is used to display cities according to the selected state
	@RequestMapping(value = "/cityDetail", method = { RequestMethod.POST })
	@ResponseBody
	protected String cityDetails(
			ModelMap model_map,
			@RequestParam(value = "emp_state", required = false) String emp_state)
			throws IOException {
		Gson gson = new Gson();
		List<StateCityDTO> city = new ArrayList<StateCityDTO>();

		city = employeeDAOimpl.findCity(emp_state);

		HashMap<String, String> hm = new HashMap<String, String>();
		for (StateCityDTO s : city) {
			if (!hm.containsKey(s.getStateCityId()))
				hm.put("label", s.getStateCityName());
			hm.put("value", s.getStateCityId().toString());
		}
		String json = "";
		if (hm.isEmpty() == false) {
			json = gson.toJson(hm);
		}
		return json;
	}

	@RequestMapping(value = "/DeleteEmployee", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String DeleteEmployee(@RequestParam("employeeId") String employeeId) {

		employeeDAOimpl.deleteEmployee(Integer.parseInt(employeeId));
		return "EmployeeList";
	}

	@RequestMapping(value = "/employeeListGrid", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public String employeeListGrid() {
		return setView();
	}

	public String setView() {
		ArrayList<EmployeeDTO> empdet = (ArrayList<EmployeeDTO>) employeeDAOimpl
				.totalEmployeeList();
		Gson gson = new Gson();
		String gsonFormat = gson.toJson(empdet);
		return gsonFormat;
	}

}
