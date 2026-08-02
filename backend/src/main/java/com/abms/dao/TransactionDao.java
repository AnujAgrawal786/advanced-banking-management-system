package com.abms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.Transaction;
import com.abms.util.DBConnection;

public class TransactionDao {
	public boolean createTransaction(Transaction transaction) {
		String sql="INSERT INTO transactions(TRANSACTION_TYPE,AMOUNT,STATUS,INITIATED_BY,ACCOUNT_ID,DESCRIPTION,BALANCE_AFTER) "
				+ "values(?,?,?,?,?,?,?)";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
          pstm.setString(1, transaction.getTransactionType());		
		  pstm.setBigDecimal(2, transaction.getAmount());
		  pstm.setString(3, transaction.getStatus());
		pstm.setLong(4, transaction.getInitiatedBy());
		pstm.setLong(5, transaction.getAccountId());
		pstm.setString(6,transaction.getDescription());
		pstm.setBigDecimal(7, transaction.getBalanceAfter());
		return pstm.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Transaction findById(Long transactionId) {
		String sql="SELECT * FROM transactions WHERE transaction_id=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setLong(1, transactionId);
            try (ResultSet rs=pstm.executeQuery()){
            	if(rs.next()) {
            		return mapTransaction(rs);
            	}
            	return null;
            }
		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Transaction> findByAccountId(Long accountId){
		String sql="SELECT * FROM transactions where account_id=?";
		try (Connection conn = DBConnection.getConnection() ;
				PreparedStatement pstm=conn.prepareStatement(sql)){
			pstm.setLong(1, accountId);
				List<Transaction> transactions=new ArrayList<>();
				try(ResultSet rs=pstm.executeQuery()){
					while(rs.next()) {
						transactions.add(mapTransaction(rs));
					}
					return transactions;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		
	}

	public Transaction findByTransactionReference(String transactionReference){
		String sql="SELECT * FROM transactions where transaction_reference=?";
		try (Connection conn = DBConnection.getConnection() ;
				PreparedStatement pstm=conn.prepareStatement(sql)){
			pstm.setString(1, transactionReference);
				try(ResultSet rs=pstm.executeQuery()){
					if(rs.next()) {
						return mapTransaction(rs);
					}
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}

	public List<Transaction> findAllTransactions(){
		String sql="SELECT * FROM transactions";
		try (Connection conn = DBConnection.getConnection() ;
			PreparedStatement pstm=conn.prepareStatement(sql);
				ResultSet rs=pstm.executeQuery()){
			List<Transaction> transactions=new ArrayList<>();
			
				while(rs.next()) {
					transactions.add(mapTransaction(rs));
				}
				return transactions;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private Transaction mapTransaction(ResultSet rs) throws SQLException {
		Transaction transaction=new Transaction();
		transaction.setTransactionId(rs.getLong("TRANSACTION_ID"));
		transaction.setTransactionType(rs.getString("TRANSACTION_TYPE"));
		transaction.setTransactionDate(rs.getTimestamp("TRANSACTION_DATE").toLocalDateTime());
		transaction.setAmount(rs.getBigDecimal("AMOUNT"));
		transaction.setBalanceAfter(rs.getBigDecimal("balance_after"));
		transaction.setDescription(rs.getString("DESCRIPTION"));
		transaction.setAccountId(rs.getLong("ACCOUNT_ID"));
		transaction.setTransactionReference(rs.getString("TRANSACTION_REFERENCE"));
		transaction.setInitiatedBy(rs.getLong("INITIATED_BY"));
		transaction.setStatus(rs.getString("STATUS"));
		return transaction;
	}
}
