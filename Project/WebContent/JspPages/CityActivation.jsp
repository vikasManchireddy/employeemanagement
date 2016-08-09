<!--  City Activation
* @author Swathi & Monika
* @Version 1.0
* @Since 2016-05-02    -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Master Data:City </title>
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
				alert("2 nd row");
				alert(rowData.stateCityId);
				alert(rowData.stateCityStatus);
				method="${pageContext.request.contextPath}/updateCity";
				break;
			case 3:
				alert("3 nd row");
				method="${pageContext.request.contextPath}/deleteCity";
				break;
			case 4:
				alert("4 nd row");
				updateCityState(rowData.stateCityId,rowData.stateCityName);
				method=""
				break;
			default:
				alert("in switch");
			}

		 	dojo.xhrPost({
				url : method,
				handleAs : "json",
				content : {
					cityData: rowData.stateCityId,
					cityStatus: rowData.stateCityStatus,
					 
				},
				load : function(response, ioArgs) {
					alert(response); 
					 var newData = {
								identifier: "stateCityStatus",
								items: response
						}; 
						
					var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId1"});
						var grid = dijit.byId("gridId");
						  grid.setStore(dataStore);
				},error : function(response, ioArgs) {
					alert("An error occurred while invoking the service.");
				}
			});
			 
		}, true);
		
		
		alert("hey");
		
		//OnLoad load the grid
		dojo.xhrPost({
		url : "${pageContext.request.contextPath}/cityListGrid",
		handleAs : "json",
		content : {
			firstParam : "" 
		},
		
		
		 load : function(response, ioArgs) {
			alert("success");
			var newData = {	identifier: "stateCityId",items: response	};		
			var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId"});
			var grid = dijit.byId("gridId");
			grid.setStore(dataStore);
			  
		},error : function(response, ioArgs) {
			alert("An error occurred while invoking the service.");
		}
	});//GRID
	

	});
	
});


	
	function saveCity(){
	  var dropdownvalue = document.getElementById("stateCombo").value;
	  var cityName=document.getElementById("cityinput").value;
	  var hiddenValue=document.getElementById("cityHiddenId").value;
	  
	  alert(dropdownvalue+cityName+hiddenValue);
	  var method = "${pageContext.request.contextPath}/saveCity";
		alert("im into save city");
	  if(hiddenValue!=0){
		  method = "${pageContext.request.contextPath}/updateNewCity";
	  }

	  require([ "dojo/_base/xhr"], function(ajax) {
		  
		  ajax.post({
				url :method,
				handleAs : 'text',
				content : {
					stateId : dropdownvalue,
					cityData : cityName,
					hiddenValue : hiddenValue
				},
				
				
				load : function(result) {
					alert("your password : " + result);

				},
				error : function() {
					alert("Db not connected");

				}
			});
	  });
	    }
	
	
	
	function getActionColumn(stateCityStatus){
	
		var str ="";
		switch(stateCityStatus){
			case 'AC':
					str ="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/GreenTick.png' width='17px' height='15px'/></a>";
			break;
			case 'IN':
				str ="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/RedTick.png' width='17px' height='15px'/></a>";
			break;	
		}	
			 
		return str;
	}
	
	 function deleteRow(stateCityStatus){
		return "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/Delete.png' width='17px' height='15px'/></a>";
	} 
	 
	 function updaterow(stateCityStatus){
			return "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'><img src='/Employee/resources/image/pencil.png' width='17px' height='15px'/></a>";
		} 
	 
	 function updateCityState(id,name){
		 document.getElementById("cityHiddenId").setAttribute("value", id);
		 document.getElementById("cityinput").setAttribute("value",name); 
	 }
	 
	 function sl(temp,idx) {
			return idx+1;
		}
  

</script>

</head>
<body class="claro">

<div id="container">

state
<select data-dojo-id="s3" path="stateCombo" name="stateCombo"  id="stateCombo" required ="true"  style="width: 150px;" data-dojo-type="dijit/form/Select"/>
						 <option value="--select State--"></option> 
						 <c:forEach items='${stateList}' var='state' >
						<option value="${state.stateCityId}" name="state1" id="state1" >${state.stateCityName}</option>
						</c:forEach>
</select>
city<font color ="red">*</font>
		<input type="text"  name="cityinput" id="cityinput"  style="width: 100px" data-dojo-type="dijit/form/TextBox" />
		<input type="text" name="cityHiddenId" id="cityHiddenId" style="width: 100px" data-dojo-type="dijit/form/TextBox" /> 
		<button type="submit" id="citySubmit"  value="submit" onclick="saveCity();"  data-dojo-type="dijit/form/Button" >
		<span>Save</span> </button>
</div>


<table  data-dojo-type="dojox.grid.DataGrid"   data-dojo-props="rowSelector:'20px'" id="gridId"    
		style="width: 670px; height: 200px;">
		  <thead>
	        <tr>
	            <th width="150px" formatter="sl">#Sl</th>
		        <th width="50px" field="slNo" noresize="true">S.no</th>
	            <th width="300px" field="stateCityName"  sortable="false">City</th>
	            <th width="50px" field="stateCityStatus" formatter="getActionColumn">Action </th>  
	            <th width="50px" field="stateCityStatus" formatter="deleteRow">Delete </th>
	            <th width="50px" field="stateCityStatus" formatter="updaterow">Update</th> 
  	             
	        </tr>   
	    </thead>  
	</table>

</body>
</html>
					







