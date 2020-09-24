package com.jin.callback.callbacktemplatefull;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class RowMapperResultSetExtractor implements ResultSetExtractor {

	private final RowMapper rowMapper;

	private final int rowsExpected;

	public RowMapperResultSetExtractor(RowMapper rowMapper) {
		this(rowMapper, 0);
	}

 
	public RowMapperResultSetExtractor(RowMapper rowMapper, int rowsExpected) {
		this.rowMapper = rowMapper;
		this.rowsExpected = rowsExpected;
	}


	public Object extractData(ResultSet rs) throws SQLException {
		List results = (this.rowsExpected > 0 ? new ArrayList(this.rowsExpected) : new ArrayList());
		int rowNum = 0;
		while (rs.next()) {
			results.add(this.rowMapper.mapRow(rs, rowNum++));
		}
		return results;
	}
}


