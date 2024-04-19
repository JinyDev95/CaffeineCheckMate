package customRecipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import customRecipe.dto.CustomBoardHashDto;
import jdbc.JdbcUtil;

public class CustomBoardHashDao {
	
	//해쉬넘버조회
	public ArrayList<CustomBoardHashDto> hashnum(Connection con, CustomBoardHashDto hashdto) throws SQLException {
		String sql = "select cus_no from custom_hash where 1=1";
		if(hashdto.getmilkType() != null && !hashdto.getmilkType().equals("null")) {
			sql += " and CUS_HASH_MILK=?";
		}
		if(hashdto.getdecaffeinated() !=null&& !hashdto.getdecaffeinated().equals("null")) {
			sql += " and CUS_HASH_DECAF=?";
		}
		if(hashdto.getshot() !="null"&& !hashdto.getshot().equals("null")) {
			sql += " and CUS_HASH_SHOT=?";
		}
		if(hashdto.gettoppingType() !="null"&& !hashdto.gettoppingType().equals("null")) {
			sql += " and CUS_HASH_TOP=?";
		}
		if(hashdto.getsyrupType() !="null"&& !hashdto.getsyrupType().equals("null")) {
			sql += " and CUS_HASH_SYRUP=?";
		}
		System.out.println(sql);
		System.out.println("--1--");
		PreparedStatement pstm = con.prepareStatement(sql);
		int a = 1;
		if(hashdto.getmilkType() != null && !hashdto.getmilkType().equals("null")) {
			pstm.setString(a++, hashdto.getmilkType());
		}
		if(hashdto.getdecaffeinated() !=null&& !hashdto.getdecaffeinated().equals("null")) {
			pstm.setString(a++, hashdto.getdecaffeinated());
		}
		if(hashdto.getshot() !="null"&& !hashdto.getshot().equals("null")) {
			pstm.setString(a++, hashdto.getshot());
		}
		if(hashdto.gettoppingType() !="null"&& !hashdto.gettoppingType().equals("null")) {
			pstm.setString(a++, hashdto.gettoppingType());
		}
		if(hashdto.getsyrupType() !="null"&& !hashdto.getsyrupType().equals("null")) {
			pstm.setString(a,hashdto.getsyrupType());
		}
		ArrayList<CustomBoardHashDto> list = new ArrayList<>();
		
		ResultSet rs = pstm.executeQuery();
		while(rs.next()) {
			CustomBoardHashDto cus_no = new CustomBoardHashDto();
			cus_no.setcus_no(rs.getString(1));
			list.add(cus_no);
			
		}
		
		JdbcUtil.close(pstm);
		JdbcUtil.close(rs);
		return list;
	}
}
