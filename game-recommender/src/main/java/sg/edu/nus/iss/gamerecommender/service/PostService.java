package sg.edu.nus.iss.gamerecommender.service;

import sg.edu.nus.iss.gamerecommender.model.PostGameReview;

public interface PostService {
	public PostGameReview createPostGameReview(PostGameReview post, int userId, int gameId);
	public PostGameReview findReviewPostByGameAndUserId(int userId, int gameId);
}
