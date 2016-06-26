package org.javachina.shangpin.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.javachina.shangpin.dto.ShangpinDto;

public class ShangpinDao extends BaseDao{
	/*
	 * 添加商品
	 */
	public int insert(ShangpinDto sp){
		String name = sp.getName();
		String address = sp.getAddress();
		double price = sp.getPrice();
		Date inputDate = sp.getInputDate();
		int leixingId = sp.getLeixingId();
		String description = sp.getDescription();
		
		int result=0;
		//得到数据库的连接
		Connection conn =null;
		Statement stat = null;
		PreparedStatement psmt = null;
		try {
			conn = this.getConnection();
			//stat = conn.createStatement();
			//方案一：
//			String sql ="insert into shangpin (id,name,price,"
//					+ "address,inputdate,leixingid,description) values"
//					+ "(seq_shangpin.nextval,'"+name+"',"+price+",'"+address+"',"
//					+ "to_date('"+inputDate.toString()+"','yyyy-mm-dd'),"+leixingId+","
//							+ "'"+description+"')";
			//方案二
//			String sql ="insert into shangpin(id,name,price,address,inputdate,leixingid,description)"
//					+ "values(seq_shangpin.nextval,'%s',%s,'%s',to_date('%s','yyyy-mm-dd'),%s,'%s')";
//			String sqlR =String.format(sql, name,price,address,inputDate.toString(),leixingId,description);
//			result = stat.executeUpdate(sqlR);
			//方案三
			String sql = "insert into shangpin(id,name,price,address,inputdate,leixingid,description)"
					+ "values(seq_shangpin.nextval,?,?,?,?,?,?)";
			
		    psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			psmt.setDouble(2, price);
			psmt.setString(3, address);
			psmt.setDate(4, inputDate);
			psmt.setInt(5, leixingId);
			psmt.setString(6, description);
			int i = psmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeResource(conn, psmt, null);
		}
		return result;
	}
	/*
	 * 删除商品
	 */
	public int delete(ShangpinDto sp){
		int id = sp.getId();
		//得到连接
		Connection conn = null;
		PreparedStatement psmt = null;
		int result = 0;
		try {
			conn = this.getConnection();
			String sql = "delete shangpin where id =?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, id);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeResource(conn, psmt, null);
		}
		return result;
	}
	/*
	 * 插入多条商品
	 */
	public int mutiInset(ArrayList<ShangpinDto> list){
		int result = 0;
		//得到连接
		Connection conn = this.getConnection();
		PreparedStatement psmt = null;
		//添加商品语句
		String sql = "insert into shangpin(id,name,price,address,inputdate,leixingid,description) "
				+ "values(seq_shangpin.nextval,?,?,?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			for (ShangpinDto sp : list) {
				String name = sp.getName();
				String address = sp.getAddress();
				double price = sp.getPrice();
				Date inputDate = sp.getInputDate();
				int leixingId = sp.getLeixingId();
				String description = sp.getDescription();
				psmt.setString(1, name);
				psmt.setDouble(2, price);
				psmt.setString(3, address);
				psmt.setDate(4, inputDate);
				psmt.setInt(5, leixingId);
				psmt.setString(6, description);
				//加入批处理行列
				//preparedstatement创建的时候已经绑定了sql，所以批处理时只能多次执行一种操作（插入，删除，更新中的一种）
				psmt.addBatch();
			}
			int[] i = psmt.executeBatch();
			result = i.length;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/*
	 * 修改商品
	 */
	
	public int update(ShangpinDto sp){
		
		return 0;
	}
	/*
	 * 查询所有商品
	 */
	public ArrayList<ShangpinDto> findAll(){
		
		return null;
	}
	public static void main(String[] args) {
		ShangpinDao dao = new ShangpinDao();
//		ArrayList<Connection> list = new ArrayList<>();
//		for(int i=0;i<10;i++){
//		Connection conn = dao.getConnection();
//		list.add(conn);
//		}
//		System.out.println("连接创建完毕");
//		Scanner sc = new Scanner(System.in);
//		while (sc.hasNext()) {
//			
//		}
//	   ShangpinDto dto = new ShangpinDto();
//	   dto.setName("高数");
//	   dto.setAddress("黑龙江大学");
//	   dto.setLeixingId(15);
//	   dto.setDescription("一本好书");
//	   dto.setPrice(12.5);
//	   dto.setInputDate(Date.valueOf("2001-10-1"));
//	   dao.insert(dto);
//		ShangpinDto sp = new ShangpinDto();
//		sp.setId(2002);
//		int result = dao.delete(sp);
//		System.out.println(result);
		ArrayList<ShangpinDto> list = new ArrayList<ShangpinDto>();
		ShangpinDto sp = new ShangpinDto();
		sp.setName("小数");
		sp.setPrice(2.5);
		sp.setAddress("新华书店");
		sp.setDescription("好书");
		sp.setInputDate(Date.valueOf("2002-3-2"));
		sp.setLeixingId(2001);
		list.add(sp);
		ShangpinDto sp1 = new ShangpinDto();
		sp1.setName("初数");
		sp1.setPrice(2.5);
		sp1.setAddress("新华书店");
		sp1.setDescription("好书");
		sp1.setInputDate(Date.valueOf("2002-3-2"));
		sp1.setLeixingId(2001);
		list.add(sp1);
		ShangpinDto sp2 = new ShangpinDto();
		sp2.setName("高中数学");
		sp2.setPrice(2.5);
		sp2.setAddress("新华书店");
		sp2.setDescription("好书");
		sp2.setInputDate(Date.valueOf("2002-3-2"));
		sp2.setLeixingId(2001);
		list.add(sp2);
		int i = dao.mutiInset(list);
		System.out.println(i);
	}
}
