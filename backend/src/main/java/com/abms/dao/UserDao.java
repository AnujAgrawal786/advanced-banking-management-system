package com.abms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.User;
import com.abms.util.DBConnection;

public class UserDao {
	 
	private User mapUser(ResultSet rs) throws SQLException {
	    User user = new User();

	    user.setUserId(rs.getLong("user_id"));
	    user.setUserName(rs.getString("user_name"));
	    user.setPassword(rs.getString("password"));
	    user.setEmail(rs.getString("email"));
	    user.setStatus(rs.getString("status"));

	    Timestamp timestamp = rs.getTimestamp("created_at");

	    if (timestamp != null) {
	        user.setCreatedAt(timestamp.toLocalDateTime());
	    }
	    return user;
	}
   public  boolean createUser(User user){
	   String sql=" INSERT INTO users\r\n"
	   		+ "    (user_id, user_name, password, email, status, created_at)\r\n"
	   		+ "    VALUES (?, ?, ?, ?, ?, ?)";
	   int res;
	try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
		
		 
			 pstm.setLong(1, user.getUserId());
			 pstm.setString(2, user.getUserName());
			 pstm.setString(3, user.getPassword());
			 pstm.setString(4, user.getEmail());
			 pstm.setString(5, user.getStatus());
			 pstm.setObject(6, user.getCreatedAt());
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
	   
   
   
	public List<User> findUserByUserName(String name){
		String sql="select * from users where user_name=?";
		List<User> users=new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
          pstm.setString(1, name);
          try (ResultSet rs=pstm.executeQuery()){
		    while(rs.next()) {
		    	 
                users.add(mapUser(rs));
		    }
		return users;
		
		}catch(SQLException e) {
			
			e.printStackTrace();
			return new ArrayList<>();
		}
          } catch (SQLException e) {

			e.printStackTrace();
			return new ArrayList<>();
		}

	}
	
	
	public List<User> findUserByEmail(String email){
		List<User> users=new ArrayList<>();
		String sql="select * from users where email=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			 pstm.setString(1, email);
			 
			 try(ResultSet rs=pstm.executeQuery()){
				 while(rs.next()) {
			
	                users.add(mapUser(rs));
			    }
			return users;
			 }catch(SQLException e){
				 e.printStackTrace();
			    return new ArrayList<>();
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}
	
	public boolean updatePassword(Long userId,String password) {
		String sql="update users set password=? where user_id=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			
			
				pstm.setString(1,password);
				pstm.setLong(2, userId);
				return pstm.executeUpdate()==1;
				
		
		   
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean updateStatus(String status,Long user_id) {
		String sql="update users set status=? where user_id=?";
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			
			
				pstm.setString(1, status);
				pstm.setLong(2, user_id);
				return pstm.executeUpdate()==1;
				
			}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(Long userId) {
		String sql="delete from users where user_Id=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
		 pstm.setLong(1, userId);
		 return pstm.executeUpdate()==1;	
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	
		
	}
	public boolean updateUser(User user) {

	    String sql = "UPDATE users SET "
	            + "user_name = ?, "
	            + "password = ?, "
	            + "email = ?, "
	            + "status = ? "
	            + "WHERE user_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstm = conn.prepareStatement(sql)) {

	        pstm.setString(1, user.getUserName());
	        pstm.setString(2, user.getPassword());
	        pstm.setString(3, user.getEmail());
	        pstm.setString(4, user.getStatus());
	        pstm.setLong(5, user.getUserId());

	        int result = pstm.executeUpdate();

	        return result == 1;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
}

