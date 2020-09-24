package com.jin.callback.example6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 一个简单的实现JDBC增删改查功能的模板
 */
public abstract class JDBCTemplate {
    /**
     * 定义当前的操作类型是新增
     */
    protected final static int TYPE_CREATE = 1;
    /**
     * 定义当前的操作类型是修改
     */
    protected final static int TYPE_UPDATE = 2;
    /**
     * 定义当前的操作类型是删除
     */
    protected final static int TYPE_DELETE = 3;
    /**
     * 定义当前的操作类型是按条件查询
     */
    protected final static int TYPE_CONDITION = 4;

    /*---------------------模板方法---------------------*/
    /**
     * 实现新增的功能
     * 
     * @param obj 需要被新增的数据对象
     */
    public final void create(Object obj) {
	//1：获取新增的sql
	String sql = this.getMainSql(TYPE_CREATE);
	//2：调用通用的更新实现
	this.executeUpdate(sql, TYPE_CREATE, obj);
    }

    /**
     * 实现修改的功能
     * 
     * @param obj 需要被修改的数据对象
     */
    public final void update(Object obj) {
	//1：获取修改的sql
	String sql = this.getMainSql(TYPE_UPDATE);
	//2：调用通用的更新实现
	this.executeUpdate(sql, TYPE_UPDATE, obj);
    }
    
    public final void updateWithCallBack(Object obj) {
	String sql = this.getMainSql(TYPE_UPDATE);
	final UserModel um = (UserModel)obj;
	this.executeUpdate(sql, new UpdateDBCallBack() {
	    @Override
	    public void doSetValue(PreparedStatement pstmt) throws Exception {
		pstmt.setString(1, um.getName());
		pstmt.setInt(2, um.getAge());
		pstmt.setString(3, um.getUuid());
	    }
	});
    }

    /**
     * 实现删除的功能
     * 
     * @param obj 需要被删除的数据对象
     */
    public final void delete(Object obj) {
	//1：获取删除的sql
	String sql = this.getMainSql(TYPE_DELETE);
	//2：调用通用的更新实现
	this.executeUpdate(sql, TYPE_DELETE, obj);
    }

    /**
     * 实现按照条件查询的功能
     * 
     * @param qm 封装查询条件的数据对象
     * @return 符合条件的数据对象集合
     */
    public final Collection getByCondition(Object qm) {
	//1：获取查询的sql
	String sql = this.getMainSql(TYPE_CONDITION);
	//2：调用通用的查询实现
	return this.getByCondition(sql, qm);
    }

    /*---------------------原语操作---------------------*/
    /**
     * 获取操作需要的主干sql
     * 
     * @param type 操作类型
     * @return 操作对应的主干sql
     */
    protected abstract String getMainSql(int type);

    /**
     * 为更新操作的sql中的"?"设置值
     * 
     * @param type  操作类型
     * @param pstmt PreparedStatement对象
     * @param obj   操作的数据对象
     * @throws Exception
     */
    protected abstract void setUpdateSqlValue(int type, PreparedStatement pstmt, Object obj) throws Exception;

    /**
     * 为通用查询动态的拼接sql的条件部分，基本思路是： 只有用户填写了相应的条件，那么才在sql中添加对应的条件
     * 
     * @param sql sql的主干部分
     * @param qm  封装查询条件的数据模型
     * @return 拼接好的sql语句
     */
    protected abstract String prepareQuerySql(String sql, Object qm);

    /**
     * 为通用查询的sql动态设置条件的值
     * 
     * @param pstmt 预处理查询sql的对象
     * @param qm    封装查询条件的数据模型
     * @throws Exception
     */
    protected abstract void setQuerySqlValue(PreparedStatement pstmt, Object qm) throws Exception;

    /**
     * 把查询返回的结果集转换成为数据对象
     * 
     * @param rs 查询返回的结果集
     * @return 查询返回的结果集转换成为数据对象
     * @throws Exception
     */
    protected abstract Object rs2Object(ResultSet rs) throws Exception;

    /*---------------------钩子操作---------------------*/
    static {
	initDb();
    }
    private static void initDb() {
	String createSql = "create table tbl_testuser (uuid varchar(255) not null, name varchar(255) not null, age int not null, primary key (uuid), unique (uuid));";

	Connection con = null;
	PreparedStatement pst = null;

	try {
	    Class.forName("org.hsqldb.jdbcDriver");
	    con = DriverManager.getConnection("jdbc:hsqldb:mem:test", "sa", "");
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

    private Connection getConnection() throws Exception {
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

    /**
     * 执行查询
     * 
     * @param sql 查询的主干sql语句
     * @param qm  封装查询条件的数据模型
     * @return 查询后的结果对象集合
     */
    protected Collection getByCondition(String sql, Object qm) {
	Collection col = new ArrayList();
	Connection conn = null;
	try {
	    //调用钩子方法
	    conn = this.getConnection();
	    //调用原语操作
	    sql = this.prepareQuerySql(sql, qm);
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    //调用原语操作
	    this.setQuerySqlValue(pstmt, qm);
	    ResultSet rs = pstmt.executeQuery();
	    while (rs.next()) {
		//调用原语操作
		col.add(this.rs2Object(rs));
	    }
	    rs.close();
	    pstmt.close();
	} catch (Exception err) {
	    err.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
	return col;
    }

    /**
     * 执行更改数据的sql语句，包括增删改的功能
     * 
     * @param sql      需要执行的sql语句
     * @param callback 回调接口，回调为sql语句赋值的方法
     */
    protected void executeUpdate(String sql, int type, Object obj) {
	Connection conn = null;
	try {
	    //调用钩子方法			
	    conn = this.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    //调用原语操作
	    this.setUpdateSqlValue(type, pstmt, obj);
	    pstmt.executeUpdate();
	    pstmt.close();
	} catch (Exception err) {
	    err.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }
    
    protected void executeUpdate(String sql, UpdateDBCallBack callBack) {
	Connection conn = null;
	try {
	    conn = this.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    callBack.doSetValue(pstmt);
	    pstmt.executeUpdate();
	    pstmt.close();
	} catch (Exception err) {
	    err.printStackTrace();
	} finally {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }
}
