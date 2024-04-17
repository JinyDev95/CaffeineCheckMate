package coffeeList.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;

import coffeeList.dto.Coffee;
import jdbc.JdbcUtil;

public class CoffeeListDao {
	//커피 목록 DAO
	public ArrayList<Coffee> CoffeeListView(Connection conn) throws SQLException {
		String listViewSQL = "SELECT * "+
				 			 "FROM COFFEELIST";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Coffee> coffeeList = new ArrayList<Coffee>();
		System.out.println("카트라이더 DAO");
		
		try {
			pstmt = conn.prepareStatement(listViewSQL);
			rs = pstmt.executeQuery();
			Coffee rsCoffeeView = null;
			
			while(rs.next()) {
				rsCoffeeView = new Coffee(
					rs.getInt("C_NO"),
					rs.getString("C_NAME"),
					rs.getString("C_BRAND"),
					rs.getInt("C_CAFFAINE"),
					rs.getString("C_IMG_COPY")
				); coffeeList.add(rsCoffeeView);
			}
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return coffeeList;	
	}
	
	//커피 상세 내역 DAO
    public Coffee getCoffeeDetail(Connection conn, int coffeeNo) throws SQLException {
        String detailViewSQL = "SELECT * FROM COFFEELIST WHERE C_NO = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Coffee coffeeDetail = null;

        try {
            pstmt = conn.prepareStatement(detailViewSQL);
            pstmt.setInt(1, coffeeNo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                coffeeDetail = new Coffee(
                		rs.getInt("C_NO"), 
                		rs.getString("C_NAME"), 
                		rs.getString("C_BRAND"),
                        rs.getInt("C_CAFFAINE"), 
                        rs.getInt("C_SACCHARIDE"),
                        rs.getInt("C_CALORIE"),
                        rs.getString("C_CONTENT"),
                        rs.getString("C_IMG_COPY")
                        );
            }
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
        return coffeeDetail;
    }


	public Coffee selectByCoffeeNo(int coffeeNo, Connection conn) throws SQLException {
		System.out.println("coffeelistdao1");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from COFFEELIST where C_NO=?;");
			pstmt.setInt(1, coffeeNo);
			rs = pstmt.executeQuery();
			Coffee coffee = null;
			if(rs.next()) {
				coffee = new Coffee(
						rs.getInt("C_NO"),
						rs.getInt("C_CAFFAINE"),
						rs.getString("C_IMG_COPY")
						);
			}
			return coffee;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public void plusFav(int coffeeNo, Connection conn) throws SQLException {
		System.out.println("coffeelist dao 2");
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"update COFFEELIST set C_FAVORITE = C_FAVORITE+1 where C_NO=?;");
			pstmt.setInt(1, coffeeNo);
			int pF = pstmt.executeUpdate();
			System.out.println("plusFav affect : "+pF+" rows");
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public void minusFav(int coffeeNo, Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"update COFFEELIST set C_FAVORITE = C_FAVORITE-1 where C_NO=?;");
			pstmt.setInt(1, coffeeNo);
			int mF = pstmt.executeUpdate();
			System.out.println("minusFav affect : "+mF+" rows");
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public HashMap<Integer, Coffee> getCoffeesByFav(Connection conn) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HashMap<Integer,Coffee> coffeeFavMap = new HashMap<Integer, Coffee>();
		try {
			String query = "select C_NO, C_CAFFAINE, C_IMG_COPY, row_number() over (order by C_FAVORITE desc, C_NAME) as idx from COFFEELIST limit 5;";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				coffeeFavMap.put(rs.getInt("idx"),new Coffee(
						rs.getInt("C_NO"),
						rs.getInt("C_CAFFAINE"),
						rs.getString("C_IMG_COPY")
						));
			}
			return coffeeFavMap;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
}