<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<!--  <head>
<meta http-equiv="refresh" content="10">
</head>  --> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>

<link href="<c:url value="/resources/css/claro.css" />" rel="stylesheet">

<tr>
<td colspan=5 align=center font=strong > <p><strong><font size = "5" color = "#990000"><b><center><strong>Employee List</strong></center></b></font></p></strong></td>
 
</tr>


<!-- <link rel="stylesheet"
	href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css"> -->
<link rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojox/grid/resources/claroGrid.css" />
<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>
<script
	src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'></script>


<script>
	function showDialog() {

		dijit.byId("dialogOne").show();
	}
</script>


<script>
	require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready", "dijit/Dialog",
			"dojox/grid/DataGrid", "dojo/data/ItemFileReadStore" ], function(
			parser, ajax, ready) {
		//require([ "dojo/ready"], function( ready){
		ready(function() {

			var gridObj = dijit.byId("gridId");

			gridObj.on("RowClick", function(evt) {

				var idx = evt.rowIndex,

				rowData = gridObj.getItem(idx);
				alert(rowData.kinId);

				dojo.xhrPost({
					url : "DeleteEmployee",
					handleAs : "json",
					content : {
						/* bkin : rowData.employeeId */
						employeeId : rowData.employeeId
						

					},
					 load : function(response, ioArgs) {
						alert("ibm");
						
						 var newData = {
							identifier : "employeeId",
							items : response
						};
						
						 
						 var dataStor = new dojo.data.ItemFileReadStore({
							data : newData,
							id : "dataStoreId1"
								
						});
						
						var grid = dijit.byId("gridId");
						grid.setStore(dataStor); 
					},  
					
					error : function(response, ioArgs) {
						alert("An error occurred while invoking the service.");
					}
				}); 

			}, true);

			alert("hey in ready fun");
			//OnLoad load the grid
			dojo.xhrPost({
				url : "${pageContext.request.contextPath}/employeeListGrid",
				handleAs : "json",
				content : {
					firstParam : ""

				},

				load : function(response, ioArgs) {

					alert("success");
					var newData = {
						identifier : "employeeId",
						items : response
					};

					var dataStore = new dojo.data.ItemFileReadStore({
						data : newData,
						id : "dataStoreId"
					});

					var grid = dijit.byId("gridId");
					grid.setStore(dataStore);

				},
				error : function(response, ioArgs) {
					alert("An error occurred while invoking the service.");
				}
			});//GRID

		});

	});

	//Grid Onclick

	//gridd click ends

	function getActionColumn(status) {

		var str = "";
		switch (status) {
		case 'A':
			str = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/GreenTick.png' width='17px' height='15px'/></a>";
			break;
		case 'I':
			str = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/RedTick.png' width='17px' height='15px'/></a>";
			break;
		}

		return str;
	}
	function deleteRow(status) {

		return "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/Delete.png' width='17px' height='15px'/></a>";
	}
	
/*  function editRow(status) {

		return "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/pencil.png' width='17px' height='15px'/></a>";
	}  */

	function sl(temp, idx) {
		return idx + 1;
	}
</script>
	
	
	
	
	
	
	<script>
	require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready" ], function(parser,
			ajax, ready) {
		//require([ "dojo/ready"], function( ready){
		ready(function() {

			dijit.byId("resetpassword").on("click", function(){
				alert("1");
			
				var oldPwd =  dijit.byId("OldPopup").get("value");
				var newPwd=  dijit.byId("NewPopup").get("value");
				var confirmNewPwd=  dijit.byId("ConfrmNewPopup").get("value");
									
				 ajax.post({
					url:"${pageContext.request.contextPath}/reset",
					handleAs:'text',
					content: {
						OldPopup: oldPwd,
						NewPopup : newPwd,
						ConfrmNewPopup :confirmNewPwd 
					},
					load: function(result) {
						alert(result);
						document.getElementById("OldPopup").value="";
						document.getElementById("NewPopup").value="";
						document.getElementById("ConfrmNewPopup").value="";
						
					},
					error: function() {
						alert("Db not connected");
					
					}
				});

			});

		});
	});



</script>









</head>
<body class="claro">

	<img alt="RRR" src="<c:url value="/resources/image/untitled.png" />">
<!-- <div align="right">
<form action="change">
 <input type="submit" value="Reset Password" font-size="2px" color="#990000"></font> 
</form>

  <button id="btn" align="right" data-dojo-type="dijit/form/Button"
            data-dojo-props="
                onClick: function(){ console.log('First button was clicked!'); }">
     
     Reset Password     </button>
     
      
</div> -->


				<td width="100%" align="right" colspan="2"><a
					href="javascript:void(0)" onclick="showDialog()"> Reset
						Password</a></td>


	<table data-dojo-type="dojox.grid.DataGrid"
		data-dojo-props="rowSelector:'20px'" id="gridId"
		style="width: 900px; height: 300px;">
		<thead>
			<tr>
			
			

			
				<th width="50px" field="slno" formatter="sl" noresize="true">S.no</th>
				<th width="300px" field="firstName">First Name</th>
				<th width="150px" field="lastName">Last Name</th>
				<th width="100px" field="kinId">Kin Id</th>
				<th width="100px" field="education">Education</th>
				<!-- 	            <th width="50px" field="status" formatter="getActionColumn">Action </th> -->
				<th width="60px" field="status" formatter="editRow">Edit</th>
				<th width="50px" field="status" formatter="deleteRow">Delete</th>
			
				
			</tr>
  

		</thead>

	</table>
	
	<button id="btn" align="center" data-dojo-type="dijit/form/Button"
            data-dojo-props="
                onClick: function(){ console.log('First button was clicked!'); }">
     
    New    </button>

<div id="dialogOne" data-dojo-type="dijit.Dialog"
		title="Forgot Password">
		<div style="width: 400px; height: 200px;">





	<table width="60%" cellspacing="1" cellpadding="1" align="center"
			border="0" BGCOLOR="#CCCCCC">
			<tr BGCOLOR="#FFFFFF">
				<td colspan="2" align="center"><font size="5" color="#990000"><b>Reset Password</b></font>

				</td>
			</tr>
			<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center"><font size="4">Old Password</font><font color="red">*</font></td>
				<td width="50%" align="center"><input type="password"
					required="true" name="OldPopup" id="OldPopup" placeholder="Your Id"
					data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

			<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center" ><font size="4">New Password</font><font color="red">*</font></td>
				<td width="50%" align="center"><input type="password"
					required="true" name="NewPopup" id="NewPopup"
					placeholder="Your Password"
					data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center"><font size="4">Confirm New Password</font><font color="red">*</font></td>
				<td width="50%" align="center"><input type="password"
					required="true" name="ConfrmNewPopup" id="ConfrmNewPopup"
					placeholder="Your Password"
					data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>




			<tr BGCOLOR="#FFFFFF">
				<td width="100%" colspan="2" align="center">
					<button type="submit" align="center" id="resetpassword"
						data-dojo-type="dijit/form/Button">
						<span>Save</span>
					</button>
			</tr>

			






		</table>
</div>
</div>

</body>
</html>