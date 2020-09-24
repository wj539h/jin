package com.jak.pattern.template.callbacktemplategeneric2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.jak.pattern.template.HsqldbUtil;


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
	private final <E> E execute(StatementCallback<E> action) throws SQLException{
		
		Connection con = HsqldbUtil.getConnection();
		Statement stmt = null;
		try {
 
			stmt = con.createStatement();
			E result = action.doInStatement(stmt);//abstract method 
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
 
	private <T> T query(final String sql, final ResultSetExtractor<T> rse) throws SQLException{
		
		class QueryStatementCallback implements StatementCallback<T> {

			public T doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = stmt.executeQuery(sql);

				return rse.extractData(rs);
			}

		}
		 return execute(new QueryStatementCallback());
	}
	
	private <T> List<T> query(String sql, RowMapper<T> rowMapper) throws Exception {
		return query(sql, new RowMapperResultSetExtractor<T>(rowMapper));
	}	
	
	public <T> List<T> queryForObject(String sql, RowMapper<T>  rowMapper) throws Exception {
		return  query(sql, rowMapper);
	}
}


