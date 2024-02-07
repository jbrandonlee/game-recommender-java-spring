package sg.edu.nus.iss.gamerecommender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.repository.ProfileGamerRepository;

@Service
@Transactional(readOnly=false)
public class ProfileGamerServiceImpl implements ProfileGamerService{
	@Autowired
	ProfileGamerRepository profileGamerRepo;
	
	@Override
	public void saveProfileGamer(ProfileGamer gamer) {
		
		profileGamerRepo.save(gamer);
	}
}
