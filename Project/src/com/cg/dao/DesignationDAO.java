package com.cg.dao;

import java.util.List;

import com.cg.model.DesignationDTO;



public interface DesignationDAO {

	
	
	public List<DesignationDTO> loadDesignation();
	public List<DesignationDTO> addDesignation(String designation);
	public List<DesignationDTO> updateNewDesignation(String designation, int designationId);
	public void updateDesignation(int designationId, String designationStatus);

	

	
}
