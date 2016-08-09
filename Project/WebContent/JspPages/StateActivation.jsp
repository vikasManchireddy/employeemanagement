<!--  State Activation
* @author Swathi & Monika
* @Version 1.0
* @Since 2016-05-02    -->


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Master Data:State </title>
<link href="<c:url value="/resources/css/claro.css" />" rel="stylesheet">

	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojox/grid/resources/claroGrid.css"/>

<script type="text/javascript">
dojoConfig = {
   isDebug: true,
   parseOnLoad : true
}
</script>

<script
	src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'>
</script>


<script>

	require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready","dijit/Dialog","dojox/grid/DataGrid","dojo/data/ItemFileReadStore" ], function(parser,
			ajax, ready) {
		ready(function() {

			var gridObj = dijit.byId("gridId");

			gridObj.on("RowClick", function(evt){
				
				var idx = evt.rowIndex;
				rowData = gridObj.getItem(idx);
				 var method="";
				switch(evt.cellIndex){
				case 2:
					method="${pageContext.request.contextPath}/updateState";
					break;
				case 3:
					method="${pageContext.request.contextPath}/deleteState";
					break;
				case 4:
					updateCityState(rowData.stateCityId,rowData.stateCityName);
					method=""
					break;
				default:
				}

				dojo.xhrPost({
					url : method,//"${pageContext.request.contextPath}/updateState",
					handleAs : "json",
					content : {
						stateData: rowData.stateCityId,
						stateStatus:rowData.stateCityStatus,
					},
					load : function(response, ioArgs) {
						 var newData = {
									identifier: "stateCityStatus",
									items: response
							}; 
							
						var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId1"});
							var grid = dijit.byId("gridId");
							  grid.setStore(dataStore);
					},
					error : function(response, ioArgs) {
						//alert("An error occurred while invoking the service.");
					}
				});
				
			}, true);
			
			
			//OnLoad load the grid
				dojo.xhrPost({
				url : "${pageContext.request.contextPath}/stateListGrid",
				handleAs : "json",
				content : {
					firstParam : ""
					 
				},
				
				 load : function(response, ioArgs) {
					var newData = {	identifier: "stateCityId",items: response};
						
					var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId"});
					var grid = dijit.byId("gridId");
					grid.setStore(dataStore);
					  
				},error : function(response, ioArgs) {
					alert("An error occurred while invoking the service.");
				}
			});//GRID
			

			});
			
	});
		
	//Grid Onclick
	
	//gridd click ends
	
/***
 * 
 *Formatters for the images to e dispalyed
 *
 */
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
	  
	 document.getElementById("hiddenStateId").setAttribute("value", id);
	  document.getElementById("state1").setAttribute("value",name); 
 }
  
  function saveState(){
	  var stateName = document.getElementById("state1").value;
	  var hiddenValue=document.getElementById("hiddenStateId").value;
	
	  var method = "${pageContext.request.contextPath}/saveState";

	  if(hiddenValue!=0){
		  method = "${pageContext.request.contextPath}/updateNewState";
	  }
		  
	  require([ "dojo/_base/xhr"], function(ajax) {
		  
		  ajax.post({
				url :method,	
				handleAs : "json",
				content : {
					stateData : stateName,
					stateId : hiddenValue
				},
				
				load : function(result) {
					
					//OnLoad load the grid
					dojo.xhrPost({
					url : "${pageContext.request.contextPath}/stateListGrid",
					handleAs : "json",
					content : {
						firstParam : ""
						 
					},
					
					 load : function(response, ioArgs) {
						//alert("success");
						var newData = {	identifier: "stateCityId",items: response};
							
						var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId"});
						var grid = dijit.byId("gridId");
						grid.setStore(dataStore);
						  
					},error : function(response, ioArgs) {
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
	    </script>
	
	</head>
	<body class="claro">
	<div>
	
	State<input type="text"  name="state" id="state1"  data-dojo-type="dijit/form/TextBox" />
		 <input type="hidden" name="hiddenStateId" id="hiddenStateId" data-dojo-type="dijit/form/TextBox" /> 
		<button type="submit" id="stateSubmit" onclick="saveState();"  data-dojo-type="dijit/form/Button" >
		<span>Save</span> </button>
		</div>
	
	
	
	<table  data-dojo-type="dojox.grid.DataGrid"   data-dojo-props="rowSelector:'20px'" id="gridId"    
		style="width: 670px; height: 200px;">
	    <thead>
	        <tr>
	        <th width="50px" field="slNo" noresize="true">S.no</th>
            <th width="300px" field="stateCityName"  sortable="false">State</th>
            <th width="50px" field="stateCityStatus" formatter="getActionColumn">Action </th>  
            <th width="50px" field="stateCityStatus" formatter="deleteRow">Delete </th>
            <th width="50px" field="stateCityStatus" formatter="updaterow">Update</th> 
  	             
	        </tr>
	        
	    </thead>
    
	</table>

</body>
	
</html>
   
