package sg.edu.nus.iss.gamerecommender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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

	@Override
	public List<Account> findByRoles(User.Role role) {
		List<Account> accounts=new ArrayList<>();
		List<Account> all = accountRepo.findAll();
		for(Account cc:all){
			User.Role ccRole = cc.getUser().getRole();
			if(ccRole==role){
				accounts.add(cc);
			}
		}
		return accounts;
	}

	@Override
	public List<Account> searchGamerByName(String query) {
		return accountRepo.SearchGamerByName(query,User.Role.GAMER);
	}

	@Override
	public List<Account> searchDeveloperByName(String query) {
		return accountRepo.SearchDeveloperByName(query,User.Role.DEVELOPER);
	}

}
