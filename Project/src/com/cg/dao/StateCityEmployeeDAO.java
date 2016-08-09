package com.cg.dao;

import java.util.List;

import com.cg.model.StateCityDTO;
/**
 * <h1>Create Employee DAO </h1>
 * <P>
 * This is the Employee DAO  class
 * </P>
 * 
 * @author monika,swathi
 * @version 1.0
 * @since 2016-05-02
 */


/*EmployeeDAO is an object/interface, which is used to access data from database of data storage*/

public interface StateCityEmployeeDAO {

	public List<StateCityDTO> loadState();
	public List<StateCityDTO> loadCity();
	public List<StateCityDTO> loadActiveState();
	public List<StateCityDTO> addState(String state);
	public void updateState(int stateCityId ,String status);
	public List<StateCityDTO> updateNewCity(String cityName,int cityId);
	public List<StateCityDTO> addCity(int stateId,String city);
	public void makeActiveCity(int stateCityId);
	public void makeInactiveCity(int stateCityId);
	public void deleteCity(int stateCityId);
	public List<StateCityDTO> updateNewState(String state, int stateId);
	
}