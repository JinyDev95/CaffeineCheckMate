package customRecipeReply.handler;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.ConnectionProvider;
import customRecipeReply.dao.CusReplyDao;
import jdbc.JdbcUtil;

@WebServlet("/CusReplyDeleteHandler")
public class CusReplyDeleteHandler extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	int cus_re_no = Integer.parseInt(request.getParameter("cus_re_no"));

    	Connection conn = null;

    	try {
    		conn = ConnectionProvider.getConnection();
    		CusReplyDao replyDao = new CusReplyDao();
    		replyDao.deleteReply(conn, cus_re_no);
    		response.getWriter().write("성공");

    	} catch (Exception e) {
    		e.printStackTrace();
    		response.getWriter().write("실패");
    	} finally {
    		JdbcUtil.close(conn);
	
    	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
