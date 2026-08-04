package com.abms.service;

import java.util.List;

import com.abms.dao.AccountDao;
import com.abms.dao.CustomerDao;
import com.abms.entity.Account;
import com.abms.entity.Customer;

public class CustomerService {
	private CustomerDao customerDao = new CustomerDao();
	private AccountDao accountDao = new AccountDao();

	private boolean validation(Customer customer) {
		if (customer == null) {
			return false;
		}
		if (customer.getCustomerId() <= 0) {
			return false;
		}
		if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
			return false;
		}
		if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
			return false;
		}
		if (customer.getDateOfBirth() == null) {
			return false;
		}
		if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
			return false;
		}
		if (customer.getCity() == null || customer.getCity().trim().isEmpty()) {
			return false;
		}
		if (customer.getPincode() == null || customer.getPincode().trim().isEmpty()) {
			return false;
		}
		if (customer.getState() == null || customer.getState().trim().isEmpty()) {
			return false;
		}
		if (customer.getUserId() <= 0) {
			return false;
		}
		return true;
	}

	public boolean createCustomer(Customer customer) {
		if (customer == null) {
			return false;
		}
		if (validation(customer)) {
			return customerDao.createCustomer(customer);
		}
		return false;
	}

	public Customer getCustomerById(Long customerId) {
		if (customerId <= 0) {
			return null;
		}
		return customerDao.findById(customerId);
	}

	public List<Customer> getAllCustomers() {
		return customerDao.findAllCustomers();
	}

	public boolean updateCustomer(Customer customer) {
		if (customer == null) {
			return false;
		}

		if (validation(customer)) {
			return customerDao.updateCustomer(customer);
		}
		return false;
	}

	public boolean deleteCustomer(Long customerId) {

		if (customerId <= 0) {
			return false;
		}
		List<Account> accounts = accountDao.findByCustomerId(customerId);

		if (!accounts.isEmpty()) {
			return false;
		}

		return customerDao.deleteCustomer(customerId);
	}
}
