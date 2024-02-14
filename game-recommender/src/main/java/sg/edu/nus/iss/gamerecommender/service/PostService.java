package sg.edu.nus.iss.gamerecommender.service;


import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;

public interface PostService {
	public Post createPost(Post post);
	public List<Post> findPostsByUserId(int userId);
	public PostGameReview createPostGameReview(PostGameReview post, int userId, int gameId);
	public PostGameReview findReviewPostByGameAndUserId(int userId, int gameId);
	public PostGameReview findReviewById(int reviewId);
	public void deletePostGameReview(int reviewId);
}
