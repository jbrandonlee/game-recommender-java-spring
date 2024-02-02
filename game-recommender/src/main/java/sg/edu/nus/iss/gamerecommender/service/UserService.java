package sg.edu.nus.iss.gamerecommender.service;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;

public interface UserService {
	public User createUser(User user);
	public User findUserByAccount(Account account);
}
