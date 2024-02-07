package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;

public interface ProfileGamerRepository extends JpaRepository<ProfileGamer, Integer> {
}
