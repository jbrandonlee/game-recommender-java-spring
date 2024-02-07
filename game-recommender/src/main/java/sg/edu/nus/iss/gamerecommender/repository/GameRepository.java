package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g JOIN g.developer d WHERE g.developer.id=:id")
    public List<Game> findGamesByDevId(@Param("id") int devId);

    Page<Game> findAll(Specification<Game> specification, Pageable pageable);

    @Query("SELECT g from Game g  where g.developer.id=:developerId")
    Page<Game> findGameByDevId(@Param("developerId") int developerId, Pageable pageable);

    @Query(
        "SELECT count(g) from Game g WHERE g.profile.approvalStatus in :approvalStatus and g.developer.id=:developerId")
    long countByStatus(@Param("approvalStatus") List<ProfileGame.ApprovalStatus> approvalStatus, @Param("developerId") int developerId);

}
