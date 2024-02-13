package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String>  {
	@Query("SELECT a FROM Account a WHERE a.username=:username AND a.password=:password")
	public Account findAccountByUsernamePassword(@Param("username") String username, @Param("password") String password);

	@Query("SELECT COUNT(a) FROM Account a JOIN a.user.role r WHERE r IN :roles")
	public long countByRole(@Param("role") User.Role role);

	@Query("SELECT acc FROM Account acc WHERE acc.username LIKE %:name% AND acc.user.role = :role or acc.user.displayName LIKE %:name% AND acc.user.role = :role")
	public List<Account> SearchGamerByName(@Param("name") String name,@Param("role") User.Role role);

	@Query("SELECT acc FROM Account acc WHERE acc.username=:name")
	public Account searchByName(@Param("name") String name);

	@Query("SELECT acc FROM Account acc WHERE acc.username LIKE %:name% AND acc.user.role = :role or acc.user.displayName LIKE %:name% AND acc.user.role = :role")
	public List<Account> SearchDeveloperByName(@Param("name") String name,@Param("role") User.Role role);

}