package sg.edu.nus.iss.gamerecommender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.model.Activity.ActivityType;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.model.PostGame;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.repository.ActivityRepository;
import sg.edu.nus.iss.gamerecommender.repository.GameRepository;
import sg.edu.nus.iss.gamerecommender.repository.PostRepository;
import sg.edu.nus.iss.gamerecommender.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	GameRepository gameRepo;
	
	@Autowired
	ActivityRepository activityRepo;
	
	@Override
	@Transactional(readOnly = false)
	public PostGame createPostGame(PostGame post, int gameId) {
		PostGame postGame = postRepo.saveAndFlush(post);
		Game game = gameRepo.findGameById(gameId);
		Activity activity = new Activity(ActivityType.GAME_UPDATE_POST, game.getId(), game.getTitle(), postGame.getId(), postGame.getTitle());
		return postGame;
	}
	
	@Override
	@Transactional(readOnly = false)
	public PostGame updatePostGame(PostGame post) {
		return postRepo.saveAndFlush(post);
	}
	
	@Override
	@Transactional(readOnly = false)
	public PostGameReview createPostGameReview(PostGameReview post, int userId, int gameId) {
		User user = userRepo.findUserById(userId);
		Game game = gameRepo.findGameById(gameId);
		
		// If Create New Review (not Update)
		if (post.getId() == 0) {
			Activity activity = new Activity(ActivityType.USER_CREATE_GAME_REVIEW, user.getId(), user.getDisplayName(), game.getId(), game.getTitle());
			activityRepo.saveAndFlush(activity);
		}
		
		return postRepo.saveAndFlush(post);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deletePost(Post post) {
		postRepo.delete(post);
	}

	@Override
	public Post findById(int postId) {
		return postRepo.findById(postId).orElse(null);
	}
	
	@Override
	public User findUserByPostId(int postId) {
		return postRepo.findUserByPostId(postId);
	}
	
	@Override
	public PostGameReview findReviewPostByGameAndUserId(int userId, int gameId) {
		return postRepo.findReviewPostByGameAndUserId(userId, gameId);
	}
	
	@Override
	public Page<PostGame> findUpdatePostsByGameIdDesc(int gameId, int pageNo, int pageSize) {
		return postRepo.findUpdatePostsByGameIdDesc(gameId, PageRequest.of(pageNo-1, pageSize));
	}
}
