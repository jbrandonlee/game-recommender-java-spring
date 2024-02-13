package sg.edu.nus.iss.gamerecommender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.gamerecommender.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
