package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	// Register
	@Override
	@Transactional(readOnly = false)
	public User createUser(User user) {
		return userRepo.saveAndFlush(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public User updateUser(User user) {
		return userRepo.saveAndFlush(user);
	}
	
	public User findUserByAccount(Account account) {
		return userRepo.findUserByAccountUsername(account.getUsername());
	}
	
	public List<User> searchGamers(String query) {
		return userRepo.searchGamers(query);
	}
	
	public List<User> searchDevelopers(String query) {
		return userRepo.searchDevelopers(query);
	}

	@Override
	public User findUserById(int id) {
		return userRepo.findUserById(id);
	}
}
