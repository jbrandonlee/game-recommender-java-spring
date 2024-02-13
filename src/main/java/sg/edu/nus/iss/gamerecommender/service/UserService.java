package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;

public interface UserService {
	public User createUser(User user);
	public User updateUser(User user);
	public User findUserByAccount(Account account);
	public List<User> findAllUsers();
}
