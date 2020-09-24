//package com.jin.callback.classictemplate;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.jak.hsqldb.User;
//
///**   
//  * @project: java.util
// * @description: 
// * @author: jakoes.wu
// * @create_time: 2010-7-15
// * @modify_time: 2010-7-15
// * @email:jakoes.wu@163.com
// */
//public class JdbcTemplateUserImplBak extends JdbcTemplate {
//
//	@Override
//	protected Object doInStatement(Statement stmt, String sql) {
//		List<User> userList = new ArrayList<User>();
//		
//		try {
//			ResultSet rs = stmt.executeQuery(sql);
//			User user = null;
//			while (rs.next()) {
//
//				user = new User();
//				user.setId(rs.getInt("id"));
//				user.setUserName(rs.getString("user_name"));
//				user.setBirth(rs.getDate("birth"));
//				user.setCreateDate(rs.getDate("create_date"));
//				userList.add(user);
//			}
//			return userList;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//}
//

