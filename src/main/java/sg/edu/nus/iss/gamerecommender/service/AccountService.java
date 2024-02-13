package sg.edu.nus.iss.gamerecommender.service;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;

import java.util.List;

public interface AccountService {
	public Account authenticate(String username, String password);
	public Account createAccount(Account account);
	public List<Account> findByRoles(User.Role role);

	public List<Account> searchGamerByName(String query);

	public List<Account> searchDeveloperByName(String query);

}
