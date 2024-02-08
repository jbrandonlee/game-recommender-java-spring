package sg.edu.nus.iss.gamerecommender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.Post;
import sg.edu.nus.iss.gamerecommender.repository.PostRepository;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepo;
	
	public List<Post> findPostsByUserId(int userId) {
		return postRepo.findPostsByUserId(userId);
	}
}
