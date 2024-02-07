package sg.edu.nus.iss.gamerecommender.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.nus.iss.gamerecommender.model.ApprovedGame;
//import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;

	public interface ApprovedGameRepository extends JpaRepository<ApprovedGame, Integer> {

	    @Query("SELECT g FROM Game g JOIN g.developer d WHERE g.developer.id=:id")
	    public List<ApprovedGame> findGamesByDevId(@Param("id") int devId);

	    Page<ApprovedGame> findAll(Specification<ApprovedGame> specification, Pageable pageable);

	    @Query("SELECT g from Game g  where g.developer.id=:developerId")
	    Page<ApprovedGame> findGameByDevId(@Param("developerId") int developerId, Pageable pageable);

	    @Query(
	        "SELECT count(g) from Game g WHERE g.profile.approvalStatus in :approvalStatus and g.developer.id=:developerId")
	    long countByStatus(@Param("approvalStatus") List<ProfileGame.ApprovalStatus> approvalStatus, @Param("developerId") int developerId);
	    


}
