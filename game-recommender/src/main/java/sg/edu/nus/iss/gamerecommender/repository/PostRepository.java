package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;

public interface PostRepository extends JpaRepository<Post, Integer> {
	@Query("SELECT p FROM Post p WHERE p.userProfile.user.id=:id")
	public List<Post> findPostsByUserId(@Param("id") int userId);
	
	@Query("SELECT p FROM PostGameReview p WHERE p.userProfile.user.id=:userId AND p.gameProfile.game.id=:gameId")
	public PostGameReview findReviewPostByGameAndUserId(@Param("userId") int userId, @Param("gameId") int gameId);
	
	@Query("SELECT p FROM PostGameReview p WHERE p.id=:reviewId")
	public PostGameReview findReviewPostByReviewId(@Param("reviewId") int reviewId);

}
