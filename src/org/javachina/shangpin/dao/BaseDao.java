package org.javachina.shangpin.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/*
 * 1.提供连接
 * 2.关闭连接
 */
public class BaseDao {
	private ResourceBundle bundle = ResourceBundle
			.getBundle("org/javachina/shangpin/dao/config/conn");
	/*
	 * 提供连接
	 */
	protected Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName(bundle.getString("drivername"));
			conn = DriverManager.getConnection(bundle.getString("url")
					,bundle.getString("username")
					,bundle.getString("password"));
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return conn;
	}
	
	protected void closeResource(Connection conn,Statement stat,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			if(stat!=null){
				stat.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
