<%@page import="db.CarListBean"%>
<%@page import="db.RentcarDAO"%>
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
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id ="rbean" class="db.CarReserveBean">
	<jsp:setProperty name="rbean" property="*"/>
</jsp:useBean>
<%
	String id = (String)session.getAttribute("id");
	if(id==null){
%>
	<script>
		alert("로그인후 구매가 가능 합니다.");
		location.href='RentcarMain.jsp?center=ForwardLogin.jsp';
	</script>
<%
	}
	//결과적으로 아무런 문제가 없다면데이터 저장후 결과 페이지 보여주기
	//아이디 값이 빈클래스에 없기에
	String id1 = (String)session.getAttribute("id");
	rbean.setId(id1);
	//데이터 베이스에 빈클래스를 저장
	RentcarDAO rdao = new RentcarDAO();
	rdao.setReserveCar(rbean); 
	//차량정보 얻어오기
	CarListBean cbean = rdao.getOneCar(rbean.getNo());
	//차량 총 금액
	int totalcar = cbean.getPrice()*rbean.getQty();
	//옵션금액
	int sun = 0;
	if(rbean.getSun()==1)sun=10;
	int wifi = 0;
	if(rbean.getWifi()==1)wifi=10;
	int navi = 0;
	if(rbean.getNavi()==1)navi=10;
	int seat = 0;
	if(rbean.getSeat()==1)seat=10;
	int totaloption = rbean.getQty()*(sun+wifi+navi+seat);
%>
<center>
	<table width="1000">
	<tr height="100">
		<td align="center">
		<font size="6" color="gray"> 차량 구매 완료 화면 </font></td>
	</tr>
		<td align="center">
		<img alt="" src="img/<%=cbean.getImg() %>" width="470"></td>	
	</tr>
	<tr height="50">
		<td align="center">
		<font size="5" color="red"> 차량 구매 금액<%=totalcar %>만원 </font></td>
	</tr>
	<tr height="50">
		<td align="center">
		<font size="5" color="red"> 차량 총 옵션 금액<%=totaloption %>만원 </font></td>
	</tr>
	<tr height="50">
		<td align="center">
		<font size="5" color="red"> 차량 금액<%=totaloption+totalcar %>만원 </font></td>
	</tr>
</table>
</center>
</body>
</html>