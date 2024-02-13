package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.model.PostGame;
import sg.edu.nus.iss.gamerecommender.model.PostGameReview;
import sg.edu.nus.iss.gamerecommender.model.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	@Query("SELECT p FROM PostGameReview p WHERE p.userProfile.user.id=:userId AND p.gameProfile.game.id=:gameId")
	public PostGameReview findReviewPostByGameAndUserId(@Param("userId") int userId, @Param("gameId") int gameId);
	
	@Query("SELECT p FROM PostGame p WHERE p.gameProfile.game.id=:gameId ORDER BY p.datePosted DESC, p.id DESC")
	public Page<PostGame> findUpdatePostsByGameIdDesc(@Param("gameId") int gameId, Pageable pageable);
	
	@Query("SELECT u FROM Post p JOIN p.userProfile.user u WHERE p.id=:postId")
	public User findUserByPostId(@Param("postId") int postId);
}
