package sg.edu.nus.iss.gamerecommender.service;

import sg.edu.nus.iss.gamerecommender.model.ApprovedProfileGame;

public interface ApprovedProfileGameService {

    ApprovedProfileGame apply(ApprovedProfileGame approvedProfileGame);


    ApprovedProfileGame findById(Integer id);

    ApprovedProfileGame edit(ApprovedProfileGame approvedProfileGame);

    void remove(int id);

}
