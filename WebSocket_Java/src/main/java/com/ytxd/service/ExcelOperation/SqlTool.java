package com.ytxd.service.ExcelOperation;

import com.ytxd.common.DataUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

public class SqlTool {
	// 设置数据库连接所需参数
	//@Value("${spring.datasource.driver-class-name}")
	private String driverName;
	//@Value("${spring.datasource.url}")
	private String dbURL;
	//@Value("${spring.datasource.username}")
	private String userName;
	//@Value("${spring.datasource.password}")
	private String userPwd;
	
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	static Connection conn = null;
	static Statement stmt = null;
	ResultSet rs = null;

	public SqlTool(String databaseName) {
		Properties properties = new Properties();
		// 使用InPutStream流读取properties文件
		try {
			String active = DataUtils.getConfInfo("spring.profiles.active");
			BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource("").toURI().getPath() + "application-" + active + ".yml"));
			properties.load(bufferedReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取key对应的value值
		driverName = properties.getProperty("driver-class-name");
		dbURL = properties.getProperty("url");
		userName = properties.getProperty("username");
		userPwd = properties.getProperty("password");
		
		getConnection();
	}

	// 获取Statement
	public Statement getStatement() {
		return (Statement) stmt;
	}

	// 获取连接
	public Connection getConnection() {
		try {
			// Save the original Java locale
			Locale originalLocale = Locale.getDefault();
			// Set "en_US" as the safe default
			Locale.setDefault(Locale.US);
			
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			
			Locale.setDefault(originalLocale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 查询方法，接收select语句并返回rs结果集

	public ResultSet executeDataTable(String preparedSql, Object[] param) {
		PreparedStatement pstmt = null;
		try {
			if(stmt==null || stmt.isClosed()){
				stmt = conn.createStatement();
			}				
			pstmt = (PreparedStatement) conn.prepareStatement(preparedSql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i + 1, param[i]);
				}
			}
			rs = pstmt.executeQuery();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 查询方法，接收select语句并返回rs结果集

	public ResultSet executeDataTable(String sql) {
		ResultSet rs = null;
		try {
			if(stmt==null || stmt.isClosed()){
				stmt = conn.createStatement();
			}
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 查询方法，接收select语句并返回rs结果集

	public String executeScalar(String sql) {
		ResultSet rs = null;
		String result = "";
		try {
			if(stmt==null || stmt.isClosed()){
				stmt = conn.createStatement();
			}
			rs = stmt.executeQuery(sql);
			if(rs!=null&&rs.next()){
				result = rs.getString(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 更新方法，可接收update/insert/delete等sql语句,
	public int executeNonQuery(String preparedSql, Object[] param) throws SQLException {
		PreparedStatement pstmt = null;
		int num = 0;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(preparedSql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i + 1, param[i]);
				}
			}
			num = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	public int executeNonQuery(String sql) throws SQLException {
		int num = 0;
		try {
			if(stmt==null || stmt.isClosed()){
				stmt = conn.createStatement();
			}
			num = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	// 事务执行
	public void executeTransaction(ArrayList sqlList) throws SQLException {
		conn.setAutoCommit(false);
		Statement stmt = conn.createStatement();
		for (int i = 0; i < sqlList.size(); i++) {
			stmt.addBatch(sqlList.get(i).toString());
		}
		stmt.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
		stmt.close();
	}
	
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
