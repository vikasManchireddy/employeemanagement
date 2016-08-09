package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cg.dao.StateCityEmployeeDAO;
import com.cg.model.StateCityDTO;
import com.google.gson.Gson;

/**
 * <h1>Create Controller</h1>
 * 
 * @author monika,swathi
 * @version 1.0
 * @since 2016-05-02
 */

@Controller
@SessionAttributes({ "userId", "UserName", "totalEmp", "EmpList", "stateList",
		"designationList", "cityList" })
public class StateCityControllerHelper {

	@Autowired
	StateCityEmployeeDAO stateCityEmployeeDAOimpl;

	/* /TotalStateList is the URI for which this controller will be used. */
	@RequestMapping("/TotalStateList")
	public String stateList(ModelMap model) {

		model.addAttribute("stateList", stateCityEmployeeDAOimpl.loadState());
		return "StateActivation";
	}

	/* /TotalCityList is the URI for which this controller will be used. */

	@RequestMapping("/TotalCityList")
	public String cityList(ModelMap model) {

		model.addAttribute("stateList", stateCityEmployeeDAOimpl.loadState());
		model.addAttribute("cityList", stateCityEmployeeDAOimpl.loadCity());
		return "CityActivation";
	}

	/*
	 * Save state is to save a new state into a database form the JSP page
	 */

	@RequestMapping(value = "/saveState", method = RequestMethod.POST)
	@ResponseBody
	public String addState(@RequestParam("stateData") String state) {

		System.out.println(state);
		stateCityEmployeeDAOimpl.addState(state);
		return "StateActivation";
		// return setView1();
	}

	/*
	 * updateNewState This method will process request based on action performed
	 * on screen.
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param response HttpServletResponse
	 */

	@RequestMapping(value = "/updateNewState", method = RequestMethod.POST)
	@ResponseBody
	public String updateNewState(@RequestParam("stateData") String state,
			@RequestParam("stateId") int stateId) {

		System.out.println(state);
		System.out.println(stateId);
		stateCityEmployeeDAOimpl.updateNewState(state, stateId);
		return "StateActivation";
		// return setView1();
	}

	/*
	 * state update
	 */

	@RequestMapping(value = "/updateState", method = { RequestMethod.POST })
	@ResponseBody
	public String updateState(@RequestParam("stateData") int stateId,
			@RequestParam("stateStatus") String stateStatus) {
		System.out.println("in updatestate controller");
		System.out.println(stateId);
		System.out.println("before: " + stateStatus);
		if ("AC".equals(stateStatus))
			stateStatus = "IN";
		else
			stateStatus = "AC";

		System.out.println("After: " + stateStatus);

		stateCityEmployeeDAOimpl.updateState(stateId, stateStatus);
		return setView1();
	}

	/*
	 * state Delete
	 */

	@RequestMapping(value = "/deleteState", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteState(@RequestParam("stateData") int stateId) {
		System.out.println("in updatestate controller");
		System.out.println(stateId);
		stateCityEmployeeDAOimpl.updateState(stateId, "DE");
		return setView1();
	}

	/*
	 * Save city is to save a new city into a database form the JSP page
	 */

	@RequestMapping(value = "/saveCity", method = RequestMethod.POST)
	@ResponseBody
	public String addCity(@RequestParam("stateId") int stateId,
			@RequestParam("cityData") String city) {

		System.out.println(stateId);
		System.out.println(city);
		stateCityEmployeeDAOimpl.addCity(stateId, city);
		return "CityActivation";
		// return setView();
	}

	/*
	 * updateNewState This method will process request based on action performed
	 * on screen.
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param response HttpServletResponse
	 */
	@RequestMapping(value = "/updateNewCity", method = RequestMethod.POST)
	@ResponseBody
	public String cityUpdate1(@RequestParam("hiddenValue") int stateCityID,
			@RequestParam("cityData") String cityData) {
		stateCityEmployeeDAOimpl.updateNewCity(cityData, stateCityID);
		return "CityActivation";
		// return setView();
	}

	/*
	 * city updation
	 */

	@RequestMapping(value = "/updateCity", method = RequestMethod.POST)
	@ResponseBody
	public String updateCity(@RequestParam("cityData") int cityId,
			@RequestParam("cityStatus") String cityStatus) {

		System.out.println(cityId);
		System.out.println(cityStatus);
		stateCityEmployeeDAOimpl.updateState(cityId, cityStatus);
		return setView();
	}

	@RequestMapping(value = "/stateListGrid", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public String employeeListGrid() {
		return setView1();
	}

	@RequestMapping(value = "/cityListGrid", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public String employeeListGrid1() {
		return setView();
	}

	public String setView1() {
		List<StateCityDTO> stateList = stateCityEmployeeDAOimpl.loadState();

		Gson gson = new Gson();

		String gsonFormat = gson.toJson(stateList);

		System.out.println("gson  " + gsonFormat);
		return gsonFormat;
	}

	public String setView() {
		List<StateCityDTO> cityList = stateCityEmployeeDAOimpl.loadCity();

		Gson gson = new Gson();

		String gsonFormat = gson.toJson(cityList);

		System.out.println("gson  " + gsonFormat);
		return gsonFormat;
	}
}
