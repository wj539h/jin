package com.jak.pattern.template.callbacktemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.jak.pattern.template.User;



/**
  * @project: java.util
 * @description:
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@163.com
 */
public class Test {
	
	//内部类方式
	public Object query(final String sql) throws SQLException {
		class QueryStatementCallback implements StatementCallback {

			public Object doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = stmt.executeQuery(sql);
				List<User> userList = new ArrayList<User>();

				User user = null;
				while (rs.next()) {

					user = new User();
					user.setId(rs.getInt("id"));
					user.setUserName(rs.getString("user_name"));
					user.setBirth(rs.getDate("birth"));
					user.setCreateDate(rs.getDate("create_date"));
					userList.add(user);
				}
				return userList;

			}

		}

		JdbcTemplate jt = new JdbcTemplate();
		return jt.query(new QueryStatementCallback());
	}

	//匿名类方式
	public Object query2(final String sql) throws Exception{
		
		JdbcTemplate jt = new JdbcTemplate();
		return jt.query(new StatementCallback() {
			
			public Object doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = stmt.executeQuery(sql);
				List<User> userList = new ArrayList<User>();

				User user = null;
				while (rs.next()) {

					user = new User();
					user.setId(rs.getInt("id"));
					user.setUserName(rs.getString("user_name"));
					user.setBirth(rs.getDate("birth"));
					user.setCreateDate(rs.getDate("create_date"));
					userList.add(user);
				}
				return userList;

			}
		});
		
	}
	public static void main(String[] args) throws Exception {

		/*String sql = "select * from User";
		Test t = new Test();
		
		List<User> userList = (List<User>) t.query(sql);
		List<User> userList2 = (List<User>) t.query2(sql);
		System.out.println(userList);
		System.out.println(userList2);*/
		JdbcTemplate jt = new JdbcTemplate();
		jt.execute(new StatementCallback() {
			
			public Object doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = stmt.executeQuery(sql);
				List<User> userList = new ArrayList<User>();

				User user = null;
				while (rs.next()) {

					user = new User();
					user.setId(rs.getInt("id"));
					user.setUserName(rs.getString("user_name"));
					user.setBirth(rs.getDate("birth"));
					user.setCreateDate(rs.getDate("create_date"));
					userList.add(user);
				}
				return userList;

			}
		});
	}
}
