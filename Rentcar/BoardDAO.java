package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	/*String id ="system";
	String pass="java";
	String url="jdbc:oracle:thin:@localhost:1521:XE";//접속 url
	*/
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//데이터 베이스의 커넥션풀을 사용하도록 설정하는 메소드
	public void getCon() {
		
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			//datasource
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//하나의 새로운 게시글이 넘어와서 저장되는 메소드
	public void insertBoard(BoardBean bean) {
		
		getCon();
		//빈클래스에 넘어오지 않았던 데이터들을 초기화 해주어야 합니다.
		int ref=0;//글그룹을 의미 = 쿼리를 실행시켜서 가장큰 ref값을 가져온후 +1을 더해주면됨
		int re_step=1;//새글이기에 = 부모글이기에
		int re_level=1;
		
		try {
			//가장큰 ref값을 읽어오는 쿼리 준비
			String refsql="select max(ref) from board";
			//쿼리준비
			pstmt = con.prepareStatement(refsql);
			//쿼리실행후 결과를 리턴
			rs=pstmt.executeQuery();
			if(rs.next()) {//결과값이 있다면
				ref = rs.getInt(1)+1;//최대값에 +1 더해서 글그룹을 설정
			}
			//실제로 게시글 전체값을 테이블에 저장
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			//?에 값을 매핑
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
			//쿼리를 실행하시오
			pstmt.executeUpdate();
			//자원반납
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//모든 게시글을 리턴해주는 메소드 작성
	public Vector<BoardBean> getAllBoard(){
		//리턴할객체 선언
		Vector<BoardBean> v= new Vector<>();
		getCon();
		
		try {
			//쿼리 준비
			String sql="select * from board order by ref desc, re_step asc";
			//쿼리실행
			pstmt=con.prepareStatement(sql);
			//쿼리실행후 결과저장
			rs = pstmt.executeQuery();
			//데이트개수가몇개인지 모르기에 반복문을 이용하여 데이터를 추출
			while(rs.next()) {
				//데이터를 패키징(가방=Boardbean클래스를이요)해줌
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				//패키징한 데이터를 백터에 저장
				v.add(bean);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}
	//하나의 게시글을 리턴하는 메소드
	public BoardBean getOneBoard(int num) {
		//리턴 타입 선언
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			//조회수 증가 쿼리
			String readsql = "update board set readcount = readcount+1 where num=?";
			pstmt = con.prepareStatement(readsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			//쿼리준비
			String sql="select * from board where num=?";
			//쿼리실행객체
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//z쿼리 실행후 결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			//자료반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
}
