package com.abms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.Account;
import com.abms.util.DBConnection;

public class AccountDao {
	
	private Account mapAccount(ResultSet rs) throws SQLException{
		Account account=new Account();
		account.setAccountId(rs.getLong("account_id"));
		account.setCustomerId(rs.getLong("customer_id"));
		account.setAccountNumber(rs.getString("account_number"));
		account.setAccountType(rs.getString("account_type"));
		account.setBalance(rs.getBigDecimal("balance"));
		account.setStatus(rs.getString("status"));
		account.setOpenedAt(rs.getTimestamp("opened_at").toLocalDateTime());
      return account;		
	}
	
	
	
	
	
	
   public boolean createAccount(Account account) {
	   String sql="INSERT INTO accounts(account_id,customer_id,account_number,account_type,balance,status) "
	   		+ "values(?,?,?,?,?,?)";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		   pstm.setLong(1, account.getAccountId());
		   pstm.setLong(2, account.getCustomerId());
		   pstm.setString(3, account.getAccountNumber());
		   pstm.setString(4, account.getAccountType());
		   pstm.setBigDecimal(5, account.getBalance());
		   pstm.setString(6, account.getStatus());
		   
		   return pstm.executeUpdate()==1;
	} catch (SQLException e) {
		e.printStackTrace();
		return false;
	}
	  
   }
   
   public Account findById(Long accountId) {
      String sql="SELECT * FROM accounts WHERE account_id=?";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
	        pstm.setLong(1, accountId);
	        try(ResultSet rs=pstm.executeQuery()){
	        
	        	if(rs.next()) {
	        	return mapAccount(rs);
	        	}
	        	return null;
	        }
	   } catch (SQLException e) {
		   e.printStackTrace();
		   return null;
	   }
	        }
   
   
   
   public Account findByAccountNumber(String accountNumber) {
	   String sql="SELECT * FROM accounts WHERE account_number=?";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		   pstm.setString(1, accountNumber);
	        try(ResultSet rs=pstm.executeQuery()){
	        
	        	if(rs.next()) {
	        	return mapAccount(rs);
	        	}
	        	return null;
	        }
		   
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
   }
   
   public List<Account> findByCustomerId(Long customerId) {
	   String sql="SELECT * FROM accounts WHERE customer_id=?";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		   pstm.setLong(1, customerId);
	        try(ResultSet rs=pstm.executeQuery()){
	        List<Account> accounts=new ArrayList<>();
	        	while(rs.next()) {
	        		accounts.add(mapAccount(rs));
	        	}
	        	return accounts;
	        }
		   
	} catch (SQLException e) {
		e.printStackTrace();
		return new ArrayList<>();
	   }
	}
   public List<Account> findAllAccounts(){
       String sql="SELECT * FROM accounts";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
        List<Account> accounts=new ArrayList<>();	
	      try(ResultSet rs=pstm.executeQuery()){
	     
	         while(rs.next()) {
	        	 accounts.add(mapAccount(rs));
	         }
	      
	      return accounts;
	      
	      }
	   } catch (SQLException e) {
		e.printStackTrace();
		return new ArrayList<>();
	}
   }
   public boolean updateAccount(Account account) {
     String sql="UPDATE accounts "
     		+ "SET account_type=?,"
     		+ "status=? "
     		+ "where account_id=?";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		   pstm.setString(1,account.getAccountType());
		   pstm.setString(2, account.getStatus());
		   pstm.setLong(3, account.getAccountId());
		   
		   return pstm.executeUpdate()==1;
		   
		   
		   
	} catch (SQLException e) {
		e.printStackTrace();
	      return false;
	}
   }
   public boolean deleteAccount(Long accountId) {
      String sql ="DELETE FROM accounts WHERE account_id=?";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
         pstm.setLong(1, accountId);	
	   return pstm.executeUpdate()==1;
	   } catch (SQLException e) {
		e.printStackTrace();
          return false;
	   }
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
