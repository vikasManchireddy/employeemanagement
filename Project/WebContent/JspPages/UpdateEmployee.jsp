<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Employee</title>

<link rel="stylesheet"
	href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css">
<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>
<script
	src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'>
</script>

<script>
	//this function displays a confirmPopUpBox
	function confirmPanel() {
		var x;
		if (confirm("Press a button!") == true) {
			x = "You pressed OK!";
			window.location.href = "viewEmpList"
		} else {
			x = "You pressed Cancel!";
		}

	}
</script>

<script>

require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready"], function(parser,ajax, ready) {
	//require([ "dojo/ready"], function( ready){
	ready(function() {

	        var stateId = dijit.byId("emp_state").get("value");
			//alert("stateId:" + stateId);
			callAjax(stateId);// onload city dropDown
			
			
			var select = dijit.byId('emp_state')
			select.on('change', function(evt) {
			var stateId = dijit.byId("emp_state").get("value");
			callAjax(stateId);//onselect city dropDown
			
			});//onChange
		});//ready ends
	});//require ends
	function callAjax(selstateId) {
		//alert("stateId  ---:"+ selstateId);

		dojo.xhrPost({
			url : "${pageContext.request.contextPath}/employee/cityDetail",
			handleAs : 'json',
			content : {
				emp_state : selstateId
			},
			load : function(result) {
				// alert(result.length());
				alert(typeof(result));
 
			 	var select1 = dijit.byId("emp_city");
				select1.addOption(result);
		 

			},
			error : function() {
				alert("Db not connected");

			}
		});//2nd ajax
	}
</script>

	


</head>
<body class="claro">
	<form:form action="saveForm" method="post" id="updateForm" data-dojo-type="dijit/form/Form" commandName="Employee">


		<script type="dojo/on" data-dojo-event="submit">

       	 if(this.validate()){

            alert("success, submit");
       	 }else{
            alert('Form contains invalid data.  Please correct first');
            return false;
        	}
        	return true;

   		</script>



		<form:input path="empId" type="hidden" name="empId" id="empId"
			value="${employee1.empId}"
			data-dojo-type="dijit/form/ValidationTextBox" />
		<table width="70%" cellspacing="1" cellpadding="8" align="center"
			bgcolor="#ccccc">
			<tr bgcolor=#ffffff>
				<td colspan="2" align="center">UPDATE EMPLOYEE</td>
			</tr>
			<tr bgcolor=#ffffff>
				<td width="20%">First Name <font color="red"><sup>*</sup></font>
				</td>
				<td width="50%"><form:input path="f_name" required="true"
						name="firstName" id="firstName" value="${employee1.f_name}"
						data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%">Last Name</td>
				<td width="50%"><form:input path="l_name" required="false"
						name="lastName" id="lastName" value="${employee1.l_name}"
						placeholder="Your Name"
						data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%">User Id<font color="red"><sup>*</sup></font>
				</td>
				<td width="50%"><input type="text" required="true"
					name="User Id" id="User Id" value="${employee1.f_name}"
					placeholder="User Id" data-dojo-type="dijit/form/ValidationTextBox"
					disabled="disabled" /></td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%">Kin Id<font color="red"><sup>*</sup></font>
				</td>
				<td width="50%"><form:input path="kin_id" required="true"
						name="kin_id" id="kin_id" value="${employee1.kin_id}"
						placeholder="Your KinId"
						data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%">Education</td>
				<td width="50%"><form:input path="emp_edu" required="false"
						name="emp_edu" id="emp_edu" value="${employee1.emp_edu}"
						placeholder="Your Education"
						data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%">Designation<font color="red"><sup>*</sup></font>
				</td>
				<td width="50%"><form:select path="desigId" required="true"
						style="width: 150px;" data-dojo-type="dijit/form/Select"
						value="${employee1.desigId}">

						<c:forEach items='${desig}' var='desig'>

							<form:option value="${desig.desigId}"
								selected="${ desig.desigId== employee1.desigId ? 'selected' : ''}">${desig.design_name}</form:option>

						</c:forEach>

					</form:select></td>
			</tr>
			<tr bgcolor=#ffffff>
				<td width="20%"><label for="state">State<font
						color="red"><sup>*</sup></font>
				</label></td>
				<td width="50%">
					<%--  (<c:out value ="${employee1.emp_state}"/>) --%> <form:select
						path="emp_state" name="emp_state" id="emp_state" required="true"
						style="width: 150px;" data-dojo-type="dijit/form/Select">

						<c:forEach items='${stateOption}' var='state'>
							<form:option value="${state.key}"
								selected="${state.key == employee1.emp_state ? 'selected' : ''}">${state.value}</form:option>

						</c:forEach>

					</form:select>
				</td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%"><label for="city">City<font color="red"><sup>*</sup></font>
				</label></td>
				<td width="50%"><form:select path="emp_city" name="emp_city"
						id="emp_city" required="true" style="width: 150px;"
						data-dojo-type="dijit/form/Select">
					</form:select></td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%">Gender</td>
				<td width="25%"><form:select path="emp_gender"
						name="emp_gender" id="emp_gender" required="true"
						style="width: 150px;" data-dojo-type="dijit/form/Select"
						value="${employee1.emp_gender}">
						<%-- <form:option value="${employee1.emp_gender}"></form:option> --%>
						<form:option value="F"
							selected="${'F'== employee1.emp_gender ? 'selected' : ''}">Female</form:option>
						<form:option value="M"
							selected="${'M' == employee1.emp_gender ? 'selected' : ''}">Male</form:option>
					</form:select> <br /></td>
			</tr>

			<tr bgcolor=#ffffff>
				<td width="20%" colspan="2" align="center">
					<button data-dojo-type="dijit/form/Button" type="submit"
						name="save" value="save">Save</button>
					<button type="button" id="cancelBtn"
						data-dojo-type="dijit/form/Button" onclick="confirmPanel()">Cancel</button>
				</td>
			</tr>

		</table>
	</form:form>

</body>
</html>