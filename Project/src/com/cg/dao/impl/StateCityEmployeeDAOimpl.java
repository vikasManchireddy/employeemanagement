package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.StateCityEmployeeDAO;
import com.cg.model.StateCityDTO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;

@Repository("stateCityEmployeeDAOimpl")
public class StateCityEmployeeDAOimpl implements StateCityEmployeeDAO{

private DataSource dataSource;
private Connection connection;
PreparedStatement selectStatement;
private String selectQuery;
private ResultSet result ;
	
	public StateCityEmployeeDAOimpl() {
		
		try {
			dataSource=ServiceLocator.getDataSource("jdbc/VIMDataSource");
			
		} catch (ServiceLocatorException e) {
			
			System.out.println("Container Service not available");
		}
		
	}

	/*
	 * this method is to load states as a list in to the jsp page.
	 * 
	 * 
	 * 
	 */
	public List<StateCityDTO> loadState(){
		
		List<StateCityDTO> stateCityDetails=new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;
		
		selectQuery="SELECT * FROM state_city where city_parent_id=0 and state_city_status!='DE'" ;
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();
			
		
			int x = 1;
		while(result.next()){
			
			 stateCity=new StateCityDTO();
			 stateCity.setSlNo(x);
			 stateCity.setCityParentId(result.getInt("city_parent_id"));
			 stateCity.setStateCityId(result.getInt("state_city_Id"));
			 stateCity.setStateCityName(result.getString("state_city_name"));
			 stateCity.setStateCityStatus(result.getString("state_city_status"));
			 stateCityDetails.add(stateCity);
			 x++;
		 }
		return stateCityDetails;
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
}
	
	/*
	 * this method is to load list of cities in to jsp pages
	 * 
	 * 
	 */
	
	public List<StateCityDTO> loadCity(){
		
		List<StateCityDTO> stateCityDetails=new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;
		
		selectQuery="SELECT * FROM state_city where NOT city_parent_id = 0 and  state_city_status!='DE'";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();
		
			int x = 1;
		while(result.next()){
			 stateCity=new StateCityDTO();
			 stateCity.setSlNo(x);
			 stateCity.setCityParentId(result.getInt("city_parent_id"));
			 stateCity.setStateCityId(result.getInt("state_city_Id"));
			 stateCity.setStateCityName(result.getString("state_city_name"));
			 stateCity.setStateCityStatus(result.getString("state_city_status"));
			 stateCityDetails.add(stateCity);
			 x++;
		 }
		return stateCityDetails;
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
}
	
	/*
	 * 
	 * 
	 * This method is to loadActiveState in to the jsp pages.
	 * 
	 * 
	 */
	
	
	public List<StateCityDTO> loadActiveState(){
	
	List<StateCityDTO> stateCityDetails=new ArrayList<StateCityDTO>();
	StateCityDTO stateCity;
	
	selectQuery="SELECT * FROM state_city where city_parent_id=0 and state_city_status='IN'";
	try {
		
		connection=dataSource.getConnection();
		selectStatement = connection.prepareStatement(selectQuery);
		result = selectStatement.executeQuery();
	
		
	while(result.next()){
		 stateCity=new StateCityDTO();
		 stateCity.setCityParentId(result.getInt("city_parent_id"));
		 stateCity.setStateCityId(result.getInt("state_city_Id"));
		 stateCity.setStateCityName(result.getString("state_city_name"));
		 stateCity.setStateCityStatus(result.getString("state_city_status"));
		 stateCityDetails.add(stateCity);
	 }
	return stateCityDetails;
	}
	catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
		return null;
	
	}
	
/* 
 * State addition to database and State updation methods 	
 */
	/*
	 * State addition to database
	 */
	public List<StateCityDTO> addState(String state){
		System.out.println("iam in add designation dao impl");
		System.out.println(state);
		selectQuery="insert into state_city(city_parent_id,state_city_name,state_city_status) values(?,?,?)";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setInt(1,0);
			selectStatement.setString(2,state);
			selectStatement.setString(3,"IN");
			selectStatement.execute();
			loadState();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return null;
	}

	//updateNewState
	
	public List<StateCityDTO> updateNewState(String state,int stateId){
		System.out.println("iam in add  new state dao impl");
		System.out.println(state);
		selectQuery="update state_city set state_city_name=? where state_city_Id=?";//(city_parent_id,state_city_name,state_city_status) values(?,?,?)";
		try {
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,state);
			selectStatement.setInt(2,stateId);
			selectStatement.execute();
			loadState();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return null;
	}

	/*
	 * state Updation(ie to activate , inactivate and to delete) to database
	 */
	public void updateState(int stateCityId ,String status)
	{
		selectQuery="update state_city SET state_city_status=? where state_city_Id=?";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,status);
			selectStatement.setInt(2,stateCityId);
			selectStatement.execute();
			System.out.println("db updated");
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	
	/* 
	 * City addition to database and City updation methods 	
	 */
		/*
		 * City addition to database
		 */

	public List<StateCityDTO> updateNewCity(String cityName,int cityId){
		
		selectQuery="update state_city set state_city_name=? where state_city_Id=?";//(city_parent_id,state_city_name,state_city_status) values(?,?,?)";
		try {
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,cityName);
			selectStatement.setInt(2,cityId);
			selectStatement.execute();
			loadState();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return null;
	}
	
	
	public List<StateCityDTO> addCity(int stateId,String city){
		System.out.println("iam in add city  dao impl");
		selectQuery="insert into state_city(city_parent_id,state_city_name,state_city_status) values(?,?,?)";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setInt(1,stateId);
			selectStatement.setString(2,city);
			selectStatement.setString(3,"IN");
			selectStatement.execute();
			loadCity();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return null;
	}

	public void makeActiveCity(int stateCityId)
	{
		selectQuery="update state_city SET state_city_status=? where state_city_Id=?";
		System.out.println("activeState in dao impl");
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,"AC");
			selectStatement.setInt(2,stateCityId);
			selectStatement.execute();
			System.out.println("db updated");
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	
	public void makeInactiveCity(int stateCityId)
	{
		selectQuery="update state_city SET state_city_status=? where state_city_Id=?";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,"IN");
			selectStatement.setInt(2,stateCityId);
			selectStatement.execute();
			System.out.println("db updated");
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}

	
	public void deleteCity(int stateCityId)
	{
		selectQuery="update state_city SET state_city_status=? where state_city_Id=?";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,"DE");
			selectStatement.setInt(2,stateCityId);
			selectStatement.execute();
			System.out.println(" delete state db updated");
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	

}