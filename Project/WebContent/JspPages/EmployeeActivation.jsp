<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>

<link rel="stylesheet"
	href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css">
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

require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready","dijit/Dialog","dojox/grid/DataGrid","dojo/data/ItemFileReadStore"], function(parser,
		ajax, ready) {
	ready(function() {

		var gridObj = dijit.byId("gridId");

		gridObj.on("RowClick", function(evt){
			
			var idx = evt.rowIndex;
			//alert(evt.cellIndex);
			rowData = gridObj.getItem(idx);
			var method="";
			switch(evt.cellIndex){
			
			case 3:
				method="${pageContext.request.contextPath}/updateEmployee";
				break;
			case 4:
				method="${pageContext.request.contextPath}/deleteEmployee";
				break;
			default:
			}
				dojo.xhrPost({
					url :method ,
					handleAs : "json",
					content : {
						employeeData : rowData.employeeId,
						employeeStatus: rowData.status
						 
					},
					load : function(response, ioArgs) {
						var newData = {
									identifier: "employeeId",
									items: response
							};
							
						var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId1"});
							var grid = dijit.byId("gridId");
							  grid.setStore(dataStore);
					},error : function(response, ioArgs) {
						//alert("An error occurred while invoking the service.");
					}
				});
				
			}, true);
		
		
		//OnLoad load the grid
			dojo.xhrPost({
			url : "${pageContext.request.contextPath}/TotalEmployeeListGrid",
			handleAs : "json",
			content : {
				firstParam : ""
			},

			 load : function(response, ioArgs) {
			 
				var newData = {	identifier: "employeeId",items: response	};
					
				var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId"});
				var grid = dijit.byId("gridId");
				grid.setStore(dataStore);
				  
			},
			
			error : function(response, ioArgs) {
			}
		});//GRID

		});
		
});



function getActionColumn(status){
	
	
	var str ="";
	switch(status){
	case 'AC':
		str ="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/GreenTick.png' width='17px' height='15px'/></a>";
	break;
	case 'IN':
		str ="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/RedTick.png' width='17px' height='15px'/></a>";
	break;
		
	}	
		 
	return str;
}
	function deleteRow(status){
		return "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/Delete.png' width='17px' height='15px'/></a>";
	
}
	
	function sl(temp,idx) {
		return idx+1;
	}
	
	function fmtItem(firstName,idx) {
		
		var gridObj = dijit.byId("gridId");
		
		rowData = gridObj.getItem(idx);
		
		return firstName+" "+rowData.lastName;
			  
		}
	
</script>

</head>
<body class="claro">


	<table data-dojo-type="dojox.grid.DataGrid"
		data-dojo-props="rowSelector:'20px'" id="gridId"
		style="width: 800px; height: 200px;">
		<thead>
			<tr>
				<th width="150px" formatter="sl">#Sl</th>
				<th width="150px" field="firstName" formatter="fmtItem">FullName</th>
				<th width="150px" field="designation">Designation</th>
				<th width="50px" field="status" formatter="getActionColumn">Action</th>
				<th width="50px" field="status" formatter="deleteRow">Delete</th>
			</tr>
		</thead>
	</table>

</body>
</html>
