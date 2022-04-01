package com.woniuxy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.woniuxy.util.Function;

public class BankDao {

	public boolean accountIsExist(String account) {
		Connection connect = Function.getConnection();
		String sql = "select * from atm where account = ?";
		PreparedStatement pst = null;
		ResultSet rs  = null;
		boolean result = true;
		try {
			pst = connect.prepareStatement(sql);
			pst.setString(1, account);
			rs = pst.executeQuery();
			result = rs.next();
			}catch(SQLException e) {
				e.printStackTrace();			
		}finally {
			Function.close(connect, pst, rs);
		}
		return result;	
	}
	
	public String register(String account, String password) {
		boolean accountIsExist = accountIsExist(account);
		if(accountIsExist) {
			return "账号已存在";
		}
			Connection conn = Function.getConnection();
			PreparedStatement pst = null;
			String sql = "insert into atm(account,password) values(?,?)";
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, account);
				pst.setString(2, password);
				pst.executeLargeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {	
				Function.close(conn, pst);
			}
			return "账户创建成功";
	}
	
	
	public String logIn (String account, String password) {
		Connection conn = Function.getConnection();
		String loginAccount = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select * from atm where account =? and password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, password);
			rs = pst.executeQuery();
			while(rs.next()) {
				loginAccount = rs.getNString("account");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			Function.close(conn, pst, rs);
		}
		
		
		return loginAccount;
		
	}
	
	public double moneyQuery (String account) {
		Connection conn = Function.getConnection();
		String sql = "select money from atm where account = ?";
		PreparedStatement  pst = null;
		ResultSet rs = null;
		double money = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			rs = pst.executeQuery();
			while(rs.next()) {
				money = rs.getDouble("money");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			Function.close(conn, pst, rs);
		}
		return money;
	}
	
	
	public void moneySave(String account,double money) {
		Connection conn = Function.getConnection();
		String sql =" update atm set money = money +? where account = ?;";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setDouble(1, money);
			pst.setString(2, account);
			pst.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			Function.close(conn, pst);
		}
	}
	
	public int moneyTakeoff(String account, double money) {
		Connection conn = Function.getConnection();
		String sql = "update atm set money = money -? where account=? and money>=?";
		PreparedStatement pst = null;
		int result = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setDouble(1, money);
			pst.setString(2, account);
			pst.setDouble(3, money);
			result = pst.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			Function.close(conn, pst);
		}
		return result;
	}
	
	public void moneyTrans (String acIn, String acOut, double money) {
		boolean cp = accountIsExist(acIn);
		if(!cp) {
			System.out.println("账户不存在！");
			return;
		}
		
		
		
		
		
		
		
		
		
		
		
		Connection conn = Function.getConnection();
		String Outsql = "update atm set money = money -? where account = ? and money>=?";
		String Insql = "update atm set money = money+ ? where account = ?";
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		try {
			conn.setAutoCommit(false);
			
			pst1 = conn.prepareStatement(Outsql);
			pst2 = conn.prepareStatement(Insql);
			pst1.setDouble(1, money);
			pst1.setString(2, acOut);
			pst1.setDouble(3, money);
			pst2.setDouble(1, money);
			pst2.setString(2, acIn);
			
			int oc =pst1.executeUpdate();
			if(oc == 0) {
				System.out.println("余额不足！");
				throw new Exception();
			}
			pst2.executeUpdate();
			
			conn.commit();
			
		}catch(Exception e) {
			try {
				conn.rollback();				
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Function.close(conn, pst1, pst2);
		}
	}
	
	
	
}
