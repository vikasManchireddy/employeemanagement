package com.cg.dao;

import java.util.List;

import com.cg.model.EmployeeDTO;

public interface UserEmployeeDAO {

	public List<String> loginValidation(String userId, String password);

	public List<String> forgotPassword(final String kinIdPopup,
			final String userIdPopup);

	public List<String> resetPassword(String userId, String oldpwd,
			String newpwd, String Confrmpwd);

}
