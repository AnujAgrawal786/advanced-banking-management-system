package com.abms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.abms.entity.Role;
import com.abms.util.DBConnection;

public class RoleDao {

	private Role mapRole(ResultSet rs) throws SQLException {

		Role role = new Role();
		role.setRoleId(rs.getLong("role_id"));
		role.setRoleName(rs.getString("role_name"));
		role.setDescription(rs.getString("role_description"));
		return role;
	}

	public boolean createRole(Role role) {
		String sql = "insert into roles(role_id,role_name,role_description) values(?,?,?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			pstm.setLong(1, role.getRoleId());
			pstm.setString(2, role.getRoleName());
			pstm.setString(3, role.getDescription());
			return pstm.executeUpdate() == 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public Role findById(Long roleId) {
		String sql = "SELECT * FROM roles WHERE role_id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setLong(1, roleId);
			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					return mapRole(rs);
				}
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Role> findByRoleName(String roleName) {
		String sql = "select * from roles where role_name=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			List<Role> roles = new ArrayList<>();
			pstm.setString(1, roleName);
			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					roles.add(mapRole(rs));
				}
				return roles;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<Role> findAllRoles() {
		String sql = "select * from roles";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {
			List<Role> roles = new ArrayList<>();
			while (rs.next()) {
				roles.add(mapRole(rs));
			}
			return roles;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public boolean deleteRole(Long roleId) {
		String sql = "delete from roles where role_id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setLong(1, roleId);
			return pstm.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
