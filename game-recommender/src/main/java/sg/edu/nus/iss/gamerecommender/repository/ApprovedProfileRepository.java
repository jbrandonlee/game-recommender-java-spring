package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.ApprovedProfileGame;

public interface ApprovedProfileRepository extends JpaRepository<ApprovedProfileGame, Integer>  {

}