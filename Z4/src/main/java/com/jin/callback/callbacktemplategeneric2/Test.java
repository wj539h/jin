package com.jin.callback.callbacktemplategeneric2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jin.callback.User;


/**   
  * @project: java.util
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@163.com
 */
public class Test {

	public static void main(String[] args) throws Exception{
		
		String sql = "select * from User";
		JdbcTemplate jt = new JdbcTemplate();
		List<User> userList = jt.queryForObject(sql, new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("user_name"));
				user.setBirth(rs.getDate("birth"));
				user.setCreateDate(rs.getDate("create_date"));
				
				return user;
			}
			
		});
		
		System.out.println(userList);
	}
}


