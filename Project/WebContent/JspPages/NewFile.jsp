<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link rel="stylesheet" href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css">
<script src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'></script>
<script>dojoConfig = {async: true, parseOnLoad: true} </script>



<script>
 
 
 
require(["dojo/parser","dojo/_base/xhr",  "dojo/ready","dijit/Dialog","dojox/grid/Grid","dojox/grid/_data/model"], function(parser, ajax,ready){
	ready(function (){ 
		alert(" dojo loaded"+'<%=request.getContextPath()%>' );
		
		 var view1 = {
		            cells: [
		                [
		                    {name: 'FullName', field: "FullName"},
		                    {name: 'KindId', field: "KindId"},
		                    {name: 'Education',field: "Education"},
		                    {name: 'Action',field: "Action"}
		                ]
		            ]
		};

		var layout = [ view1 ];
		
		model = new dojox.grid.data.Objects(null,null);

		function handleResponse(responseObject, ioArgs){
		    // set the model object with the returned customers list
		    model.setData(responseObject);        
		}   

		// make request to the customers web service 
		function loadTable(page){
// 		    start = page \* batchSize;
// 		    var targetURL = "resources/customers/?start="+ 
// 		                      encodeURIComponent(start);    
		    dojo.xhrGet({
		        url: "${pageContext.request.contextPath}/empList",
		        handleAs: "json",
		        load: handleResponse,
		        error: handleError
		    });
		}
	});
});



	/* require(["dojo/parser","dojo/_base/xhr",  "dojo/ready"], function(parser, ajax,ready){
 
		ready(function (){  
		  
			
			alert("Dojo loaded");
			/*dijit.byId('getValueBtn').on("click", function(){
				var fieldValue = dijit.byId('sl#').get('value');
				 
			});
			
			dijit.byId('setValueBtn').on("click", function(){
				var fieldValue2 = dijit.byId('state').set('value', 'New Value');
				 
			});

			dijit.byId('setValueBtn').on("click", function(){
				var fieldValue2 = dijit.byId('action').set('value', 'New Value');
				 
			});
			
			dijit.byId('saveBtn').on("click", function(){
				var State =  dijit.byId('State').get('value');
				
				 				
				ajax.post({
					url:"test",
					handleAs:'text',
					content: {
						slNumber: State,
						id: City
					},
					load: function(response) {
						alert("Success");
					},
					error: function() {
					}
				});
				 
			});
		});*/
			  
			</script> 
			<script type="text/javascript">
        dojo.addOnLoad(function(){
            loadTable(0);
        });
    </script>
	<style type="text/css">
     
      @import "src/dojox/grid/_grid/tundraGrid.css";
      @import "src/dijit/themes/tundra/tundra.css";
      @import "src/dojo/resources/dojo.css";
      @import "src/dojox/grid/_grid/Grid.css";
    </style>
    
    
    <script >
    
   
</script>


<style>
table, th, td {
    border: 2px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 15px;
}
.redBig{
 	color: #ff0000;
	font-style: bold;
	 font-size: double;	
}
.greenBig{
 	color: green;
	font-style: bold;
	 font-size: double;	
}

</style>
</head>
<body>


<table dojoType="dojox.Grid" model="model" structure="layout"></table>


</body>
</html>