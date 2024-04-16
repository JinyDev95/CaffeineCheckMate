package customRecipeReply.service;

import static jdbc.JdbcUtil.close;
import static jdbc.JdbcUtil.rollback;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.ConnectionProvider;
import customRecipeReply.dao.CusReplyDao;
import customRecipeReply.dto.CusReplyDto;

public class CusReplyInsertService {
	
	/*댓글 삽입*/
	public int insertReply(CusReplyDto reply) throws SQLException {
		System.out.println("작성 서비스 왔슈--1");
		Connection conn = ConnectionProvider.getConnection();
		System.out.println("작성 서비스 왔슈--2");
		int result = new CusReplyDao().insertReply(conn, reply);
		System.out.println(result);
		System.out.println("작성 서비스 왔슈--3");
		
		if(result>0) {
			System.out.println("작성 서비스 왔슈--4");
		}else {
			rollback(conn);
		}
		System.out.println("작성 서비스 왔슈--4");
		
		close(conn);
		
		return result;
		
	}
}
