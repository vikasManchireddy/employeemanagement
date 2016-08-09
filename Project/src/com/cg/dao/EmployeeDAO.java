package com.cg.dao;

import java.util.List;

import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;

/**
 * <h1>Create Employee DAO</h1>
 * <P>
 * This is the Employee DAO class
 * </P>
 * 
 * @author NEHA ERSAVADLA
 * @version 1.0
 * @since 2016-05-02
 */

/*
 * EmployeeDAO is an object/interface, which is used to access data from
 * database of data storage
 */
public interface EmployeeDAO {
	public List<StateCityDTO> loadCity();

	public List<DesignationDTO> loadActiveDesignation();

	public List<StateCityDTO> loadActiveState();

	public boolean checkDuplicate(String userId);

	public void addEmployee(EmployeeDTO employeeDTO);

	public List<EmployeeDTO> employeeList();

	public void updateEmployee(int employeeId, String employeeStatus);

	public List<EmployeeDTO> totalEmployeeList();

	public EmployeeDTO findById(int id);

	public void update(EmployeeDTO employee);

	public List<EmployeeDTO> findAll();

	public List<StateCityDTO> findState();

	public List<DesignationDTO> findDesig();

	public List<StateCityDTO> findCity(String stateId);

	public List<StateCityDTO> findAllCity(String empState);
	
	public void deleteEmployee(int employeeId);

}
