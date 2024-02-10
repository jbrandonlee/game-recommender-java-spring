package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;

public interface UserService {
	public User createUser(User user);
	public User updateUser(User user);
	public User findUserById(int userId);
	public User findUserByAccount(Account account);
	public List<User> searchGamers(String query);
	public List<User> searchDevelopers(String query);
	public User addFriend(int userId, int friendId);
	public User removeFriend(int userId, int friendId);
	public User followDev(int userId, int devId);
	public User unfollowDev(int userId, int devId);
	public User followGame(int userId, int gameId);
	public User unfollowGame(int userId, int gameId);
}
