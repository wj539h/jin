package com.jak.pattern.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Jakoes Wu <br>
 * @create 2009-7-26 <br>
 * @project SpringTemplateCallbackTemplate <br>
 * @email: jakoes.wu@163.com
 * @copyright  
 * @desc <br>
 */
public class HsqldbUtil {

	static{
		initDb();
	}
	private  HsqldbUtil(){
	}
	/**
	 * 初始化化内存数据库
	 * 
	 * @author Jakoes Wu <br>
	 * @mail wujianchao@hongguaninfo.com <br>
	 * @create 2009-7-26 <br>
	 * @update 2009-7-26 <br>
	 * @version 1.0<br>
	 * @desc <br>
	 */
	private static void initDb() {

		/*
		 * <property name="username" value="sa" /> <property name="password"
		 * value="" /> <property name="driverClassName"
		 * value="org.hsqldb.jdbcDriver" /> <property name="url"
		 * value="jdbc:hsqldb:mem:." /> <property name="poolPreparedStatements"
		 * value="true" />
		 */

		// 创建表
		String createSql = "create table User (id bigint generated by default as identity (start with 1), user_name varchar(255) not null,birth date null,create_date TIMESTAMP null, primary key (id), unique (id));";
		// 增加3条测试数据
		createSql += "insert into User(id,user_name,birth) values(1,'jakoes',sysdate);";
		createSql += "insert into User(id,user_name,create_date) values(2,'brier',CURTIME());";
		createSql += "insert into User(id,user_name) values(3,'leo');";

		Connection con = null;
		PreparedStatement pst = null;

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:mem:test", "sa", "");// mem:内存模式，数据库名称test，用户名sa，密码为空
			pst = con.prepareStatement(createSql);
			pst.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static Connection getConnection(){
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:hsqldb:mem:test", "sa", "");
			
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
