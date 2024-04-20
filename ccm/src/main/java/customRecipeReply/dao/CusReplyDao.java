package customRecipeReply.dao;

import static jdbc.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import customRecipeReply.dto.CusReplyDto;

public class CusReplyDao {
	
	public CusReplyDao() {
	}
	
	
	/*댓글 목록*/
	public ArrayList <CusReplyDto> selectReplyList(Connection conn, int cus_no){
		
		ArrayList<CusReplyDto> cus_re_list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT M.M_ID, M.M_NICKNAME, C.CUS_NO, R.CUS_RE_NO, R.CUS_RE_REGDATE, R.CUS_RE_CONTENT FROM CUSTOM_REPLY R, CUSTOM C, MEMBER M  WHERE C.M_ID = R.M_ID AND M.M_ID = R.M_ID AND C.CUS_NO = ? ORDER BY CUS_RE_REGDATE DESC";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cus_no);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				cus_re_list.add(new CusReplyDto(
						rset.getString("M.M_ID"),
						rset.getString("M.M_NICKNAME"),
						rset.getInt("C.CUS_NO"),
						rset.getInt("R.CUS_RE_NO"),
						rset.getString("R.CUS_RE_REGDATE"),
						rset.getString("R.CUS_RE_CONTENT")
						));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return cus_re_list;
		
	}
	
	
	/*댓글 삽입*/
	public int insertReply(Connection conn, CusReplyDto reply) {
		
	    int result = 0;
	    PreparedStatement pstmt = null;
	   
	    String sql = "INSERT INTO CUSTOM_REPLY(M_ID, CUS_NO, CUS_RE_CONTENT) VALUES(?, ?, ?)";
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, reply.getM_id());
	        pstmt.setInt(2, reply.getCus_no());
	        pstmt.setString(3, reply.getCus_re_content());
	        
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    
	    return result;
	    
	}
	
	
	/* 댓글 수정 */
	public int updateReply(Connection conn, CusReplyDto reply) {
		
	    int result = 0;
	    PreparedStatement pstmt = null;
	    
	    String sql = "UPDATE CUSTOM_REPLY SET CUS_RE_CONTENT = ? WHERE CUS_RE_NO = ?";
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, reply.getCus_re_content());
	        pstmt.setInt(2, reply.getCus_re_no());
	        
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    
	    return result;
	}

	
    /* 댓글 삭제 */
    public int deleteReply(Connection conn, int cus_re_no) {
    	
        int result = 0;
        PreparedStatement pstmt = null;
        
        String sql = "DELETE FROM CUSTOM_REPLY WHERE CUS_RE_NO = ?";
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cus_re_no);
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        
        return result;
    }
    
    
}
