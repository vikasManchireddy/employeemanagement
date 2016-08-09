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

			
			dijit.byId('loginBtnPopup').on("click", function() {
				var userId = dijit.byId("userIdPopup").get("value");
				var kinId = dijit.byId("kinIdPopup").get("value");
				
				alert(userId+kinId);
				ajax.post({
					url : "${pageContext.request.contextPath}/forgot",
					handleAs : 'text',
					content : {
						userIdPopup : userId,
						kinIdPopup : kinId
					},
					load : function(result) {
						alert("your password : " + result);
						//document.getElementById("showPassword").innerHTML= "your password : " + result;

					},
					error : function() {
						alert("Db not connected");

					}
				});

			});

		});
	});
</script>




</head>
<body class="claro">



	<form action="Welcome" method="post">
		<table width="60%" cellspacing="1" cellpadding="1" align="center"
			border="0" BGCOLOR="#CCCCCC">
			<tr BGCOLOR="#FFFFFF">
				<td colspan="2" align="center"><font size="5" color="#990000"><b>Login</b></font>

				</td>
			</tr>
			<tr BGCOLOR="#FFFFFF" align="center"><font size="5" color="#990000">${reasonObject}</font></tr>
			<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center"><font size="4">User Id</font></td>
				<td width="50%" align="center"><input type="text" value="pistari"
					required="true" name="userId" id="userId" placeholder="Your Id"
					data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>

			<tr BGCOLOR="#FFFFFF">
				<td width="50%" align="center"><font size="4">Password</font></td>
				<td width="50%" align="center"><input type="password"
					required="true" name="password" id="password" value="123"
					placeholder="Your Password"
					data-dojo-type="dijit/form/ValidationTextBox" /></td>
			</tr>




			<tr BGCOLOR="#FFFFFF">
				<td width="100%" colspan="2" align="center">
					<button type="submit" align="center" id="loginBtn"
						data-dojo-type="dijit/form/Button">
						<span>Login</span>
					</button>
			</tr>

			<tr BGCOLOR="#FFFFFF">
				<td width="100%" align="right" colspan="2"><a
					href="javascript:void(0)" onclick="showDialog()"> Forget
						Password?</a></td>

			</tr>






		</table>
	</form>
	<div id="dialogOne" data-dojo-type="dijit.Dialog"
		title="Forgot Password">
		<div style="width: 400px; height: 200px;">



			<table width="100%" cellspacing="1" cellpadding="1" align="center"
				border="0" BGCOLOR="#CCCCCC">
				<tr BGCOLOR="#FFFFFF">
					<td colspan="2" align="center"><font size="5" color="#990000"><b>Forgot
								Password </b></font></td>
				</tr>
				<tr BGCOLOR="#FFFFFF">
					<td width="50%" align="center"><font size="4">User Id<font
							color=red>*</font>
					</font></td>
					<td width="50%" align="center"><input type="text"
						required="true" name="userIdPopup" id="userIdPopup"
						placeholder="Your Id"
						data-dojo-type="dijit/form/ValidationTextBox" /></td>
				</tr>

				<tr BGCOLOR="#FFFFFF">
					<td width="50%" align="center"><font size="4">Kin Id <font
							color=red>*</font>
					</font></td>
					<td width="50%" align="center"><input type="text"
						required="true" name="kinIdPopup" id="kinIdPopup"
						placeholder="Your KindId"
						data-dojo-type="dijit/form/ValidationTextBox" /></td>
				</tr>




				<tr BGCOLOR="#FFFFFF">
					<td width="100%" colspan="2" align="center">
						<button type="button" align="center" id="loginBtnPopup"
							data-dojo-type="dijit/form/Button">
							<span>Get Password</span>
						</button>
				</tr>

				<!-- <tr BGCOLOR="#FFFFFF">
					<td width="100%"  colspan="2" align="center">
					<span id="showPassword"></span>
					</td>
				</tr> -->
				







			</table>
		</div>
	</div>
</body>
</html>