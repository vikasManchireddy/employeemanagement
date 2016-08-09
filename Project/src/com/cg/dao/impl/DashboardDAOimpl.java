package com.cg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.cg.dao.DashboardDAO;
import com.cg.util.ServiceLocator;
import com.cg.util.ServiceLocatorException;


@Repository("dashboardDAOimpl")
public class DashboardDAOimpl implements DashboardDAO{

	
	private DataSource dataSource;

	public DashboardDAOimpl() {

		try {
			dataSource = ServiceLocator.getDataSource("jdbc/VIMDataSource");

		} catch (ServiceLocatorException e) {

			System.out.println("Container Service not available");
		}

	}
	
	public int dashbord() {

		Connection connection = null;
		PreparedStatement selectStatement = null;
		String selectQuery = null;
		ResultSet result = null;

		selectQuery = "SELECT COUNT(EMP_ID) FROM tempempdet where emp_status='IN' and emp_role='E'";
		try {
			connection = dataSource.getConnection();

			selectStatement = connection.prepareStatement(selectQuery);
			result = selectStatement.executeQuery();

			if (result.next()) {

				System.out.println(result.getInt(1));
				return result.getInt(1);
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

		return 0;
	}

}
