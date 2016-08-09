package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.dao.EmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.cg.util.JDBCDaoException;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;

@Repository("employeeDAOimpl")
public class EmployeeDAOimpl implements EmployeeDAO {

	private DataSource dataSource;
	private Connection connection;
	PreparedStatement selectStatement;
	private String selectQuery;
	private ResultSet result;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
		// this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.setNamedParameterJdbcTemplate(new NamedParameterJdbcTemplate(
				dataSource));
	}
	
	public EmployeeDAOimpl() {

		try {
			dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");

		} catch (ServiceLocatorException e) {

			System.out.println("Container Service not available");
		}// catch block closed

	} // method closed

	public List<StateCityDTO> loadCity() {

		List<StateCityDTO> stateCityDetails = new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;

		selectQuery = "SELECT * FROM state_city where NOT city_parent_id = 0";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				stateCity = new StateCityDTO();
				stateCity.setCityParentId(result.getInt("city_parent_id"));
				stateCity.setStateCityId(result.getInt("state_city_Id"));
				stateCity.setStateCityName(result.getString("state_city_name"));
				stateCity.setStateCityStatus(result
						.getString("state_city_status"));
				stateCityDetails.add(stateCity);
			}
			return stateCityDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}// loadCity() closed
	
	
	public List<DesignationDTO> loadActiveDesignation() {

		List<DesignationDTO> desginationDetails = new ArrayList<DesignationDTO>();
		DesignationDTO desginationDTO;

		selectQuery = "SELECT * FROM  designation where desig_status='AC'";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				desginationDTO = new DesignationDTO();
				desginationDTO.setDesignationId(result.getInt("desig_Id"));
				desginationDTO.setDesignationName(result
						.getString("design_name"));
				desginationDTO.setDesignationStatus(result
						.getString("desig_status"));
				desginationDetails.add(desginationDTO);

			}
			return desginationDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public List<EmployeeDTO> employeeList() {
		List<EmployeeDTO> empDetails = new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery = "select f_name,l_name,kin_id,emp_edu from tempempdet where emp_status='AC' and emp_role='E'";
		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);

			result = selectStatement.executeQuery();

			for (; result.next();) {
				emp = new EmployeeDTO();
				emp.setFirstName(result.getString("f_name"));
				emp.setLastName(result.getString("l_name"));
				emp.setKinId(result.getString("kin_id"));
				emp.setEducation(result.getString("emp_edu"));
				empDetails.add(emp);
			}

			return empDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}// employeeList() closed

	

	public List<StateCityDTO> loadActiveState() {

		List<StateCityDTO> stateCityDetails = new ArrayList<StateCityDTO>();
		StateCityDTO stateCity;

		selectQuery = "SELECT * FROM state_city where city_parent_id=0 and state_city_status='AC'";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			while (result.next()) {
				stateCity = new StateCityDTO();
				stateCity.setCityParentId(result.getInt("city_parent_id"));
				stateCity.setStateCityId(result.getInt("state_city_Id"));
				stateCity.setStateCityName(result.getString("state_city_name"));
				stateCity.setStateCityStatus(result
						.getString("state_city_status"));
				stateCityDetails.add(stateCity);
			}
			return stateCityDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void addEmployee(EmployeeDTO employeeDTO) {

		selectQuery = "insert into tempempdet(f_name,l_name,kin_id,emp_edu,designation,emp_state,emp_city,emp_gender,emp_role,emp_status) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);

			selectStatement.setString(1, employeeDTO.getFirstName());
			selectStatement.setString(2, employeeDTO.getLastName());
			selectStatement.setString(3, employeeDTO.getKinId());
			selectStatement.setString(4, employeeDTO.getEducation());
			selectStatement.setString(5, employeeDTO.getDesignation());
			selectStatement.setInt(6, employeeDTO.getState());
			selectStatement.setInt(7, employeeDTO.getCity());
			selectStatement.setString(8, employeeDTO.getGender());
			selectStatement.setString(9, "E");
			selectStatement.setString(10, "IN");

			boolean n = selectStatement.execute();

			System.out.println(n);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	@Override
	public boolean checkDuplicate(String userId) {
		PreparedStatement stmt = null;
		String checkId = "select * from login_details where user_Id=?";
		boolean result = true;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(true);
			stmt = connection.prepareStatement(checkId);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				result = false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<EmployeeDTO> totalEmployeeList() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<EmployeeDTO> empDetails = new ArrayList<EmployeeDTO>();
		EmployeeDTO emp;
		selectQuery = "select * from tempempdet where  emp_role='E' and emp_status!='DE'";
		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);

			result = selectStatement.executeQuery();

			for (; result.next();) {
				emp = new EmployeeDTO();
				emp.setFirstName(result.getString("f_name"));
				emp.setLastName(result.getString("l_name"));
				emp.setKinId(result.getString("kin_id"));
				emp.setEmployeeRole(result.getString("emp_role"));
				emp.setEmployeeId(result.getInt("EMP_ID"));
				emp.setStatus(result.getString("emp_status"));
				emp.setDesignation(result.getString("designation"));
				empDetails.add(emp);
			}

			return empDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void updateEmployee(int employeeId, String status) {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		selectQuery = "update tempempdet SET emp_status=? where EMP_ID=?";
		try {

			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);
			selectStatement.setString(1, status);
			selectStatement.setInt(2, employeeId);
			selectStatement.execute();
			System.out.println("db updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	
	// this method is used for closing connections,statement,preparedStatement
		// and resultSet
		public void close(final Connection connection,
				final PreparedStatement statement, final ResultSet result,
				final Statement statement2) {
			Connection connection1 = connection;
			PreparedStatement statement1 = statement;
			ResultSet result1 = result;
			Statement statement3 = statement2;
			if (connection1 != null) {
				try {
					connection1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement1 != null) {
				try {
					statement1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (result1 != null) {
				try {
					result1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement3 != null) {
				try {
					statement3.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// this method uses the Employee object as parameter to update the
		// Emp_detail table
		@Override
		public void update(final EmployeeDTO employee) {
			// TODO Auto-generated method stub
			String updateQuery = "update tempempdet set f_name=?,l_name=?,kin_id=?,emp_edu=?,emp_state=?,emp_city=?,emp_gender=?,designation=? where EMP_ID=?";
			Connection connection = null;
			PreparedStatement updateStatement = null;

			try {
				try {
					connection = dataSource.getConnection();
					connection.setAutoCommit(false);
					updateStatement = connection.prepareStatement(updateQuery);
					updateStatement.setString(1, employee.getFirstName());
					updateStatement.setString(2, employee.getLastName());
					updateStatement.setString(3, employee.getKinId());
					updateStatement.setString(4, employee.getEducation());
					updateStatement.setInt(5, employee.getState());
					updateStatement.setInt(6, employee.getCity());
					updateStatement.setString(7, employee.getGender());
					updateStatement.setString(8, employee.getDesignation());
					updateStatement.setInt(9, employee.getEmployeeId());
					updateStatement.executeUpdate();
					connection.commit();
				} catch (SQLException e) {
					if (connection != null)
						connection.rollback();

					throw e;
				} finally {
					close(connection, updateStatement, null, null);
				}
			} catch (SQLException e) {
				throw new JDBCDaoException("SQL error while excecuting query: "
						+ updateQuery, e);
			}

		}

		// this method takes id as a parameter to find the details of the selected
		// employee
		@Override
		public EmployeeDTO findById(final int id) {
			Connection connection = null;
			String selectQuery = "select * from tempempdet where EMP_ID=?";
			EmployeeDTO employee = new EmployeeDTO();
			PreparedStatement selectStatement = null;
			ResultSet result = null;
			try {
				try {
					connection = dataSource.getConnection();
					connection.setAutoCommit(true);

					selectStatement = connection.prepareStatement(selectQuery);
					selectStatement.setInt(1, id);
					result = selectStatement.executeQuery();
					result.next();
					employee.setEmployeeId(result.getInt("Emp_Id"));
					employee.setFirstName(result.getString("f_name"));
					employee.setLastName(result.getString("l_name"));
					employee.setKinId(result.getString("kin_id"));
					employee.setEducation(result.getString("emp_edu"));
					// employee.setDesignationId(result.getInt("desig_Id"));
					employee.setState(result.getInt("emp_state"));
					employee.setCity(result.getInt("emp_city"));
					if (result.getString("emp_gender").equalsIgnoreCase("F")) {
						employee.setGender("F");
					} else {
						employee.setGender("M");
					}

				} finally {

					close(connection, selectStatement, result, null);
				}
			} catch (SQLException e) {

				throw new JDBCDaoException("SQL error while excecuting query: "
						+ selectQuery, e);
			}

			return employee;
		}

		// this method retrieve all the employee's detail from the database
		@Override
		public List<EmployeeDTO> findAll() {
			List<EmployeeDTO> employee1 = new ArrayList<EmployeeDTO>();
			ResultSet result = null;
			// PreparedStatement selectStatement=null;
			Connection connection = null;
			Statement selectStatement = null;
			String selectQuery = "select * from tempempdet where  emp_role='E'";
			List<EmployeeDTO> empDetails = new ArrayList<EmployeeDTO>();
			EmployeeDTO emp;
			
			try {
				connection = dataSource.getConnection();

				selectStatement = connection.prepareStatement(selectQuery);

				result = selectStatement.executeQuery(selectQuery);

				for (; result.next();) {
					emp = new EmployeeDTO();
					emp.setFirstName(result.getString("f_name"));
					emp.setLastName(result.getString("l_name"));
					emp.setKinId(result.getString("kin_id"));
					emp.setEmployeeRole(result.getString("emp_role"));
					emp.setEmployeeId(result.getInt("EMP_ID"));
					empDetails.add(emp);
				}

				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return empDetails;
		}

		// this method retrieve all the State's detail from the database
		@Override
		public List<StateCityDTO> findState() {
			// TODO Auto-generated method stub
			List<StateCityDTO> state = null;
			ResultSet result = null;
			Statement selectStatement = null;

			String findState = "select * from state_city where state_city_status='AC' AND city_parent_id=0";
			Connection connection = null;

			try {
				try {
					connection = dataSource.getConnection();
					connection.setAutoCommit(true);
					selectStatement = connection.createStatement();

					result = selectStatement.executeQuery(findState);
					if (result.wasNull() == false) {
						state = new ArrayList<StateCityDTO>();
					}
					while (result.next()) {
						StateCityDTO StateCityDTO = new StateCityDTO();
						StateCityDTO.setCityParentId(result
								.getInt("city_parent_id"));
						StateCityDTO.setStateCityId(result
								.getInt("state_city_Id"));
						StateCityDTO.setStateCityName(result
								.getString("state_city_name"));
						StateCityDTO.setStateCityStatus(result
								.getString("state_city_status"));
						state.add(StateCityDTO);
					}
				} finally {
					close(connection, null, result, selectStatement);
				}
			} catch (SQLException e) {
				throw new JDBCDaoException("SQL error while excecuting query: "
						+ findState, e);
			}

			return state;

		}

		// this method retrieve all the designation's detail from the database
		@Override
		public List<DesignationDTO> findDesig() {
			// TODO Auto-generated method stub
			List<DesignationDTO> designation = null;
			ResultSet result = null;
			Statement selectStatement = null;

			String findDesig = "select * from designation where desig_status='AC'";
			Connection connection = null;

			try {
				try {
					connection = dataSource.getConnection();
					connection.setAutoCommit(true);
					selectStatement = connection.createStatement();

					result = selectStatement.executeQuery(findDesig);

					if (result.wasNull() == false) {
						designation = new ArrayList<DesignationDTO>();
					}

					while (result.next()) {
						DesignationDTO desig = new DesignationDTO();
						desig.setDesignationId(result.getInt("desig_Id"));
						desig.setDesignationStatus(result.getString("desig_status"));
						desig.setDesignationName(result.getString("design_name"));
						designation.add(desig);
					}
				} finally {
					close(connection, null, result, selectStatement);
				}
			} catch (SQLException e) {
				throw new JDBCDaoException("SQL error while excecuting query: "
						+ findDesig, e);
			}

			return designation;

		}

		public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
			return namedParameterJdbcTemplate;
		}

		public void setNamedParameterJdbcTemplate(
				NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		}

		@Override
		public List<StateCityDTO> findCity(String stateId) {
			// TODO Auto-generated method stub
			Connection connection = null;
			PreparedStatement selectStatement = null;
			ResultSet result = null;
			List<StateCityDTO> city1 = null;

			String selectQuery = "select * from  state_city where city_parent_id=? AND state_city_status='AC'";
			StateCityDTO city;

			try {
				try {
					connection = dataSource.getConnection();
					connection.setAutoCommit(true);

					selectStatement = connection.prepareStatement(selectQuery);
					selectStatement.setInt(1, Integer.parseInt(stateId));
					result = selectStatement.executeQuery();
					if (result.wasNull() == false)
						city1 = new ArrayList<StateCityDTO>();
					while (result.next()) {
						city = new StateCityDTO();
						// city.setCity_parent_id(result.getInt("city_parent_id"));
						city.setStateCityId(result.getInt("state_city_Id"));
						city.setStateCityName(result.getString("state_city_name"));
						// city.setStateCityDTO_status("StateCityDTO_status");
						city1.add(city);
					}

				} finally {

					close(connection, selectStatement, result, null);
				}
			} catch (SQLException e) {

				throw new JDBCDaoException("SQL error while excecuting query: "
						+ selectQuery, e);
			}

			return city1;
		}

		@Override
		public List<StateCityDTO> findAllCity(String empState) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		
		//Delete Employee
		@SuppressWarnings("resource")
		public void deleteEmployee(int employeeId)
		{
			
			/**
			 * <p>deleteEmployee method takes employee id from welcome page controller and changes the AC to DE 
			 * and updates in the database.
			 * </p>
			 * 
			 */
			
			
			selectQuery="update tempempdet SET emp_status=? where EMP_ID=?";
			try {
				
				connection=dataSource.getConnection();
				
				selectStatement = connection.prepareStatement(selectQuery);
				selectStatement.setString(1,"DE");
				
				selectStatement.setInt(2,employeeId);
				selectStatement.execute();
				
				System.out.println(" delete employee db updated");
				
				
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}

		
		

}