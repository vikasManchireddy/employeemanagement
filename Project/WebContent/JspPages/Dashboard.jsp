
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css">
			<script>dojoConfig = {async: true, parseOnLoad: true}</script>
			<script src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'></script>
				<script>

dojo.require("dijit.form.Button");
	</script>
<style> 
table, th, td {
    border: 2px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 15px;
}
</style>
</head>
<body class="claro">
 
<table width="100%" cellspacing="1" cellpadding="1" align="center"
			border="0" BGCOLOR="#CCCCCC">


  <tr BGCOLOR="#FFFFFF" >
    <th><a href="dashboard">Dash Board</a></th>
<!-- <th>Dash Board</th> -->
    <th><a href="TotalEmployeeList">Employee List</a></th>		
    <th><a href="TotalDesignationList">Designation</a></th>
	<th><a href="TotalStateList">State</a></th>		
    <th><a href="TotalCityList">City</a></th>
  </tr>
  
 	<tr BGCOLOR="#FFFFFF">
 	
 	<td colspan="5" width="100%" ><b>Welcome Admin...! </b><br/><br/><a href="TotalEmployeeList"> ${EmpInactiveNumber} </a> Employees are waiting for approval</td>
 	
 	
 	</tr>
 
 
 
</table>
 
</body>
</html>

