package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {	
	@Query("SELECT u FROM Account a JOIN a.user u WHERE a.username=:username")
	public User findUserByAccountUsername(@Param("username")String username);
	
	@Query("SELECT u FROM User u WHERE u.displayName=:name")
	public User findUserByDisplayName(@Param("name")String displayName);
	
	@Query("SELECT u FROM User u WHERE u.id=:id")
	public User findUserById(@Param("id") int userId);
	
	@Query("SELECT u FROM User u WHERE u.role=GAMER AND u.displayName LIKE CONCAT('%',:query,'%') AND u.profile.visibilityStatus=true")
	public List<User> searchGamers(@Param("query")String query);
	
	@Query("SELECT u FROM User u WHERE u.role=DEVELOPER AND u.displayName LIKE CONCAT('%',:query,'%')")
	public List<User> searchDevelopers(@Param("query")String query);
}
