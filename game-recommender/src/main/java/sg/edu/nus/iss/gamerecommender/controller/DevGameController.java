package sg.edu.nus.iss.gamerecommender.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.gamerecommender.model.Game;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame.ApprovalStatus;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.GameService;

@Controller
@RequestMapping("/dev/game")
public class DevGameController {

	@Autowired
	GameService gameService;
	
	@GetMapping(value = "/list")
	public String devGameList(Model model, HttpSession sessionObj) {
		User user = (User) sessionObj.getAttribute("user");
		List<Game> gameList = gameService.findGamesByDevId(user.getId());
		model.addAttribute("gameList", gameList);
		return "dev-game-list";
	}
	
	@GetMapping(value = "/view/{id}")
	public String gameProfileView(Model model, HttpSession sessionObj) {
		return "dev-game-profile";
	}
	
	@GetMapping(value = "/create")
	public String gameCreateForm(Model model, HttpSession sessionObj) {
		model.addAttribute("game", new Game());
		return "dev-game-create";
	}
	
	@PostMapping("/create")
    public String createNewGame(@Valid @ModelAttribute("game") Game game,
        BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "dev-game-create";
        }
        gameService.createNewGame(game);
        return "redirect:/dev/game/list";
    }
	
	  @PostMapping("/publish/{id}")  
	   //从数据库中获取状态为applied的game,profileGame, 和application
	   //publish之前不可见
	   //publish之后设为可见
	    public String publishGame(@PathVariable(value = "id") int id, @RequestBody Game game,
	        BindingResult bindingResult, Model model, HttpSession session) {
	        if (bindingResult.hasErrors()) {
	            return "redirect:/dev/game/list";
	        }  
	         Game toBePublishedGame=gameService.findApprovedGameById(id); 
	         if(toBePublishedGame.getApplication()!=null) {
	         toBePublishedGame.getApplication().setVisibilityStatus(true); 	 
	         };	     
	         //how to add the changed content ? 
	         model.addAttribute("game", toBePublishedGame);
	         gameService.editGame(game);
	         return "redirect:/dev/game/list";
	    }
	
	
	  @GetMapping(value = "/edit/{id}")
	 public String gameEditForm(@PathVariable(value = "id") int id,
			 @RequestBody Game game, Model model, HttpSession sessionObj) {
	   
		  //Application application=new application();
		  
		// Change Visibility or Details
		return "dev-game-edit";
	}
		
	@PostMapping(value = "/delete/{id}")
	public String gameDelete(Model model, HttpSession sessionObj) {
		return "redirect:/dev/game/list";
	}
	
	
}
