package com.abms.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.User;
import com.abms.util.DBConnection;

public class UserDao {
	 
	private User mapUser(ResultSet rs) throws SQLException {

	    User user = new User();

	    user.setUserId(rs.getLong("user_id"));
	    user.setUserName(rs.getString("username"));
	    user.setPassword(rs.getString("password_hash"));
	    user.setEmail(rs.getString("email"));
	    user.setStatus(rs.getString("status"));
	    user.setMobile(rs.getString("MOBILE"));
	    Timestamp updated = rs.getTimestamp("updated_at");
	    if(updated !=null) {
	    	user.setUpdatedAt(updated.toLocalDateTime());
	    }
	    Timestamp timestamp = rs.getTimestamp("created_at");

	    if (timestamp != null) {
	        user.setCreatedAt(timestamp.toLocalDateTime());
	    }
	    return user;
	}
  
	
	public  boolean createUser(User user){

	   String sql=" INSERT INTO users\r\n"
	   		+ "    (user_id, username, password_hash, email, status, created_at,mobile,updated_at) "
	   		+ "    VALUES (?, ?, ?, ?, ?, ?,?,?)";
	   int res;
	try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		
		 
			 pstm.setLong(1, user.getUserId());
			 pstm.setString(2, user.getUserName());
			 pstm.setString(3, user.getPassword());
			 pstm.setString(4, user.getEmail());
			 pstm.setString(5, user.getStatus());
			 pstm.setTimestamp(6, java.sql.Timestamp.valueOf(user.getCreatedAt()));
			 pstm.setString(7, user.getMobile());
			 pstm.setTimestamp(8, java.sql.Timestamp.valueOf(user.getUpdatedAt()));
			 res=pstm.executeUpdate();
		 return res==1;

	} catch ( SQLException  e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
   
   
   }
   
   public User findById(Long userId) {

	   String sql="select * from users where user_id=?";
	   try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		   pstm.setLong(1, userId);
		   try (ResultSet rs = pstm.executeQuery()) {

	            if (rs.next()) {
	                return mapUser(rs);
	            }
	            return null;
	        } }catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	           }

		   
	   }
	   
   
   
	public User findUserByUserName(String name){

		String sql="select * from users where username=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
          pstm.setString(1, name);
          try (ResultSet rs=pstm.executeQuery()){
		    if(rs.next()) {
		    	 return mapUser(rs);
		    }
		return null;
		
		}}catch(SQLException e) {
			
			e.printStackTrace();
			return null;
		}
          }
	
	
	
	
	public User findUserByEmail(String email){

		String sql="select * from users where email=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			 pstm.setString(1, email);
			 
			 try(ResultSet rs=pstm.executeQuery()){
				 if(rs.next()) {
			
	                return mapUser(rs);
			    }
			return null;
			}catch(SQLException e){
				 e.printStackTrace();
                  return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
           return null;
		}

	}
	
	public boolean updatePassword(String username,String password) {
		String sql="update users set password_hash= ? ,updated_at=? where username=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			
			
				pstm.setString(1,password);
				pstm.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
				pstm.setString(3, username);
				return pstm.executeUpdate()==1;
				
		
		   
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean updateStatus(String status,String username) {
		String sql="update users set status=? ,updated_at=? where username=?";
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			
			
				pstm.setString(1, status);
				pstm.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
				pstm.setString(3, username);
				return pstm.executeUpdate()==1;
				
			}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(String username) {

		String sql="delete from users where username=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
		 pstm.setString(1, username);
		 return pstm.executeUpdate()==1;	
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	
		
	}
	public boolean updateUser(User user) {


	    String sql = "UPDATE users SET "
	            + "username = ?, "
	            + "password_hash = ?, "
	            + "email = ?, "
	            + "status = ?, "
	            + "mobile = ?, "
	            + "updated_at = ? "
	            + "WHERE user_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstm = conn.prepareStatement(sql)) {

	        pstm.setString(1, user.getUserName());
	        pstm.setString(2, user.getPassword());
	        pstm.setString(3, user.getEmail());
	        pstm.setString(4, user.getStatus());
	        pstm.setString(5, user.getMobile());
	        pstm.setTimestamp(6, Timestamp.valueOf(user.getUpdatedAt()));
	        pstm.setLong(7, user.getUserId());
	        int result = pstm.executeUpdate();

	        return result == 1;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public List<User> viewAllUser(){

		String sql="select * from users ";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs=pstm.executeQuery()) {
			 List<User> users=new ArrayList<>();
			 
				 while(rs.next()) {
			
	                users.add(mapUser(rs));
			    }
			return users;}
			 catch(SQLException e){
				 e.printStackTrace();
			    return new ArrayList<>();
			 }
		
	}
	
}

