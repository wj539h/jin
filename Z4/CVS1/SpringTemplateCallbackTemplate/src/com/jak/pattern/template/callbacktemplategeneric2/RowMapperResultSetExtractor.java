package com.jak.pattern.template.callbacktemplategeneric2;

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
public class RowMapperResultSetExtractor<E> implements ResultSetExtractor<List<E>> {

	private final RowMapper<E> rowMapper;

	private final int rowsExpected;

	public RowMapperResultSetExtractor(RowMapper<E> rowMapper) {
		this(rowMapper, 0);
	}

 
	public RowMapperResultSetExtractor(RowMapper<E> rowMapper, int rowsExpected) {
		this.rowMapper = rowMapper;
		this.rowsExpected = rowsExpected;
	}


	public List<E> extractData(ResultSet rs) throws SQLException {
		List<E> results = (this.rowsExpected > 0 ? new ArrayList<E>(this.rowsExpected) : new ArrayList<E>());
		int rowNum = 0;
		while (rs.next()) {
			results.add(this.rowMapper.mapRow(rs, rowNum++));
		}
		return results;
	}
}


