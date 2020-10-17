package com.jak.pattern.template.callbacktemplate;

import com.jak.pattern.template.HsqldbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**   
  * @project: java.util
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@163.com
 */
public class JdbcTemplate {

	//template method
	private final Object execute(StatementCallback action) throws SQLException{
		
		Connection con = HsqldbUtil.getConnection();
		Statement stmt = null;
		try {
 
			stmt = con.createStatement();
			Object result = action.doInStatement(stmt);//abstract method 
			return result;
		}
		catch (SQLException ex) {
			 ex.printStackTrace();
			 throw ex;
		}
		finally {
 
			try {
				stmt.close();
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
	}
 
	public Object query(StatementCallback stmt) throws SQLException{
		
		 
		return execute(stmt);
	}
}


