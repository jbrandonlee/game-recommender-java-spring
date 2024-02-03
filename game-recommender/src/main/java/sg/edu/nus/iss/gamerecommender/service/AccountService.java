package sg.edu.nus.iss.gamerecommender.service;

import sg.edu.nus.iss.gamerecommender.model.Account;

public interface AccountService {
	public Account authenticate(String username, String password);
	public Account createAccount(Account account);
}
