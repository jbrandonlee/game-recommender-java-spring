package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Profile;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;
import sg.edu.nus.iss.gamerecommender.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	GameRepository gameRepo;
	
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
	
	@Override
	public User findUserById(int userId) {
		return userRepo.findUserById(userId);
	}
	
	@Override
	public User findUserByAccount(Account account) {
		return userRepo.findUserByAccountUsername(account.getUsername());
	}
	
	@Override
	public List<User> searchGamers(String query) {
		return userRepo.searchGamers(query);
	}
	
	@Override
	public List<User> searchDevelopers(String query) {
		return userRepo.searchDevelopers(query);
	}
	
	@Override
	@Transactional(readOnly = false)
	public User addFriend(int userId, int friendId) {
		User user = userRepo.findUserById(userId);
		User friend = userRepo.findUserById(friendId);
		
		if (user.getRole() != Role.GAMER) { return null; }
		if (userId == friendId) { return null; }
		
		Profile profile = user.getProfile();
		if (profile instanceof ProfileGamer) {
			List<User> friendList = ((ProfileGamer) profile).getFriends();
			if (!friendList.contains(friend)) {
				((ProfileGamer) profile).addFriend(friend);
			}			
		}
		
		return userRepo.saveAndFlush(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public User removeFriend(int userId, int friendId) {
		User user = userRepo.findUserById(userId);
		User friend = userRepo.findUserById(friendId);
		
		if (user.getRole() != Role.GAMER) { return null; }
		
		Profile profile = user.getProfile();
		if (profile instanceof ProfileGamer) {
			((ProfileGamer) profile).removeFriend(friend);
		}
		
		return userRepo.saveAndFlush(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public User followDev(int userId, int devId) {
		User user = userRepo.findUserById(userId);
		User dev = userRepo.findUserById(devId);
		
		if (user.getRole() != Role.GAMER) { return null; }
		
		Profile profile = user.getProfile();
		if (profile instanceof ProfileGamer) {
			List<User> followedDevList = ((ProfileGamer) profile).getFollowedDevelopers();
			if (!followedDevList.contains(dev)) {
				((ProfileGamer) profile).addFollowedDev(dev);
			}			
		}
		
		return userRepo.saveAndFlush(user);
	}

	@Override
	@Transactional(readOnly = false)
	public User unfollowDev(int userId, int devId) {
		User user = userRepo.findUserById(userId);
		User dev = userRepo.findUserById(devId);
		
		if (user.getRole() != Role.GAMER) { return null; }
		
		Profile profile = user.getProfile();
		if (profile instanceof ProfileGamer) {
			((ProfileGamer) profile).removeFollowedDev(dev);
		}
		
		return userRepo.saveAndFlush(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public User followGame(int userId, int gameId) {
		User user = userRepo.findUserById(userId);
		Game game = gameRepo.findGameById(gameId);
		
		if (user.getRole() != Role.GAMER) { return null; }
		
		Profile profile = user.getProfile();
		if (profile instanceof ProfileGamer) {
			List<Game> followedGameList = ((ProfileGamer) profile).getFollowedGames();
			if (!followedGameList.contains(game)) {
				((ProfileGamer) profile).addFollowedGame(game);
			}			
		}
		
		return userRepo.saveAndFlush(user);
	}
	
	@Override
	@Transactional(readOnly = false)
	public User unfollowGame(int userId, int gameId) {
		User user = userRepo.findUserById(userId);
		Game game = gameRepo.findGameById(gameId);
		
		if (user.getRole() != Role.GAMER) { return null; }
		
		Profile profile = user.getProfile();
		if (profile instanceof ProfileGamer) {
			((ProfileGamer) profile).removeFollowedGame(game);
		}
		
		return userRepo.saveAndFlush(user);
	}
}
