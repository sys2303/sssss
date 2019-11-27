package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class RentcarDAO {
	/*String id ="system";
	String pass="java";
	String url="jdbc:oracle:thin:@localhost:1521:XE";//접속 url
	*/
	 Connection con;
	 PreparedStatement pstmt;
	 ResultSet rs;
	 
	 //커넥션 풀을 이요한 데이터 베이스 연결
	 public void getCon() {
		 
		 try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	 }
	 //최신순 3대의 자동차를 리턴하는 메소드
	 public Vector<CarListBean> getSelectCar(){
		 //리턴타입을 설정
		 Vector<CarListBean> v =new Vector<>();
		 getCon();//커넥션이 연결되어야 쿼리를 실행 가능
		 
		 try {
			String sql = "select * from rentcar order by no desc";
			pstmt = con.prepareStatement(sql);
			//쿼리 실행후 
			rs = pstmt.executeQuery();
			int count=0;
			while(rs.next()) {
				
				CarListBean bean = new CarListBean();
				bean.setNo(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getInt(3));
				bean.setPrice(rs.getInt(4));
				bean.setUsepeople(rs.getInt(5));
				bean.setCompany(rs.getString(6));
				bean.setImg(rs.getString(7));
				bean.setInfo(rs.getString(8));
				//백터에 빈클래스를 저장
				v.add(bean);
				count++;
				if(count >2)break;//반복문을 빠져나가시오
				//3개만 벡터에 저장
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return v;
	 }
	 //카테고리별 자동차 리스트를 저장하는 메소드
	 public Vector<CarListBean> getCategoryCar(int cate){
		 Vector<CarListBean> v = new Vector<>();
		 //데이터를 저장할 빈 클래스 선언
		 CarListBean bean = null;
		 
		 getCon();
		 
		 try {
			String sql = "select * from rentcar where category=?";
			pstmt = con.prepareStatement(sql);
			//?
			pstmt.setInt(1, cate);
			//결과를 리턴
			rs= pstmt.executeQuery();
			//반복문을 돌면서 데이터를 저장
			while(rs.next()) {
				//데이터를 저장할 빈클래스 생성
				bean = new CarListBean();
				bean.setNo(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getInt(3));
				bean.setPrice(rs.getInt(4));
				bean.setUsepeople(rs.getInt(5));
				bean.setCompany(rs.getString(6));
				bean.setImg(rs.getString(7));
				bean.setInfo(rs.getString(8));
				//백터에 빈클래스를 저장
				v.add(bean);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return v;
	 }
	 //모든 차량을 검색하는 메소드
	 public Vector<CarListBean> getAllCar(){
	 Vector<CarListBean> v = new Vector<>();
	 //데이터를 저장할 빈 클래스 선언
	 CarListBean bean = null;
	 
	 getCon();
	 
	 try {
		String sql = "select * from rentcar";
		pstmt = con.prepareStatement(sql);
		
		//결과를 리턴
		rs= pstmt.executeQuery();
		//반복문을 돌면서 데이터를 저장
		while(rs.next()) {
			//데이터를 저장할 빈클래스 생성
			bean = new CarListBean();
			bean.setNo(rs.getInt(1));
			bean.setName(rs.getString(2));
			bean.setCategory(rs.getInt(3));
			bean.setPrice(rs.getInt(4));
			bean.setUsepeople(rs.getInt(5));
			bean.setCompany(rs.getString(6));
			bean.setImg(rs.getString(7));
			bean.setInfo(rs.getString(8));
			//백터에 빈클래스를 저장
			v.add(bean);
		}
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	 return v;
	}
	 //하나의 자동차 정보를 리턴하는 메소드
	 public CarListBean getOneCar(int no) {
		 CarListBean bean = new CarListBean();
		 getCon();
		 
		 try {
			 String sql = "select * from rentcar where no=?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, no);
			//결과를 리턴
				rs= pstmt.executeQuery();
				//반복문을 돌면서 데이터를 저장
				while(rs.next()) {
					
					bean.setNo(rs.getInt(1));
					bean.setName(rs.getString(2));
					bean.setCategory(rs.getInt(3));
					bean.setPrice(rs.getInt(4));
					bean.setUsepeople(rs.getInt(5));
					bean.setCompany(rs.getString(6));
					bean.setImg(rs.getString(7));
					bean.setInfo(rs.getString(8));
					
				}
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return bean;
	 }
	//회원정보가 있는지를 비교
		public int getMember(String id, String pass) {
			int result=0;//0이면 회원 없음
			getCon();
			
			try {
				String sql="select count(*) from member where id=? and pass1=?";
				pstmt = con.prepareStatement(sql);
				//?
				pstmt.setString(1,id);
				pstmt.setString(2, pass);
				//결과리턴
				rs = pstmt.executeQuery();
				if(rs.next()) {
					result=rs.getInt(1);//0또는 1값이 저장
				}
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		//하나의 구매 정보를 저장하는메소드
	 public void setReserveCar(CarReserveBean bean) {
		 getCon();
		 
		 try {
			 String sql = "insert into carreserve values(reserve_seq.NEXTVAL,?,?,?,?,?,?,?)";
			 pstmt = con.prepareStatement(sql);
			 //?에 값을 대입
			 pstmt.setInt(1, bean.getNo());
			 pstmt.setString(2, bean.getId());
			 pstmt.setInt(3, bean.getQty());
			 pstmt.setInt(4, bean.getSun());
			 pstmt.setInt(5, bean.getWifi());
			 pstmt.setInt(6, bean.getNavi());
			 pstmt.setInt(7, bean.getSeat());
			 
			 pstmt.executeUpdate();
			 
			 con.close();
		 } catch (Exception e) {
			e.printStackTrace();
		}
	 }
}