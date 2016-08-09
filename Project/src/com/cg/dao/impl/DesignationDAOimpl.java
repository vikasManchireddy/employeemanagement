package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.DesignationDAO;
import com.cg.model.DesignationDTO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;


@Repository("designationActivationDAOimpl")
public class DesignationDAOimpl implements DesignationDAO{

	

private DataSource dataSource;

public DesignationDAOimpl() {

	try {
		dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");
	} catch (ServiceLocatorException e) {
		System.out.println("Container Service not available");
	}

}

public   List<DesignationDTO> loadDesignation(){

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;
		List<DesignationDTO> desginationDetails=new ArrayList<DesignationDTO>();
		DesignationDTO desginationDTO;

		selectQuery="SELECT * FROM  designation where desig_status!=?";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,"DE");
			result = selectStatement.executeQuery();
		
			
		while(result.next())
		{
			desginationDTO=new DesignationDTO();
			desginationDTO.setDesignationId(result.getInt("desig_Id"));
			desginationDTO.setDesignationName(result.getString("design_name"));
			desginationDTO.setDesignationStatus(result.getString("desig_status"));
			desginationDetails.add(desginationDTO);
			
		}
		return desginationDetails;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (result != null)
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (selectStatement != null)
				try {
					selectStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return null;
	}
	

	/*
	 * Designation Updation(ie to activate , inactivate and to delete) to databse
	 */
	public List<DesignationDTO> addDesignation(String designation){
		
		
		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;
		
		selectQuery="insert into designation(design_name,desig_status) values(?,?)";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,designation);
			selectStatement.setString(2,"IN");
			selectStatement.execute();
			loadDesignation();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}finally {
				if (result != null)
					try {
						result.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (selectStatement != null)
					try {
						selectStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		return null;
	}
	
	
	public List<DesignationDTO> updateNewDesignation(String designationName,int designationId)
	{
		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;
		
		selectQuery="update designation SET design_name=? where desig_Id=?";//  into designation(design_name,desig_status) values(?,?)";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,designationName);
			selectStatement.setInt(2,designationId);
			selectStatement.execute();
			loadDesignation();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}finally {
				if (result != null)
					try {
						result.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (selectStatement != null)
					try {
						selectStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		return null;
	}
	
	/*
	 * Designation addition to database
	 */
	public void updateDesignation(int designationId ,String status)
	{
		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;
		selectQuery="update designation SET desig_status=? where desig_Id=?";
		try {
			
			connection=dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1,status);
			selectStatement.setInt(2,designationId);
			selectStatement.execute();
			System.out.println("db updated");
		}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		finally {
			if (result != null)
				try {
					result.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (selectStatement != null)
				try {
					selectStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
