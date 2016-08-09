<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>





<link rel="stylesheet"
	href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css">
<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>
<script
	src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'></script>


<script>
	require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready", "dijit/Dialog" ],
			function(parser, ajax, ready) {
				ready(function() {

				});
			});

	function showDialog() {

		dijit.byId("dialogOne").show();
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




		<table width="60%" cellspacing="1" cellpadding="1" align="center"
			border="0" BGCOLOR="#CCCCCC">
			<tr BGCOLOR="#FFFFFF">
				<td colspan="2" align="center"><font size="5" color="#990000"><b>Reset Password</b></font>

				</td>
			</tr>
			<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center"><font size="4">Old Password</font></td>
				<td width="50%" align="center"><input type="password"
					required="true" name="OldPopup" id="OldPopup" placeholder="Your Id"
					data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

			<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center"><font size="4">New Password</font></td>
				<td width="50%" align="center"><input type="password"
					required="true" name="NewPopup" id="NewPopup"
					placeholder="Your Password"
					data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center"><font size="4">Confirm New Password</font></td>
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

	
</body>
</html>