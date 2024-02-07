package sg.edu.nus.iss.gamerecommender.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.yaml.snakeyaml.util.EnumUtils;
import sg.edu.nus.iss.gamerecommender.model.*;
import sg.edu.nus.iss.gamerecommender.model.ProfileGame.ApprovalStatus;
import sg.edu.nus.iss.gamerecommender.service.ApprovedGameService;
import sg.edu.nus.iss.gamerecommender.service.ApprovedProfileGameService;
import sg.edu.nus.iss.gamerecommender.service.GameService;

@Controller
@RequestMapping("/game")
public class DevGameController {

    @Autowired
    GameService gameService;
    @Autowired
    ApprovedGameService approvedGameService;
    @Autowired
    ApprovedProfileGameService approvedProfileGameService;

    @GetMapping(value = "/list")
    public String devGameList(Model model, HttpSession sessionObj) {
        User user = (User)sessionObj.getAttribute("user");
        List<Game> gameList = gameService.findGamesByDevId(user.getId());
        model.addAttribute("gameList", gameList);
        return "game-list";
    }

    @GetMapping(value = "/view/{id}")
    public String gameProfileView(Model model, HttpSession sessionObj) {
        return "dev-game-profile";
    }

    @GetMapping(value = "/edit/{id}")
    public String gameEditForm(Model model, HttpSession sessionObj) {
        // Change Visibility or Details
        return "dev-game-edit";
    }

   
    @GetMapping("/toApply")
    public String toApply(Model model) {
        model.addAttribute("game", new Game());
        return "game-apply";
    }

    @PostMapping("/apply")
    public String apply(@Valid @ModelAttribute("game") Game game, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "game-apply";
        }
        User developer = (User)session.getAttribute("user");
        game.setDeveloper(developer);

        ProfileGame profileGame = new ProfileGame();
        profileGame.setApprovalStatus(ProfileGame.ApprovalStatus.APPLIED);
        game.setProfile(profileGame);
        game.setDateRelease(LocalDate.now());
        gameService.applyGame(game);
        return "redirect:/game/page/1/10";
    }
    //This method gets the status sent by the front-end
    @PostMapping("/audit/{approvalStatus}/{id}")
    public String audit(@PathVariable(value = "approvalStatus") String approvalStatus,
        @PathVariable(value = "id") int id, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "game-apply";
        }
        Game game = gameService.findById(id);
        game.getProfile()
            .setApprovalStatus(EnumUtils.findEnumInsensitiveCase(ProfileGame.ApprovalStatus.class, approvalStatus));
        gameService.editGame(game);
        return "redirect:/game/page/1/10";

    }

    @GetMapping("/published/{id}")
    public String published(@PathVariable(value = "id") int id ){
        Game game = gameService.findById(id);
        game.getProfile()
            .setApprovalStatus(ProfileGame.ApprovalStatus.PUBLISHED);
        ApprovedGame approvedGame = approvedGameService.findById(id);
        ApprovedProfileGame profileGame = new ApprovedProfileGame();
        BeanUtils.copyProperties(game.getProfile(),profileGame);
        profileGame.setId(game.getProfile().getId());
        if (approvedGame == null) {
            BeanUtils.copyProperties(game,approvedGame);
            approvedGame.setProfile(profileGame);
            approvedGameService.applyGame(approvedGame);
        } else {
            BeanUtils.copyProperties(game,approvedGame);
            approvedGame.setProfile(profileGame);
            approvedGame.getProfile().setVisibilityStatus(false);
            approvedGameService.editGame(approvedGame);
        }
        return "redirect:/game/page/1/10";
    }

    @GetMapping("/page/{pageNo}/{pageSize}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
        @PathVariable(value = "pageSize", required = false) int pageSize, Model model, HttpSession session) {

        User developer = (User)session.getAttribute("user");

        Page<Game> page =
            gameService.findGamePage(developer.getId(), pageNo, pageSize == 0 ? 10 : pageSize, "id", "asc");

        List<Game> games = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("pageSize", page.getSize());

        model.addAttribute("games", games);
        return "game-page";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        gameService.removeGame(id);
        return "redirect:/game/page/1/10";
    }

    @GetMapping("/toEdit/{id}")
    public String toEditGame(@PathVariable Integer id, Model model) {

        model.addAttribute("game", gameService.findById(id));

        return "game-edit";
    }

    @PostMapping("/edit")
    public String editGame(@Valid @ModelAttribute("game") Game editedGame, BindingResult bindingResult,
        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "game-edit";
        }
        Game game = gameService.findById(editedGame.getId());
        game.setDescription(editedGame.getDescription());
        game.setTitle(editedGame.getTitle());
        gameService.editGame(game);
        return "redirect:/game/page/1/10";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        model.addAttribute("game", gameService.findById(id));
        return "game-detail";
    }


}
