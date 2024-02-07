package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import sg.edu.nus.iss.gamerecommender.model.Post;

public interface PostService {
	public List<Post> findPostsByUserId(int userId);
}
