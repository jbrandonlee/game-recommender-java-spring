package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.gamerecommender.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
