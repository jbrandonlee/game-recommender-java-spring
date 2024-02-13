package sg.edu.nus.iss.gamerecommender.service;

import org.springframework.data.domain.Page;

import sg.edu.nus.iss.gamerecommender.model.PostGame;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;

public interface PostService {
	public PostGameReview createPostGameReview(PostGameReview post, int userId, int gameId);
	public PostGameReview findReviewPostByGameAndUserId(int userId, int gameId);
	public Page<PostGame> findUpdatePostsByGameIdDesc(int gameId, int pageNo, int pageSize);
}
