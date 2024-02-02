package sg.edu.nus.iss.gamerecommender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;
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
	
	public User findUserByAccount(Account account) {
		return userRepo.findUserByAccountUsername(account.getUsername());
	}

}
