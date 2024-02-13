package sg.edu.nus.iss.gamerecommender.service;

import org.springframework.data.domain.Page;

import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.model.PostGame;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;
import sg.edu.nus.iss.gamerecommender.model.User;

public interface PostService {
	public Post findById(int postId);
	public User findUserByPostId(int postId);
	public void deletePost(Post post);
	public PostGame createPostGame(PostGame post, int gameId);
	public PostGame updatePostGame(PostGame post);
	public PostGameReview createPostGameReview(PostGameReview post, int userId, int gameId);
	public PostGameReview findReviewPostByGameAndUserId(int userId, int gameId);
	public Page<PostGame> findUpdatePostsByGameIdDesc(int gameId, int pageNo, int pageSize);
}
