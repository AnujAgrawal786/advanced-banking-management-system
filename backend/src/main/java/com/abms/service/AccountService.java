package com.abms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.abms.dao.AccountDao;
import com.abms.entity.Account;

public class AccountService {
	private AccountDao accountDao = new AccountDao();

	private boolean validation(Account account) {
		if (account == null) {
			return false;
		}
		if (account.getCustomerId() <= 0) {
			return false;
		}
		if (account.getAccountId() <= 0) {
			return false;
		}
		if (account.getAccountNumber() == null || account.getAccountNumber().trim().isEmpty()) {
			return false;
		}
		if (account.getAccountType() == null || account.getAccountType().trim().isEmpty()) {
			return false;
		}
		if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
			return false;
		}
		if (account.getOpenedAt() == null) {
			return false;
		}
		return true;
	}

	public boolean createAccount(Account account) {
		account.setOpenedAt(LocalDateTime.now());
		if (!validation(account)) {
			return false;
		}
		if (accountDao.findByAccountNumber(account.getAccountNumber()) != null) {
			return false;
		}
		return accountDao.createAccount(account);
	}

	public Account getAccountById(Long accountId) {
		if (accountId <= 0) {
			return null;
		}
		return accountDao.findById(accountId);
	}

	public Account getAccountByNumber(String accountNumber) {
		if (accountNumber == null || accountNumber.trim().isEmpty()) {
			return null;
		}
		return accountDao.findByAccountNumber(accountNumber);
	}

	public List<Account> getAccountsByCustomerId(Long customerId) {
		if (customerId <= 0) {
			return new ArrayList<>();
		}
		return accountDao.findByCustomerId(customerId);
	}

	public List<Account> getAllAccounts() {
		return accountDao.findAllAccounts();
	}

	public boolean updateAccount(Account account) {
		if (!validation(account)) {
			return false;
		}
		return accountDao.updateAccount(account);
	}

	public boolean deleteAccount(Long accountId) {
		if (accountId <= 0) {
			return false;
		}
		return accountDao.deleteAccount(accountId);
	}
}
