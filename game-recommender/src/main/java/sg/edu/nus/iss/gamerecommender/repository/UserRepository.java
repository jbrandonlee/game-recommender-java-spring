package sg.edu.nus.iss.gamerecommender.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.dto.IGenreCount;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;

public interface UserRepository extends JpaRepository<User, Integer> {	
	@Query("SELECT u FROM Account a JOIN a.user u WHERE a.username=:username")
	public User findUserByAccountUsername(@Param("username")String username);
	
	@Query("SELECT u FROM User u WHERE u.displayName=:name")
	public User findUserByDisplayName(@Param("name")String displayName);
	
	@Query("SELECT u FROM User u WHERE u.id=:id")
	public User findUserById(@Param("id") int userId);
	
	@Query("SELECT u FROM User u WHERE u.role=GAMER AND u.displayName LIKE CONCAT('%',:query,'%')")
	public List<User> searchGamers(@Param("query")String query);
	
	@Query("SELECT u FROM User u WHERE u.role=DEVELOPER AND u.displayName LIKE CONCAT('%',:query,'%')")
	public List<User> searchDevelopers(@Param("query")String query);
	
	@Query("SELECT gp AS name, COUNT(gp) AS value FROM User u JOIN u.profile.genrePreferences gp GROUP BY gp")
	public List<IGenreCount> countUserGenrePrefs();
	
	@Query("SELECT COUNT(u) FROM User u JOIN u.profile p WHERE u.role=:role")
	public Integer countAllUsersbyRole(@Param("role") Role role);
	
	@Query("SELECT COUNT(u) FROM User u JOIN u.profile p WHERE u.role=:role AND p.dateCreated = :date")
	public Integer countNewUsersByRoleOnDate(@Param("role") Role role, @Param("date") LocalDate date);
	
	@Query("SELECT COUNT(u) FROM User u JOIN u.profile.followedGames g WHERE g.developer.id=:id")
	public Integer countGamesFollowersByDevId(@Param("id") int devId);
	
	@Query("SELECT COUNT(u) FROM User u JOIN u.profile.followedDevelopers d WHERE d.id=:id")
	public Integer countAccountFollowersByDevId(@Param("id") int devId);
}