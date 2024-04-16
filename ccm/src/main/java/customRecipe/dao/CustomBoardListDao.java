package customRecipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import customRecipe.dto.CustomBoardListDto;
import jdbc.JdbcUtil;



public class CustomBoardListDao {

	
	
	//이미지포함 게시판리스트 출력
	public  ArrayList<CustomBoardListDto> getallList(Connection con) throws SQLException {
		//String sql="select CUS_NO,M_NO,C_NO,CUS_NAME,CUS_CONTENT,CUS_REGDATE,CUS_SUMGOOD from custom order by cus_regdate desc";
		//위 sql은 cus_no조회하는 쿼리문
		String sql = "select cus_no,num,C_NO,CUS_TITLE,CUS_CONTENT,CUS_REGDATE,CUS_SUMGOOD from custom_view";
			Statement st1 = con.createStatement();
			ResultSet rs = st1.executeQuery(sql);
			ArrayList<CustomBoardListDto> list = new ArrayList<>();
	
			while(rs.next()){

//				int CUS_NO = rs.getInt(1);
				CustomBoardListDto dto = new CustomBoardListDto();
				
	            dto.setCUS_NO(rs.getInt(1)); 
	            dto.setCUS_NUM(rs.getInt(2));
	            dto.setC_NO(rs.getString(3));
	            dto.setcus_title(rs.getString(4));
	            dto.setCUS_CONTENT(rs.getString(5));
	            dto.setCUS_REGDATE(rs.getString(6));
	            dto.setCUS_SUMGOOD(rs.getString(7));
	            
	            getimg(dto,con); 
	            list.add(dto);
			}
			JdbcUtil.close(rs); JdbcUtil.close(st1);
			return list;
	} 	

	
	
	//게시글번호로 해당이미지파일값 불러오기
	public void getimg(CustomBoardListDto dto, Connection con) throws SQLException {
		int num =dto.getcus_no();
	    String sql = "select CUS_IMG_NO,CUS_NO,CUS_IMG_REAL,CUS_IMG_COPY from custom_img where cus_no=?";
	    	PreparedStatement pstm = con.prepareStatement(sql);
	        pstm.setInt(1,num);
	        ResultSet rs = pstm.executeQuery();
	        if (rs.next()) {
	            dto.setCUS_IMG_NO(rs.getInt(1));
	            dto.setcus_img_realname(rs.getString(4));
	}
	        JdbcUtil.close(pstm); JdbcUtil.close(rs);
	        
}
	public ArrayList<CustomBoardListDto> readlist(int count,int allcount,Connection con) throws SQLException {
		
		String sql = "select cus_no,num,C_NO,CUS_TITLE,CUS_CONTENT,CUS_REGDATE,CUS_SUMGOOD from custom_view where num between ? and ?";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, allcount-7);
		pstm.setInt(2, allcount);
		ResultSet rs = pstm.executeQuery();
		ArrayList<CustomBoardListDto> list = new ArrayList<>();

		while(rs.next()){

//			int CUS_NO = rs.getInt(1);
			CustomBoardListDto dto = new CustomBoardListDto();
			
            dto.setCUS_NO(rs.getInt(1)); 
            dto.setCUS_NUM(rs.getInt(2));
            dto.setC_NO(rs.getString(3));
            dto.setcus_title(rs.getString(4));
            dto.setCUS_CONTENT(rs.getString(5));
            dto.setCUS_REGDATE(rs.getString(6));
            dto.setCUS_SUMGOOD(rs.getString(7));
            
            getimg(dto,con); 
            list.add(dto);
		}
		JdbcUtil.close(rs); JdbcUtil.close(pstm);
		return list;
	
	}
	
	
	public int allCount(Connection con) throws SQLException {
	    String sql = "select count(cus_no) from custom";
	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery(sql);
	    while(rs.next()) {
	    	return rs.getInt(1);
	    }
		return -1;
	}
}
