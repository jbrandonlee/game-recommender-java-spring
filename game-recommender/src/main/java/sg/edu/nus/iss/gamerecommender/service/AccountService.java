package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Account.Role;

public interface AccountService {
	public Account authenticate(String username, String password);
	public Account createAccount(Account account);
	public List<Account> findAccountsByRole(Role role);
	public int findAccountCountByRole(Role role);
}
