package com.jak.pattern.template.classictemplate;

import com.jak.pattern.template.User;

import java.sql.SQLException;
import java.util.List;


/**   
  * @project: java.util
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@163.com
 */
public class Test {

	public static void main(String[] args) throws SQLException {
		
		String sql = "select * from User";
		JdbcTemplate jt = new JdbcTemplateUserImpl();
		List<User> userList = (List<User>) jt.execute(sql);
		System.out.println(userList);
		
	}
}


