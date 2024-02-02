package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Account.Role;
import sg.edu.nus.iss.gamerecommender.repository.AccountRepository;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountRepository accountRepo;
	
	// Login
	@Override
	public Account authenticate(String username, String password) {
		return accountRepo.findAccountByUsernamePassword(username, password);
	}
	
	// Register
	@Override
	@Transactional(readOnly = false)
	public Account createAccount(Account account) {
		return accountRepo.saveAndFlush(account);
	}
	
	// Admin
	@Override
	public List<Account> findAccountsByRole(Role role) {
		return accountRepo.findAccountsByRole(role.name());
	}
	
	// Admin Dashboard
	@Override
	public int findAccountCountByRole(Role role) {
		return accountRepo.findAccountCountByRole(role.name());
	}
}
