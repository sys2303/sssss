<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 세션을 이용한 로그인 처리 -->
<%
	String id = (String)session.getAttribute("id");
	//로그인이 되어있지 않다면
	if(id==null){
		id="GUEST";		
	}
%>
<table width="1000">
	<tr height="70">
		<td colspan="4">
			<a href="RentcarMain.jsp" style="text-decoration:none">
			<img alt = "" src="img/main1.png" height="65">
			</a>
		</td>
		<td align="center" width="200">
		<%=id %>님 
		<%
			if(id.equals("GUEST")){%>
			<button onclick="location.href='RentcarMain.jsp?center=ForwardLogin.jsp'">로그인</button>	
			<button onclick="location.href='RentcarMain.jsp?center=MemberJoin.jsp'">회원가입</button>
		<%
			}else{%>
			<button onclick="location.href='ForwardLoout.jsp'">로그아웃</button>		
		<%
			}
		%>
		</td>			
	</tr>
	<tr height="50">
		<td align="center" width="200" bgcolor="PowderBlue">
			<font color="Snow" size="5"><a href="RentcarMain.jsp?center=CarReserveMain.jsp" style="text-decoration:none"> 구매하기 </a></font>
		</td>
		<td align="center" width="200" bgcolor="PowderBlue">
			<font color="WHITE" size="5"><a href="#" style="text-decoration:none"> 구매확인 </a></font>
		</td>
		<td align="center" width="200" bgcolor="PowderBlue">
			<font color="WHITE" size="5"><a href="RentcarMain.jsp?center=BoardWriteForm.jsp" style="text-decoration:none"> 자유게시판 </a></font>
		</td>
		<td align="center" width="200" bgcolor="PowderBlue">
			<font color="WHITE" size="5"><a href="#" style="text-decoration:none"> 이벤트 </a></font>
		</td>
		<td align="center" width="200" bgcolor="PowderBlue">
			<font color="WHITE" size="5"><a href="#" style="text-decoration:none"> 고객센터 </a></font>
		</td>	
	</tr>
	
</table>
</body>
</html>