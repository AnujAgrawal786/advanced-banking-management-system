package com.abms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.Beneficiary;
import com.abms.util.DBConnection;

public class BeneficiaryDao {
	  public boolean createBeneficiary(Beneficiary beneficiary) {
		  String sql="INSERT INTO beneficiaries(BENEFICIARY_ID,CUSTOMER_ID,BENEFICIARY_NAME,ACCOUNT_NUMBER,BANK_NAME,IFSC_CODE, STATUS) "
		  		+ "values(?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
        	pstm.setLong(1, beneficiary.getBeneficiaryId());
        	pstm.setLong(2, beneficiary.getCustomerId());
        	pstm.setString(3, beneficiary.getBeneficiaryName());
        	pstm.setString(4, beneficiary.getAccountNumber());
        	pstm.setString(5, beneficiary.getBankName());
        	pstm.setString(6, beneficiary.getIfscCode());
        	pstm.setString(7, beneficiary.getStatus());        	
        	return pstm.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	  }

	    public Beneficiary findById(Long beneficiaryId) {
        String sql="SELECT * FROM beneficiaries WHERE beneficiary_id=?";
	    	
	    	try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql)) {
               pstm.setLong(1, beneficiaryId);
              try( ResultSet rs=pstm.executeQuery()){
               if(rs.next()) {
                   return mapBeneficiary(rs);
	    	   } 
                 return null;
               }}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	    }

	    public List<Beneficiary> findByCustomerId(Long customerId){
	    	String sql="SELECT * FROM beneficiaries WHERE customer_id=?";
	    	try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql)) {
		        List<Beneficiary> beneficiaries=new ArrayList<>();
		        pstm.setLong(1, customerId);
		        try(ResultSet rs=pstm.executeQuery()){
		        	while(rs.next()) {
		        	beneficiaries.add(mapBeneficiary(rs));
		        }
		        	return beneficiaries;
		        }
	    	
	    	
	    	} catch (SQLException e) {
				e.printStackTrace();
               return new ArrayList<>();
	    	}
	    }
	    	
	    

	    public Beneficiary findByAccountNumber(String accountNumber) {
	    	String sql="SELECT * FROM beneficiaries WHERE account_number=?";
	    	
	    	try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql)) {
               pstm.setString(1, accountNumber);
              try( ResultSet rs=pstm.executeQuery()){
               if(rs.next()) {
               return mapBeneficiary(rs);
	    	} 
            return null;   
              }}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	    	
	    	
	    	
	    }

	    public List<Beneficiary> findAllBeneficiaries(){
	    	String sql="SELECT * FROM beneficiaries";
	    	try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql)) {
		        List<Beneficiary> beneficiaries=new ArrayList<>();
		        try(ResultSet rs=pstm.executeQuery()){
		        	while(rs.next()) {
		        	beneficiaries.add(mapBeneficiary(rs));
		        }
		        	return beneficiaries;
		        }
	    	
	    	
	    	} catch (SQLException e) {
				e.printStackTrace();
               return new ArrayList<>();
	    	}
	    }

	    public boolean updateBeneficiary(Beneficiary beneficiary) {
	    	String sql="UPDATE beneficiaries "
	    			+ "set beneficiary_name=?,"
	    			+ "account_number=?,"
	    			+ "bank_Name=?,"
	    			+ "ifsc_code=?,"
	    			+ "status=? "
	    			+ "WHERE beneficiary_id=?";
	       try (Connection conn = DBConnection.getConnection();
  				PreparedStatement pstm = conn.prepareStatement(sql)) {
	            pstm.setString(1, beneficiary.getBeneficiaryName());
	            pstm.setString(2, beneficiary.getAccountNumber());
	            pstm.setString(3, beneficiary.getBankName());
	            pstm.setString(4, beneficiary.getIfscCode());
	            pstm.setString(5, beneficiary.getStatus());
	            pstm.setLong(6, beneficiary.getBeneficiaryId());
	       
	          return pstm.executeUpdate()==1;
	       } catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	    	
	    }

	    public boolean deleteBeneficiary(Long beneficiaryId) {
	      String sql="DELETE from beneficiaries WHERE beneficiary_id=?";
	    	try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql)) {
             pstm.setLong(1, beneficiaryId);
	    	
	    	return pstm.executeUpdate()==1;
	    	
	    	} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	    }

	    private Beneficiary mapBeneficiary(ResultSet rs) throws SQLException{
	    	Beneficiary bfc=new Beneficiary();
	    	bfc.setBeneficiaryId(rs.getLong("beneficiary_id"));
	    	bfc.setCustomerId(rs.getLong("CUSTOMER_ID"));
	    	bfc.setBeneficiaryName(rs.getString("BENEFICIARY_NAME"));
	    	bfc.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
	    	bfc.setBankName(rs.getString("BANK_NAME"));
	    	bfc.setIfscCode(rs.getString("IFSC_CODE"));
	    	bfc.setStatus(rs.getString("STATUS"));
	    	bfc.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
	    	return bfc;
	    }
}
