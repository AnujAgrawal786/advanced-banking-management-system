package com.abms.service;

import java.time.LocalDateTime;
import java.util.List;

import com.abms.dao.UserDao;
import com.abms.entity.User;
import com.abms.util.PasswordUtil;

public class UserService {
	private UserDao ud = new UserDao();

	private boolean validation(User user) {
		if (user.getUserId() <= 0) {
			return false;
		}
		if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
			return false;
		}
		if (user.getPassword() == null || user.getPassword().trim().isEmpty() || user.getPassword().length() < 6) {
			return false;
		}
		if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
			return false;
		}
		if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
			return false;
		}

		if (user.getMobile() == null || user.getMobile().trim().isEmpty()) {
			return false;
		}

		return true;
	}

	public boolean registerUser(User user) {
		if (validation(user)) {
			if (ud.findUserByUserName(user.getUserName()) != null) {
				return false;
			}

			if (ud.findUserByEmail(user.getEmail()) != null) {
				return false;
			}
			LocalDateTime now = LocalDateTime.now();

			user.setCreatedAt(now);
			user.setUpdatedAt(now);

			String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
			user.setPassword(hashedPassword);

			return ud.createUser(user);
		}
		return false;
	}

	public User login(String username, String password) {
		if (username == null || username.trim().isEmpty()) {
			return null;
		}
		if (password == null || password.trim().isEmpty()) {
			return null;
		}
		User user = ud.findUserByUserName(username);
		if (user == null) {
			return null;
		}
		if (PasswordUtil.verifyPassword(password, user.getPassword())) {
			return user;
		}
		return null;
	}

	public User getUserById(Long userId) {
		if (userId <= 0) {
			return null;
		}
		User user = ud.findById(userId);
		if (user != null) {
			return user;
		}

		return null;
	}

	public List<User> getAllUsers() {
		return ud.viewAllUser();
	}

	public boolean updateUser(User user) {

		if (!validation(user)) {
			return false;
		}

		User existing = ud.findUserByUserName(user.getUserName());

		if (existing != null && !existing.getUserId().equals(user.getUserId())) {
			return false;
		}

		User emailUser = ud.findUserByEmail(user.getEmail());

		if (emailUser != null && !emailUser.getUserId().equals(user.getUserId())) {
			return false;
		}

		user.setUpdatedAt(LocalDateTime.now());

		return ud.updateUser(user);
	}

	public boolean deleteUser(String username) {
		if (username == null || username.trim().isEmpty()) {
			return false;
		}
		return ud.deleteUser(username);
	}

	public boolean updatePassword(String username, String password) {
		if (username == null || username.trim().isEmpty()) {
			return false;
		}
		if (password == null || password.trim().isEmpty()) {
			return false;
		}
		String hashedPassword = PasswordUtil.hashPassword(password);
		return ud.updatePassword(username, hashedPassword);
	}

	public boolean updateStatus(String username, String status) {
		if (username == null || username.trim().isEmpty()) {
			return false;
		}
		if (status == null || status.trim().isEmpty()) {
			return false;
		}
		return ud.updateStatus(status, username);
	}
}
