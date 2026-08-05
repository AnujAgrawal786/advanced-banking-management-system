package com.abms.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.AuditLog;
import com.abms.util.DBConnection;

public class AuditLogDao {
	public boolean createAuditLog(AuditLog auditLog) {
        String sql="INSERT INTO audit_logs(log_id,user_id,action,description,ip_address,created_at,entity_name,entity_id) "
        		       + "values(?,?,?,?,?,?,?,?)";		
	    try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
     pstm.setLong(1, auditLog.getAuditLogId());
     pstm.setLong(2, auditLog.getUserId());
     pstm.setString(3, auditLog.getAction());
     pstm.setString(4, auditLog.getDescription());
     pstm.setString(5, auditLog.getIpAddress());
     pstm.setTimestamp(6, java.sql.Timestamp.valueOf(auditLog.getCreatedAt()));
     pstm.setString(7,auditLog.getEntityName());
     pstm.setLong(8, auditLog.getEntityId());
	    
	    return pstm.executeUpdate()==1;
	    
	    } catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	
	
	
	}

	public AuditLog findById(Long auditLogId) {
     String sql="SELECT * FROM audit_logs where log_id=?";
     try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
		pstm.setLong(1, auditLogId);
		try(ResultSet rs=pstm.executeQuery()){
		if(rs.next()) {
			return mapAudit(rs);
		    }	
		 return null;
		}
     } catch (SQLException e) {
			e.printStackTrace();
        return null;
     }
	}

	public List<AuditLog> findByUserId(Long userId){
		String sql="SELECT * FROM audit_logs WHERE user_id=? ";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)
				) {
         List<AuditLog> auditLogs=new ArrayList<>(); 
         pstm.setLong(1, userId);
         try(ResultSet rs=pstm.executeQuery()){
			while(rs.next()) {
         	 auditLogs.add(mapAudit(rs));
          }
        		return auditLogs;
		} }catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
		
	

	public List<AuditLog> findByAction(String action){
		String sql="SELECT * FROM audit_logs WHERE action=? ";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)
				) {
			pstm.setString(1, action);
         List<AuditLog> auditLogs=new ArrayList<>(); 
         try(ResultSet rs=pstm.executeQuery()){
			while(rs.next()) {
         	 auditLogs.add(mapAudit(rs));
          }
        		return auditLogs;
		} }catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	

	public List<AuditLog> findAll(){
		String sql="SELECT * FROM audit_logs";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs=pstm.executeQuery()) {
         List<AuditLog> auditLogs=new ArrayList<>(); 
			while(rs.next()) {
         	 auditLogs.add(mapAudit(rs));
          }
        		return auditLogs;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	public List<AuditLog> getAuditLogsByDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
      String sql="SELECT * FROM audit_logs "
      		+ "WHERE created_at BETWEEN ? AND ? "
      		+ "ORDER BY created_at DESC";
      try (Connection conn = DBConnection.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql)) {
    	  List<AuditLog> auditLogs=new ArrayList<>();
    	  pstm.setTimestamp(1, Timestamp.valueOf(fromDate));
    	  pstm.setTimestamp(2, Timestamp.valueOf(toDate));
    	  try(ResultSet rs=pstm.executeQuery()) {
    		  while(rs.next()) {
    			  auditLogs.add(mapAudit(rs));
    		  }
    		  return auditLogs;
    	  }
      } catch (SQLException e) {
		e.printStackTrace();
		return new ArrayList<>();
	}
	}
	public List<AuditLog> findByUserIdAndDateRange(Long userId,LocalDateTime fromDate, LocalDateTime toDate) {
		 String sql="SELECT * FROM audit_logs "
		      		+ "WHERE user_id=? "
		      		+ "And created_at BETWEEN ? AND ? "
		      		+ "ORDER BY created_at DESC";
		      try (Connection conn = DBConnection.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql)) {
		    	  List<AuditLog> auditLogs=new ArrayList<>();
		    	  pstm.setLong(1, userId);
		    	  pstm.setTimestamp(2, Timestamp.valueOf(fromDate));
		    	  pstm.setTimestamp(3, Timestamp.valueOf(toDate));
		    	  try(ResultSet rs=pstm.executeQuery()) {
		    		  while(rs.next()) {
		    			  auditLogs.add(mapAudit(rs));
		    		  }
		    		  return auditLogs;
		    	  }
		      } catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
	       
	       
		}
	private AuditLog mapAudit(ResultSet rs)throws SQLException {
		AuditLog auditLog=new AuditLog();
		auditLog.setAuditLogId(rs.getLong("log_id"));
		auditLog.setUserId(rs.getLong("user_id"));
		auditLog.setAction(rs.getString("action"));
		auditLog.setDescription(rs.getString("description"));
		auditLog.setIpAddress(rs.getString("ip_address"));
		auditLog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		auditLog.setEntityName(rs.getString("entity_name"));
		auditLog.setEntityId(rs.getLong("entity_id"));
		return auditLog;
	}
}
