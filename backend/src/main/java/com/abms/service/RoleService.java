package com.abms.service;

import java.util.ArrayList;
import java.util.List;

import com.abms.dao.RoleDao;
import com.abms.entity.Role;

public class RoleService {
	private RoleDao roleDao = new RoleDao();

	private boolean validation(Role role) {
		if(role==null) {
			return false;
		}
		if (role.getRoleId() <= 0) {
			return false;
		}
		if (role.getRoleName() == null || role.getRoleName().trim().isEmpty()) {
			return false;
		}
		if (role.getDescription() == null || role.getDescription().trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean createRole(Role role) {
		if (!validation(role)) {
			return false;

		}
		if (!roleDao.findByRoleName(role.getRoleName()).isEmpty()) {
			return false;
		}
		return roleDao.createRole(role);
	}

	public Role getRoleById(Long roleId) {
		if (roleId <= 0) {
			return null;
		}
		return roleDao.findById(roleId);

	}

	public List<Role> getRoleByName(String roleName) {
		if (roleName == null || roleName.trim().isEmpty()) {
			return new ArrayList<>();
		}

		return roleDao.findByRoleName(roleName);

	}

	public List<Role> getAllRoles() {
		return roleDao.findAllRoles();
	}

	public boolean deleteRole(Long roleId) {
		if (roleId <= 0) {
			return false;
		}
		return roleDao.deleteRole(roleId);
	}
}
