package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Activity;
import sg.edu.nus.iss.gamerecommender.model.Activity.ActivityType;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.Post;
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
	public Post createPost(Post post) {
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
	public List<Post> findPostsByUserId(int userId) {
		return postRepo.findPostsByUserId(userId);
	}
	
	@Override
	public PostGameReview findReviewPostByGameAndUserId(int userId, int gameId) {
		return postRepo.findReviewPostByGameAndUserId(userId, gameId);
	}

	@Override
	@Transactional(readOnly = false)
	public void deletePostGameReview(int reviewId) {
		postRepo.deleteById(reviewId);
		
	}

	@Override
	public PostGameReview findReviewById(int reviewId) {
		return postRepo.findReviewPostByReviewId(reviewId);
	}
}
