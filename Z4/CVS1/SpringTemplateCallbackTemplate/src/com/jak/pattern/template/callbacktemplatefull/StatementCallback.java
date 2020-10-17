package com.jak.pattern.template.callbacktemplatefull;

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
public interface StatementCallback {
	Object doInStatement(Statement stmt) throws SQLException;
}


