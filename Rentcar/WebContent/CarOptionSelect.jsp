<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	int no = Integer.parseInt(request.getParameter("no"));
	//수량
	int qty = Integer.parseInt(request.getParameter("qty"));
	//이미지
	String img = request.getParameter("img");
%>
<center>
<form action="RentcarMain.jsp?center=CarReserveResult.jsp" method="post">
<table width="1000">
	<tr height="100">
	<td align="center" colspan="3">
	<font size="4" color="gray"> 옵션선택 </font></td>
	</tr>
	<tr>
		<td rowspan="7" width="500">
		<img alt="" src="img/<%=img %>" width="450"></td>
		<td width="250" align="center"> 썬루프 </td>
		<td width="250" align="center"><select name ="sun"> 
										<option value="1">적용</option>
										<option value="2">미적용</option>
										</select></td>
	</tr>
	<tr>
	<td width="250" align="center"> Wifi 적용 </td>
		<td width="250" align="center"><select name ="wifi"> 
										<option value="1">적용</option>
										<option value="2">미적용</option>
										</select></td>
	</tr>
	<tr>
	<td width="250" align="center"> 네비게이션 </td>
		<td width="250" align="center"><select name ="navi"> 
										<option value="1">적용</option>
										<option value="2">미적용</option>
										</select></td>
	</tr>
	<tr>
	<td width="250" align="center"> 베이비시트 적용 </td>
		<td width="250" align="center"><select name ="seat"> 
										<option value="1">적용</option>
										<option value="2">미적용</option>
										</select></td>
	</tr>
	<tr>
	<td align="center" colspan="2"><input type="submit" value="차량구매"> </td>
	</tr>
</table>
</form>
</center>	
</body>
</html>