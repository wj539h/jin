package com.jin.callback.callbacktemplategeneric;

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
public interface StatementCallback<T> {
	T doInStatement(Statement stmt) throws SQLException;
}


