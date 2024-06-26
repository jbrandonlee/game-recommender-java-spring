package sg.edu.nus.iss.gamerecommender.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
	
	@Query("SELECT g FROM Game g WHERE g.id=:id")
	public Game findGameById(@Param("id") int gameId);
	
	@Query("SELECT g FROM Game g WHERE g.developer.id=:id")
	public List<Game> findGamesByDevId(@Param("id") int devId);
	
	@Query("SELECT g FROM Game g WHERE g.developer.id=:id")
	public Page<Game> findGamesByDevId(@Param("id") int devId, Pageable pageable);

	@Query("SELECT g.id FROM Game g WHERE g.developer.id=:id")
	public List<Integer> findGameIdsByDevId(@Param("id") int devId);
	
	@Query("SELECT g FROM Game g WHERE g.title LIKE CONCAT('%',:query,'%')")
	public List<Game> searchGames(@Param("query")String query);
	
	@Query("SELECT g FROM Game g ORDER BY g.rating DESC")
	public List<Game> findAllSortedTopRating();
	
	@Query("SELECT g FROM Game g ORDER BY g.rating DESC")
	public Page<Game> findTopRated(Pageable pageable);
	
	@Query("SELECT g.title FROM Game g ORDER BY g.rating DESC")
	public Page<String> findTopRatedTitles(Pageable pageable);
	
	@Query("SELECT g.rating FROM Game g ORDER BY g.rating DESC")
	public Page<Double> findTopRatedRatings(Pageable pageable);
	
	@Query("SELECT g FROM Game g JOIN g.developer d WHERE g.developer.id=:id ORDER BY g.rating DESC")
	public Page<Game> findTopRatedByDevId(@Param("id") int devId, Pageable pageable);
	
	@Query("SELECT g.title FROM Game g JOIN g.developer d WHERE g.developer.id=:id ORDER BY g.rating DESC")
	public Page<String> findTopRatedTitlesByDevId(@Param("id") int devId, Pageable pageable);
	
	@Query("SELECT g.rating FROM Game g JOIN g.developer d WHERE g.developer.id=:id ORDER BY g.rating DESC")
	public Page<Double> findTopRatedRatingsByDevId(@Param("id") int devId, Pageable pageable);
		
	@Query("SELECT g FROM User u JOIN u.profile.followedGames g GROUP BY g.id ORDER BY COUNT(g) DESC")
	public Page<Game> findTopFollowed(Pageable pageable);
	
	@Query("SELECT g.title FROM User u JOIN u.profile.followedGames g GROUP BY g.id ORDER BY COUNT(g) DESC")
	public Page<String> findTopFollowedTitles(Pageable pageable);
	
	@Query("SELECT COUNT(g) FROM User u JOIN u.profile.followedGames g GROUP BY g.id ORDER BY COUNT(g) DESC")
	public Page<Integer> countTopFollowedTitlesFollowers(Pageable pageable);
	
	@Query("SELECT g FROM User u JOIN u.profile.followedGames g WHERE g.developer.id=:id GROUP BY g.id ORDER BY COUNT(g) DESC")
	public Page<Game> findTopFollowedByDevId(@Param("id") int devId, Pageable pageable);
	
	@Query("SELECT g.title FROM User u JOIN u.profile.followedGames g WHERE g.developer.id=:id GROUP BY g.id ORDER BY COUNT(g) DESC")
	public Page<String> findTopFollowedTitlesByDevId(@Param("id") int devId, Pageable pageable);
	
	@Query("SELECT COUNT(g) FROM User u JOIN u.profile.followedGames g WHERE g.developer.id=:id GROUP BY g.id ORDER BY COUNT(g) DESC")
	public Page<Integer> countTopFollowedTitlesFollowersByDevId(@Param("id") int devId, Pageable pageable);
		
	@Query("SELECT genres AS name, COUNT(genres) AS value FROM Game g JOIN g.genres genres WHERE g.developer.id=:id GROUP BY genres")
	public List<IGenreCount> countGameGenreDistributionByDevId(@Param("id") int devId);
	
	@Query("SELECT COUNT(g) FROM Game g")
	public Integer countAllGames();

	@Query("SELECT COUNT(g) FROM Game g WHERE g.developer.id=:id")
	public Integer countGamesByDevId(@Param("id") int devId);
	
	@Query("SELECT COUNT(g) FROM Game g JOIN g.profile p WHERE p.dateCreated = :date")
	public Integer countNewGamePagesOnDate(@Param("date") LocalDate date);
	
	@Query("SELECT AVG(g.rating) FROM Game g WHERE g.developer.id=:id")
	public Double getAverageGameRatingByDevId(@Param("id") int devId);
	
	@Query("SELECT g FROM Game g WHERE g.id IN (:idList)")
	public List<Game> findGamesFromIdList(@Param("idList") List<String> idList);
	
	@Query("SELECT g FROM PostGameReview p JOIN p.gameProfile.game g WHERE p.id=:postId")
	public Game findGameByReviewPostId(@Param("postId") int postId);
}