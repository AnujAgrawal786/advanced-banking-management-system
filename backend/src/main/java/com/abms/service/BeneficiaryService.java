package com.abms.service;

import java.util.ArrayList;
import java.util.List;

import com.abms.dao.BeneficiaryDao;
import com.abms.entity.Beneficiary;

public class BeneficiaryService {
	private BeneficiaryDao beneficiaryDao = new BeneficiaryDao();

	private boolean validation(Beneficiary beneficiary) {
		if (beneficiary == null) {
			return false;
		}
		if (beneficiary.getBeneficiaryId() <= 0) {
			return false;
		}
		if (beneficiary.getCustomerId() <= 0) {
			return false;
		}
		if (beneficiary.getBeneficiaryName() == null || beneficiary.getBeneficiaryName().trim().isEmpty()) {
			return false;
		}
		if (beneficiary.getAccountNumber() == null || beneficiary.getAccountNumber().trim().isEmpty()) {
			return false;
		}
		if (beneficiary.getBankName() == null || beneficiary.getBankName().trim().isEmpty()) {
			return false;
		}
		if (beneficiary.getIfscCode() == null || beneficiary.getIfscCode().trim().isEmpty()) {
			return false;
		}
		if (beneficiary.getStatus() == null || beneficiary.getStatus().trim().isEmpty()) {
			return false;
		}
		if (beneficiary.getCreatedAt() == null) {
			return false;
		}

		return true;
	}

	public boolean createBeneficiary(Beneficiary beneficiary){
		if(beneficiary==null) {
			return false;
		}
		if(beneficiaryDao.findByAccountNumber(beneficiary.getAccountNumber())!=null) {
			return false;
		}
		if(!validation(beneficiary)) {
			
		}
	   	return beneficiaryDao.createBeneficiary(beneficiary);
	     }
	
	public Beneficiary getBeneficiaryById(Long beneficiaryId) {
           if(beneficiaryId<=0) {
        	   return null;
           }
           return beneficiaryDao.findById(beneficiaryId);
	}

	public List<Beneficiary> getBeneficiariesByCustomerId(Long customerId) {
         if(customerId<=0) {
        	 return new ArrayList<>();
         }
         return beneficiaryDao.findByCustomerId(customerId);
	}

	public List<Beneficiary> getAllBeneficiaries() {
       return beneficiaryDao.findAllBeneficiaries();
	}

	public boolean updateBeneficiary(Beneficiary beneficiary ) {
		if(beneficiary==null) {
			return false;
		}
       return beneficiaryDao.updateBeneficiary(beneficiary);
	}

	public boolean deleteBeneficiary(Long beneficiaryId) {
        if(beneficiaryId<=0) {
        	return false;
        }
        return beneficiaryDao.deleteBeneficiary(beneficiaryId);
	}
}
