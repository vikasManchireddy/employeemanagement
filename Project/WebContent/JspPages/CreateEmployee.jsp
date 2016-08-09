<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
    
    <!--  
 * <h1>Create Employee JSP</h1>
 * <P>
 * This is the Employee DAO JSP
 * </P>
 * 
 * @author NEHA ERSAVADLA
 * @version 1.0
 * @since 2016-05-02
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css">
<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>
<script src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'></script>

<!-- To load dojo libraries -->

	<script>
	require(["dojo/parser","dojo/_base/xhr",  "dojo/ready"], function(parser, ajax,ready){
		ready(function (){  
		   
			 
			 //ready open
				
		 
			dijit.byId('submit').on("click", function(){
				var fName =  dijit.byId('firstName').get('value');
				var lName =  dijit.byId('lastName').get('value');
			
			});
			
			

			 var select = dijit.byId('state')
			select.on('change', function(evt) {
			var stateId = dijit.byId("state").get("value");
			callAjax(stateId);   
			
			});
			 alert("DOJO LOADED");
			 
			
			

		});
	});//ready closed
	//ajax function for state city dynamic loading
	 function callAjax(selstateId) {
		//alert("stateId  ---:"+ selstateId);

		dojo.xhrPost({
		url : "${pageContext.request.contextPath}/cityDetail",
		handleAs : 'json',
		content : {
		emp_state : selstateId
		},
		load : function(result) {
		alert(result);
		var select1 = dijit.byId("city");
		select1.addOption(result);

		},
		error : function() {
		alert("Db not connected");

		}
		});//2nd ajax
		}
	  
//function for cancel button
	 function myFunction() {
		    var txt;
		    var r = confirm("Are you sure??");
		    if (r == true) {
		        txt = " OK!";
		        window.location.href="${pageContext.request.contextPath}/empList";
		        /*  <button onclick="window.location.href='/EmployeeList'">OK</button>  */
		        
		    }
		     else {
		        txt = " Cancel!";
		    }
	 };
	 
	 //function for reset button
	 function resetForm() {
		 
		   dijit.byId("state").set("value","");
		   dijit.byId("employeeRole").set("value","");
		   document.getElementById("Choose_Male").checked=false;
		   document.getElementById("Choose_Female").checked=false;
	 };
    
</script>
	
</head>
<body class="claro">

<div class="divRightContainer">
	
	
	
	
		<form:form action="saveEmployee"  method="POST"   data-dojo-type="dijit/form/Form" commandName="employeeDTO"  >
		   <script type="dojo/on" data-dojo-event="submit">
        if(this.validate()){
            return confirm('Form is valid, press OK to submit');
        }else{
            alert('Form contains invalid data.  Please correct first');
            return false;
        }
        return true;
    </script>
		 
		<table  width="60%" cellspacing="1" cellpadding="1" align="center"  border="1">
		<tr> 
		
        <td colspan=3 align=center font=strong > <p><strong><font color="maroon">New Employee </font></strong></p></td>

        </tr>

		
			<tr>
			
				<td  width="30%">First Name<font color="red">*</font></td>
				<td  width="50%">
				<form:input path="firstName" required="true" name="firstName" id="firstName"  placeholder="Your Name" data-dojo-type="dijit/form/ValidationTextBox" />

				</td>
			</tr>
			
			<tr>
				<td width="30%">Last Name</td>
				<td width="50%">
					<form:input path="lastName" required="false" name="lastName" id="lastName" 
						placeholder="Your Name"  data-dojo-type="dijit/form/ValidationTextBox" 
						 />
 
						 
				</td>
			</tr>
			
			
				
			
			
	
			
			<tr>
				<td width="30%">kin Id<font color="red">*</font></td>
				
				<td width="50%">
					<form:input path="kinId" required="true" name="kinId" id="kinId" placeholder="Your Id" data-dojo-type="dijit/form/ValidationTextBox" />
				</td>
			</tr>
			
			<tr>
				<td width="30%">Education<font color="red">*</font></td>
				
				<td width="50%">
					<form:input path="education" required="true" name="education" id="education" placeholder="Your education" data-dojo-type="dijit/form/ValidationTextBox" />
				</td>
			</tr>
			
			
			
		<tr> 
				<td width="30%">Designation<font color="red">*</font></td>
				<td width="50%">
					<form:select path="employeeRole"    name="employeeRole" required ="true" id="employeeRole" 
					style="width: 150px;" data-dojo-type="dijit/form/Select">
				<form:option value="">--select option -</form:option>
				
				<!--  to get designation values from database -->
 				<c:forEach items='${designationList}' var='designation' >
 				
 				<form:option value="${designation.designationId}">${designation.designationName}</form:option> 
 				
 				</c:forEach>
				
				
				
				
				</form:select>
				</td>
 			</tr>  
 			
 			
 			
 			
 			
 			
 			<tr> 
				<td width="30%">State<font color="red">*</font></td>
				<td width="50%">
					<form:select path="state"    name="state" required ="true" id="state" 
					style="width: 150px;" data-dojo-type="dijit/form/Select">
				<form:option value="0">--select option -</form:option>
				<!--  to get state city values from database -->
				
 				<c:forEach items='${StateList}' var='state' >
 				
 				<form:option value="${state.stateCityId}">${state.stateCityName}</form:option> 
 				
 				</c:forEach>
				
				
				
				
				</form:select>
				</td>
 			</tr>  
 			
 			
 			
 			
			
			
			 
			 <tr>
				<td width="30%">City<font color="red">*</font></td>
				<td width="50%">
				<form:select path="city"  name="city" required ="true" id="city" style="width: 150px;" data-dojo-type="dijit/form/Select">
 				
				<form:option value="0">--select option--</form:option>
 				
 				<%-- <c:forEach items='${CityList}' var='city' >
 				
 				<form:option value="${city.stateCityId}">${city.stateCityName}</form:option> 
 				
 				</c:forEach> --%>
				
				</form:select>
				
				</td>
			</tr> 
			 	 
		
			
			 <tr>
				<td width="30%">Gender</td>
				
				<td width="50%">
				<input  type="radio" path="gender" name="gender" id="Choose_Male" value="male"  /> Male
                <input type="radio" path="gender" name="gender"  id="Choose_Female"value="female" /> Female
				</td>
				</tr> 
			  
			
			<tr>
				<td width="20%" colspan="2" align="center">
						<button  action="saveEmployee" type="submit" id="submit"  data-dojo-type="dijit/form/Button" >
							
							
							<%-- <form action="saveEmployee">
    <input dojoType="dijit.validation.TextBox"/>
    <button onClick="validateall()">save</button>
    <button id="submit" type="submit" hidden="true"/> 
    </form>  --%>
							
							
							
							
							
							<span>Save</span>
							</button>
					
						<button  type="reset" onclick="resetForm()" id="resetBtn" data-dojo-type="dijit/form/Button">
							<span>Reset</span>
							 
						</button>
						
						<button onclick="myFunction()" type="button" id="cancelBtn" data-dojo-type="dijit/form/Button">
							<span>Cancel</span>
							
							
						</button>
				</td>
			</tr>
	
		</table>
	
		</form:form>
</div>

 
</body>
</html>