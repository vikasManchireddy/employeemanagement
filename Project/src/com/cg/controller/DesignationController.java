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

import com.cg.dao.DesignationDAO;
import com.cg.model.DesignationDTO;
import com.google.gson.Gson;

@Controller
@SessionAttributes({ "designationList" })
public class DesignationController {

	@Autowired
	DesignationDAO designationDAOimpl;

	/* TotalDesignationList is the URI for which this controller will be used. */

	@RequestMapping("/TotalDesignationList")
	public String TotalDesignationList(ModelMap model) {

		return "DesignationActivation";
	}

	@RequestMapping("/TotalDesignationListGrid")
	@ResponseBody
	public String totalDesignationList(ModelMap model) {

		return setView();
	}

	/*
	 * 
	 * Save Designation is to save a new Designation from the Jsp Page using
	 * Ajax and Storing it into the database
	 */

	@RequestMapping(value = "/saveDesignation", method = RequestMethod.POST)
	@ResponseBody
	public String saveDesignation(
			@RequestParam("designationData") String designation) {
		System.out.println("iam in save designann");
		designationDAOimpl.addDesignation(designation);
		return "DesignationActivation";
	}

	/*
	 * Modify the existing designation using updateNewDesignation Method
	 */
	// updateNewDesignation
	@RequestMapping(value = "/updateNewDesignation", method = RequestMethod.POST)
	@ResponseBody
	public String updateNewDesignation(
			@RequestParam("designationData") String designation,
			@RequestParam("designationId") int designationId) {
		designationDAOimpl.updateNewDesignation(designation, designationId);
		return "DesignationActivation";
	}

	/*
	 * update designation method is to update the designation ie to actvate or
	 * to inactivate or to delete
	 */

	// updateDesignation
	@RequestMapping(value = "/updateDesignation", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDesignation(
			@RequestParam("designationData") int designationId,
			@RequestParam("designationStatus") String designationStatus) {
		if ("AC".equals(designationStatus))
			designationStatus = "IN";
		else
			designationStatus = "AC";
		designationDAOimpl.updateDesignation(designationId, designationStatus);
		return setView();
	}

	@RequestMapping(value = "/deleteDesignation", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteState(@RequestParam("designationData") int designationId) {
		designationDAOimpl.updateDesignation(designationId, "DE");
		return setView();
	}

	public String setView() {

		List<DesignationDTO> designationList = designationDAOimpl
				.loadDesignation();
		Gson gson = new Gson();
		String gsonFormat = gson.toJson(designationList);
		System.out.println(gsonFormat);
		return gsonFormat;
	}

}
