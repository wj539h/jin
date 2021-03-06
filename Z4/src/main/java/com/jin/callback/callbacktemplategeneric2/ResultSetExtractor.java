package com.jin.callback.callbacktemplategeneric2;

import java.sql.ResultSet;
import java.sql.SQLException;

/**   
  * @project: java.util
 * @description: 
 * @author: jakoes.wu
 * @create_time: 2010-7-15
 * @modify_time: 2010-7-15
 * @email:jakoes.wu@163.com
 */
public interface ResultSetExtractor<E>{
	E extractData(ResultSet rs) throws SQLException;
}


