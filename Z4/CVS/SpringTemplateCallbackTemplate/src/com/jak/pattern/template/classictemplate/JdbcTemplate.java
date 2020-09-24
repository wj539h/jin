package com.jak.pattern.template.classictemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jak.pattern.template.HsqldbUtil;


/**   
  * @project: java.util
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@163.com
 */
public abstract class JdbcTemplate {

	//template method
	public final Object execute(String sql) throws SQLException{
		
		Connection con = HsqldbUtil.getConnection();
		Statement stmt = null;
		try {
 
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Object result = doInStatement(rs);//abstract method 
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
	
	//implements in subclass
	protected abstract Object doInStatement(ResultSet rs);
}


