package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.UserEmployeeDAO;
import com.cg.model.DesignationDTO;
import com.cg.model.EmployeeDTO;
import com.cg.model.StateCityDTO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;

@Repository("userEmployeeDAOimpl")
public class UserEmployeeDAOimpl implements UserEmployeeDAO {

	private DataSource dataSource;

	public UserEmployeeDAOimpl() {

		try {
			dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");

		} catch (ServiceLocatorException e) {

			System.out.println("Container Service not available");
		}

	}
	@SuppressWarnings("resource")
	public List<String> loginValidation(String userId, String password) {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<String> arrayList = new ArrayList<String>();
		selectQuery = "select Emp_Id from login_details where user_Id=? and user_pwd=?";

		try {
			connection = dataSource.getConnection();
			selectStatement = connection.prepareStatement(selectQuery);

			selectStatement.setString(1, userId);
			selectStatement.setString(2, password);

			result = selectStatement.executeQuery();

			if (result.next()) {

				System.out.println(result.getInt("Emp_Id"));

				selectQuery = "select emp_role,f_name from tempempdet where Emp_Id=?";

				selectStatement = connection.prepareStatement(selectQuery);
				selectStatement.setInt(1, result.getInt("Emp_Id"));
				result = selectStatement.executeQuery();
				
				if (result.next()) {

					System.out.println(result.getString("emp_role"));

					arrayList.add(0, result.getString("f_name"));
					arrayList.add(1, result.getString("emp_role"));
				}

				else {
					System.out.println("Authorisation failed");
					arrayList.add(0,
							"You are not authorized to login.....! impl ");
					return arrayList;
				}
			}

			else {
				System.out.println("validation failed  impl");
				arrayList.add(0, "Incorrect Username or Password....!  impl");
				return arrayList;
			}
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
		return arrayList;

	}



	// forgot password
	@SuppressWarnings("resource")
	public List<String> forgotPassword(final String kinIdPopup,
			final String userIdPopup)

	{
		List<String> forgotPasswordList = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		Connection connection = null;

		forgotPasswordList = new ArrayList<String>();
		selectQuery = "select Emp_Id,emp_role from tempempdet where kin_id=?";

		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);

			selectStatement.setString(1, kinIdPopup);

			result = selectStatement.executeQuery();

			selectStatement = null;
			

			if (result.next()) {

				System.out
						.println("emp id and employee role when kin id is given is "
								+ result.getInt("Emp_Id")
								+ "-"
								+ result.getString("emp_role"));
				if ("A".equals(result.getString("emp_role"))
						|| "I".equals(result.getString("emp_role"))) {

					selectQuery = "select user_Id,user_pwd from login_details where Emp_Id=?";

					selectStatement = connection.prepareStatement(selectQuery);
					selectStatement.setInt(1, result.getInt("Emp_Id"));
					result = selectStatement.executeQuery();
					selectStatement = null;
					if (result.next()) {

						System.out.println("password for given emp id is "
								+ result.getString("user_pwd"));
						System.out.println("user id for given emp id is "
								+ result.getString("user_Id"));

						if (userIdPopup.equals(result.getString("user_Id"))) {

							forgotPasswordList.add(0,
									result.getString("user_pwd"));

						} else {
							forgotPasswordList
									.add(0,
											"can't display because given user ID didn't match with data base user id");
						}
					}

					else {
						System.out.println("Authorisation failed");
						forgotPasswordList
								.add(0,
										"Can't display since user Id or kin Id is incorrect...! Try with correct credentials");
						return forgotPasswordList;
					}
				} else {
					System.out.println("you are not admin or inputter");
					forgotPasswordList
							.add(0,
									"Can't display since you are not admin or inputter");
					return forgotPasswordList;

				}

			}

			else {
				System.out.println("validation failed");
				forgotPasswordList
						.add(0,
								"Can't display since user Id or kin Id is incorrect...! Try with correct credentials");
				return forgotPasswordList;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (connection != null)
				try {
					connection.close();

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

			if (result != null)
				try {
					result.close();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return forgotPasswordList;

	}
	
	public List<String> resetPassword(String userId, String oldpwd,
			String newpwd, String Confrmpwd) {

		/**
		 * <p>
		 * resetPassword method takes the old password,new password and confirm
		 * password as the arguments checks whether the new password and confirm
		 * password is same and whether the old password entered matches with
		 * the database then establishes the connection to the MySql data base
		 * executes queries does the validation with if else conditions and
		 * returns the resetPasswordList then finally closes the result
		 * set,prepared statement and connection
		 * </p>
		 * 
		 */
		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		List<String> resetPasswordList = null;

		resetPasswordList = new ArrayList<String>();
		System.out.println("in dao once again " + userId + "--" + oldpwd + "--"
				+ newpwd + "--" + Confrmpwd);
		if (newpwd.equals(Confrmpwd)) {
			selectQuery = "select user_pwd from login_details where user_Id=? ";
			try {
				connection = dataSource.getConnection();
				selectStatement = connection.prepareStatement(selectQuery);

				selectStatement.setString(1, userId);
				// selectStatement.setString(2, password);

				result = selectStatement.executeQuery();
				if (result.next()) {
					System.out.println("user password when user id is given "
							+ result.getString("user_pwd"));
					if (oldpwd.equals(result.getString("user_pwd"))) {
						System.out
								.println("Old password and data base password is same");
						selectQuery = "update login_details set user_pwd=? where user_Id=? ";
						selectStatement = connection
								.prepareStatement(selectQuery);
						selectStatement.setString(1, newpwd);
						selectStatement.setString(2, userId);
						selectStatement.executeUpdate();
						resetPasswordList.add(0,
								"your password is succesfully reset");
						System.out.println("in dao result" + resetPasswordList);
						return resetPasswordList;
					} else {
						resetPasswordList.add(0,
								"new userid and password doesn't match");
						return resetPasswordList;
					}
				} else {
					resetPasswordList.add(0, "failed");
					return resetPasswordList;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		} else {
			resetPasswordList.add(0, "new password doesn't match");
			return resetPasswordList;
		}
		return resetPasswordList;
	}

	
}