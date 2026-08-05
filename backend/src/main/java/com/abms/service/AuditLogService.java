package com.abms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.abms.dao.AuditLogDao;
import com.abms.entity.AuditLog;

public class AuditLogService {
   private AuditLogDao auditLogDao=new AuditLogDao();
	private boolean validation(AuditLog auditLog) {
		if (auditLog == null) {
			return false;
		}
		if (auditLog.getAuditLogId() <= 0) {
			return false;
		}
		if (auditLog.getEntityId() <= 0) {
			return false;
		}
		if (auditLog.getEntityName() == null || auditLog.getEntityName().trim().isEmpty()) {
			return false;
		}
		if (auditLog.getUserId() <= 0) {
			return false;
		}
		if (auditLog.getIpAddress() == null || auditLog.getIpAddress().trim().isEmpty()) {
			return false;
		}
		if (auditLog.getDescription() == null || auditLog.getDescription().trim().isEmpty()) {
			return false;
		}
		if(auditLog.getAction()==null||auditLog.getAction().trim().isEmpty()) {
			return false;
		}
		if (auditLog.getCreatedAt()==null) {
			return false;
		}
		return true;
	}

	public boolean createAuditLog(AuditLog auditLog) {
        
        if(auditLogDao.findById(auditLog.getAuditLogId())!=null) {
        	return false;
        }
        auditLog.setCreatedAt(LocalDateTime.now());
        if(!validation(auditLog)) {
        	return false;
        }
        return auditLogDao.createAuditLog(auditLog);
        
	}

	public AuditLog getAuditLogById(Long auditLogId) {
        if(auditLogId<=0) {
        	return null;
        }
        return auditLogDao.findById(auditLogId);
	}

	public List<AuditLog> getAuditLogsByUserId(Long userId) {
       if(userId<=0) {
    	   return new ArrayList<>();
       }
       return auditLogDao.findByUserId(userId);
	}

	public List<AuditLog> getAuditLogsByAction(String action) {
         if(action==null||action.trim().isEmpty()) {
        	 return new ArrayList<>();
         }
         return auditLogDao.findByAction(action);
         
	}

	public List<AuditLog> getAuditLogsByDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
       if(fromDate==null) {
    	   return new ArrayList<>();
       }
       if(toDate==null) {
    	   return new ArrayList<>();
       }
       
       return auditLogDao.getAuditLogsByDateRange(fromDate, toDate);
	}
	
	public List<AuditLog> getAuditLogsByDateRange(Long userId,LocalDateTime fromDate, LocalDateTime toDate) {
	       if(fromDate==null) {
	    	   return new ArrayList<>();
	       }
	       if(toDate==null) {
	    	   return new ArrayList<>();
	       }
	       if(userId<=0) {
	    	   return new ArrayList<>();
	       }
	       
	       return auditLogDao.findByUserIdAndDateRange(userId,fromDate, toDate);
		}
	public List<AuditLog> getAllAuditLogs() {
      return auditLogDao.findAll();
	}

	

}
