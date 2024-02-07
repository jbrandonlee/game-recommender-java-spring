package sg.edu.nus.iss.gamerecommender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import sg.edu.nus.iss.gamerecommender.model.ApprovedGame;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.service.ApprovedGameService;


@Controller
@RequestMapping("/approvedgame")
public class ApprovedGameController {

    @Autowired
    ApprovedGameService ApprovedgameService;


    @GetMapping("/page/{pageNo}/{pageSize}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
        @PathVariable(value = "pageSize", required = false) int pageSize, Model model, HttpSession session) {

        User developer = (User)session.getAttribute("user");

        Page<ApprovedGame> page =
        	ApprovedgameService.findGamePage(developer.getId(), pageNo, pageSize == 0 ? 10 : pageSize, "id", "asc");

        List<ApprovedGame> games = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("pageSize", page.getSize());

        model.addAttribute("games", games);
        return "game-page";
    }
}