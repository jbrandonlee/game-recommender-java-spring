package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.model.PostGame;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;
import sg.edu.nus.iss.gamerecommender.model.User;

public interface PostService {
	
	public Post findById(int postId);
	public User findUserByPostId(int postId);
	public List<Post> findPostsByUserId(int userId);
	public PostGame createPostGame(PostGame post, int gameId);
	public PostGame updatePostGame(PostGame post);
	public PostGameReview createPostGameReview(PostGameReview post, int userId, int gameId);
	public PostGameReview findReviewPostByGameAndUserId(int userId, int gameId);
	public PostGameReview findReviewById(int reviewId);
	public void deletePost(Post post);
	public void deletePostGameReview(int reviewId);
	public Page<PostGameReview> findReviewPostsByGameIdDesc(int gameId, int pageNo, int pageSize);
	public Page<PostGame> findUpdatePostsByGameIdDesc(int gameId, int pageNo, int pageSize);
}
