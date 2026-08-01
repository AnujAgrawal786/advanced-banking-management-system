package com.abms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.Customer;
import com.abms.util.DBConnection;

public class CustomerDao {
	private Customer mapCustomer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setCustomerId(rs.getLong("customer_id"));
		customer.setUserId(rs.getLong("user_id"));
		customer.setFirstName(rs.getString("first_name"));
		customer.setLastName(rs.getString("last_name"));
		customer.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
		customer.setAddress(rs.getString("address"));
		customer.setCity(rs.getString("city"));
		customer.setState(rs.getString("state"));
		customer.setPincode(rs.getString("pincode"));
		customer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		return customer;
	}

	public boolean createCustomer(Customer customer) {
		String sql = "INSERT INTO customers(customer_id,user_id,first_name,last_name,date_of_birth,address,city,state,pincode) VALUES(?,?,?,?,?,?,?,?,?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setLong(1, customer.getCustomerId());
			pstm.setLong(2, customer.getUserId());
			pstm.setString(3, customer.getFirstName());
			pstm.setString(4, customer.getLastName());
			pstm.setDate(5, java.sql.Date.valueOf(customer.getDateOfBirth()));
			pstm.setString(6, customer.getAddress());
			pstm.setString(7, customer.getCity());
			pstm.setString(8, customer.getState());
			pstm.setString(9, customer.getPincode());
			return pstm.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Customer findById(Long customerId) {
		String sql = "SELECT * FROM customers WHERE customer_id=? ";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setLong(1, customerId);

			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					return mapCustomer(rs);
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Customer  findByUserId(Long userId) {
	 String sql="SELECT * FROM customers WHERE user_id=?";
	  try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		 pstm.setLong(1, userId);
        try(ResultSet rs=pstm.executeQuery()){
             if(rs.next()) {
        		return mapCustomer(rs);
        	}
        	return null;
        }		 
	} catch (SQLException e) {
		e.printStackTrace();
        return null;
	}
  }

	

	

	public List<Customer> findAllCustomers() {
		String sql="SELECT * FROM customers";
      try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery()) {
    	  
    	  List<Customer> customers=new ArrayList<>();
    	  while(rs.next()) {
    		customers.add(mapCustomer(rs));
    		  
    		  
    	  }
    	  return customers;
	} catch (SQLException e) {
		e.printStackTrace();
      return new ArrayList<>();
	}
	}

	public boolean updateCustomer(Customer customer) {
      String sql="UPDATE customers "
      		+ "set first_name=?,"
      		+ "last_name=?,"
      		+ "date_of_birth=?,"
      		+ "address=?,"
      		+ "city=?,"
      		+ "state=?,"
      		+ "pincode=?"
      		
      		+ "where customer_id=?";
      
      
  
    try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
         	
		pstm.setString(1, customer.getFirstName());
		pstm.setString(2, customer.getLastName());
		pstm.setDate(3, java.sql.Date.valueOf(customer.getDateOfBirth()));
		pstm.setString(4, customer.getAddress());
		pstm.setString(5, customer.getCity());
		pstm.setString(6, customer.getState());
		pstm.setString(7, customer.getPincode());
		pstm.setLong(8, customer.getCustomerId());
		return pstm.executeUpdate() == 1;
    	
	} catch (SQLException e) {
		e.printStackTrace();
		return false;
	}
    
      		
	}

	public boolean deleteCustomer(Long customerId) {
		String sql="delete from customers where customer_id=?";
        try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
              pstm.setLong(1, customerId);        	
          return	pstm.executeUpdate()==1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
