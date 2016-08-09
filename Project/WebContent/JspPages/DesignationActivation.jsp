<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Designation Activation</title>

 <link rel="stylesheet"
	href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css">
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojox/grid/resources/claroGrid.css"/>
<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>
  <script
	src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'></script>  

<script>
	 
require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready","dijit/Dialog","dojox/grid/DataGrid","dojo/data/ItemFileReadStore"], function(parser,
		ajax, ready) {
	ready(function() {

		var gridObj = dijit.byId("gridId");

		gridObj.on("RowClick", function(evt){
			
			var idx = evt.rowIndex;
			rowData = gridObj.getItem(idx);
			var method="";
			switch(evt.cellIndex){
			
			case 2:
				method="${pageContext.request.contextPath}/updateDesignation";
				break;
			case 3:
				method="${pageContext.request.contextPath}/deleteDesignation";
				break;
			case 4:
				updateDesignation(rowData.designationId,rowData.designationName);
				method=""
				break;
			default:
			}

				dojo.xhrPost({
					url :method , /* "updateAndLoad", "updateDesignation",*/
					handleAs : "json",
					content : {
						designationData : rowData.designationId,
						designationStatus : rowData.designationStatus
						 
					},
					load : function(response, ioArgs) {
						//alert(response);
						var newData = {
									identifier: "designationId",
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
			url : "${pageContext.request.contextPath}/TotalDesignationListGrid",
			handleAs : "json",
			content : {
				firstParam : ""
				 
			},
			 load : function(response, ioArgs) {
				var newData = {	identifier: "designationId",items: response	};
				var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId"});
				var grid = dijit.byId("gridId");
				grid.setStore(dataStore);
			},
			
			error : function(response, ioArgs) {
				//alert("An error occurred while invoking the service.");
			}
		});//GRID

		});
		
});
	
	function updateDesignation(id,name){
		  
		 // alert(id+name);
		 document.getElementById("hiddendesignationId").setAttribute("value", id);
		 document.getElementById("designation").setAttribute("value",name); 
	}

	function saveDesignation(){
		  var designationName = document.getElementById("designation").value;
		  var hiddenValue=document.getElementById("hiddendesignationId").value;
		  //alert(designationName+hiddenValue)
		  var methodNew = "${pageContext.request.contextPath}/saveDesignation";
		  if(hiddenValue!=0){
			  methodNew = "${pageContext.request.contextPath}/updateNewDesignation";
		  }
	
		  require([ "dojo/_base/xhr"], function(ajax) {
			  
			  ajax.post({
					url : methodNew, 
					handleAs : "json",
					content : {
						designationData : designationName,
						designationId :hiddenValue
					},
					
					load : function(result) {
						
						
			dojo.xhrPost({
			url : "${pageContext.request.contextPath}/TotalDesignationListGrid",
			handleAs : "json",
			content : {
				firstParam : ""
				 
			},

			 load : function(response, ioArgs) {
				
				var newData = {	identifier: "designationId",items: response	};
				var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId"});
				var grid = dijit.byId("gridId");
				grid.setStore(dataStore);
				  
			},
			
			error : function(response, ioArgs) {
				//alert("An error occurred while invoking the service.");
			}
		});//GRID
					},
					error : function() {
						alert("Db not connected");
		
					}
				});
		  	});
		   }
	  
	    
	function getActionColumn(designationStatus){
		var str ="";
		switch(designationStatus){
			case 'AC':
				str ="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Project/resources/image/GreenTick.png' width='17px' height='15px'/></a>";
			break;
			case 'IN':
				str ="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Project/resources/image/RedTick.png' width='17px' height='15px'/></a>";
			break;
			
		}	
			 
		return str;
	}

	function deleteRow(designationStatus){
				
			return "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Project/resources/image/Delete.png' width='17px' height='15px'/></a>";
	}
	
	function updateRow(designationStatus){
				
			return "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Project/resources/image/pencil.png' width='17px' height='15px'/></a>";
	}
	
	function sl(temp,idx) {
		return idx+1;
	}

</script>

</head>
<body class="claro">
					<div>
						<input type="text"  name="designation" id="designation" data-dojo-type="dijit/form/TextBox" /> 
						<input type="hidden" name="hiddendesignationId" id="hiddendesignationId"  data-dojo-type="dijit/form/TextBox" /> 
						<button type="submit" id="designationSubmit" onclick="saveDesignation();"  data-dojo-type="dijit/form/Button" >
						<div id="container1">
						<span>Save</span> </button>
					</div>

<table  data-dojo-type="dojox.grid.DataGrid"	data-dojo-props="rowSelector:'20px'" id="gridId"    style="width: 800px; height: 200px;">
<thead>
	        <tr>
	            <th width="150px" formatter="sl">#Sl</th>
	            <th width="150px" field="designationName">Designation Name</th>
	            <th width="50px" field="designationStatus" formatter="getActionColumn">Action </th>
	            <th width="50px" field="designationStatus"  formatter="deleteRow">Delete </th> 
	            <th width="50px" field="designationStatus"  formatter="updateRow">Update </th> 
	        </tr>     
	    </thead>  
	</table>

</body>
</html>
					







