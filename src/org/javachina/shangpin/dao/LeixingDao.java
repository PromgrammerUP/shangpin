package org.javachina.shangpin.dao;

import java.sql.Statement;
import java.util.ArrayList;

import org.javachina.shangpin.dto.LeixingDto;
import org.javachina.shangpin.dto.ShangpinDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeixingDao extends BaseDao{
	/*
	 * 添加类型
	 */
	public int insert(LeixingDto leixing){
		int result=0;
		//取得数据库连接
		Connection conn = this.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			//String preSql ="insert into leixing (id,name)values
			//(nvl((select max(id)from leixing)+1 ,1001),'%s')";
			//String静态方法：参数1：源字符串，参数2.。参数n 用新字符串补齐1中的%s
			//String sql = String.format(preSql,leixing.getName());
			result = stmt.executeUpdate("insert into leixing(id,name) values"
					+ "(seq_leixing.nextval,'"+leixing.getName()+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeResource(conn, stmt, null);
		}
		return result;
	}
	/*
	 * 删除类型
	 */
	public int delete(ShangpinDto dto){
		int leixingId = dto.getId();
		int result = 0;
		Connection conn = this.getConnection();
		Statement stat = null;
		try {
			stat = conn.createStatement();
			
			stat.executeUpdate("delete leixing where id='"+leixingId+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeResource(conn, stat, null);
		}
		
		return result;
	}
	/*
	 * 更新类型
	 */
	public int update(LeixingDto dto){
		int result = 0;
		int leixingId = dto.getId();
		String leixingName = dto.getName();
		Connection conn = this.getConnection();
		Statement stat = null;
		try {
			stat = conn.createStatement();
			result = stat.executeUpdate("update leixing set name='"+leixingName+"' where id ="+leixingId+"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeResource(conn, stat, null);
		}
		
				
		return result;
	}
	/*
	 * 根据Id查找指定类型
	 */
	public LeixingDto findByid(int leixingId){
		LeixingDto result = null;
		Connection conn = this.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select name from leixing where id="+leixingId+" ");
			while(rs.next()){
				//String name = rs.getString("name");//按照字段名字取得name
				String name = rs.getString(1);//按照在sql语句中的位置取得
				result = new LeixingDto();
				result.setName(name);
				result.setId(leixingId);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeResource(conn, stat, rs);
		}
		
		return result;
	}
	/*
	 * 查找所有类型
	 */
	public ArrayList<LeixingDto> findAll(){
		ArrayList<LeixingDto> result = new ArrayList<LeixingDto>();
		Connection conn = this.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select id,name from leixing order by id ");
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				LeixingDto lx = new LeixingDto(id,name);
				result.add(lx);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeResource(conn, stat, rs);
		}
		
		return result;
	}
	/*
	 * statement 批处理操作，加入多条记录
	 * statement创建时候不绑定sql，她的批处理可以一起执行多条sql语句，sql语句可以是插入，删除，修改；
	 *事务：一组不可分割的操作（如果其中的某个操作在执行中出现了问题，则本次操作没有意义或者影响数据的完整性）
	 *事务的特征：ACID
	 *  1. 原子性（Atomicity）
		原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。	 
		2. 一致性（Consistency）
		事务必须使数据库从一个一致性状态变换到另外一个一致性状态。
		3. 隔离性（Isolation）
		事务的隔离性是指一个事务的执行不能被其他事务干扰，即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。
		4. 持久性（Durability）
		持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来的其他操作和数据库故障不应该对其有任何影响。
	 *处理过程为：
	 *1设置connection.setAutoCommit(false);
	 *2如果正常执行完毕，则把操作结果提交connection.commit();
	 *3如果执行过程中出现异常（sql错误，数据错误等）,则进行回滚，则在捕获异常处理中执行connection.rollback();
	 */
	public int insert(){
		int result = 0;
		Connection conn = this.getConnection();
		Statement stat = null;
		try {
			stat = conn.createStatement();
			conn.setAutoCommit(false);//事务处理，如果批处理中某个操作出现问题则本次操作全部回滚，rollback
			stat.addBatch("insert into leixing (id,name) values(seq_leixing.nextval,'中文')");
			stat.addBatch("insert into leixing (id,name) values(seq_leixing.nextval,'英文')");
			stat.addBatch("insert into leixing (id,name) values(seq_leixing.nextval,'法文')");
			int [] i = stat.executeBatch();//statement的批处理可以一起执行多条sql语句，sql语句可以是插入，删除，修改；
			conn.commit();
			result = i.length;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			this.closeResource(conn, stat, null);
		}
		
		return result;
	}
	public static void main(String[] args) {
		//new LeixingDao().insert( new LeixingDto(0,"java"));
		//new LeixingDao().insert( new LeixingDto(0,"php"));
//		new LeixingDao().insert( new LeixingDto(0,"servlet"));
//		new LeixingDao().insert( new LeixingDto(0,"jsp"));
		LeixingDao dao = new LeixingDao();
//		LeixingDto dto = dao.findByid(1004);
//		System.out.println(dto.getName());
//		ArrayList<LeixingDto> list = dao.findAll();
//		for (LeixingDto dto : list) {
//			System.out.println(dto.getName());
//		}
		dao.insert();
	}
}
