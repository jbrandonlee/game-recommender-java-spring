package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
	
	@Query("SELECT g FROM Game g JOIN g.developer d WHERE g.developer.id=:id")
	public List<Game> findGamesByDevId(@Param("id") int devId);
	
	@Query("SELECT g FROM Game g WHERE g.id=:id")
	public Game findGameById(@Param("id") int gameId);
	
	//@Query("SELECT g FROM Game g WHERE g.title=:title")
	//public Game findGameByGameTitle(@Param("id") String title);

	@Query("SELECT g FROM Game g ORDER BY g.rating DESC")
	List<Game> findTop10Games(Pageable pageable);

	@Query("SELECT g FROM Game g WHERE LOWER(g.title) LIKE LOWER(CONCAT('%', :title, '%'))")
	List<Game> findByTitle(String title);

	@Query("SELECT g FROM Game g WHERE g.gameStatus = :status")
	List<Game> findByGameStatus(@Param("status") Game.GameStatus gameStatus);

	@Transactional
	@Modifying
	@Query("UPDATE Game g SET g.gameStatus = :status WHERE g.id = :gameId")
	void updateGameStatus(@Param("gameId") Integer gameId, @Param("status") Game.GameStatus gameStatus);

	@Query("SELECT COUNT(g) FROM Game g WHERE g.gameStatus = :status")
	long countByPendingGames(@Param("status") Game.GameStatus status);

	@Query("SELECT g FROM Game g WHERE g.title LIKE %:searchTerm% OR g.developer.displayName LIKE %:searchTerm%")
	List<Game> searchByGameNameOrDeveloperName(@Param("searchTerm") String searchTerm);
}
