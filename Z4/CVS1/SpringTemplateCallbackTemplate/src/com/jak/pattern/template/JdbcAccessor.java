package com.jak.pattern.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

 

/**
  * @project: java.util
 * @description:
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@163.com
 */
public class JdbcAccessor {
	public List<User> query() {

		List<User> userList = new ArrayList<User>();
		String sql = "select * from User";

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = HsqldbUtil.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			User user = null;
			while (rs.next()) {

				user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("user_name"));
				user.setBirth(rs.getDate("birth"));
				user.setCreateDate(rs.getDate("create_date"));
				userList.add(user);
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(!con.isClosed()){
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return userList;
	}

	public static void main(String[] args) throws Exception {

		JdbcAccessor t = new JdbcAccessor();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss SSSS");

		List<User> userList = t.query();

		for (User u : userList) {
			System.out.println("id=" + u.getId() + ",name=" + u.getUserName()
					+ ",birth=" + u.getBirth() + ",createDate="
					+ u.getCreateDate());

			// if(u.getId() == 2){
			// System.out.println(dateFormat.format(u.getCreateDate()));
			// }
		}
	}
}
