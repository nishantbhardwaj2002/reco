package reco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reco.model.UserModel;
import reco.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Controller
@RequestMapping(value = "signup")
public class SignupController {

    private final UserService userService;

    @Autowired
    public SignupController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String signupPage(final Model model) {

        model.addAttribute("command", new UserModel());
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String signupForm(@ModelAttribute("command") final UserModel userModel, final HttpServletRequest req) {

        final UserModel createdUserModel = userService.signup(userModel.getUsername(), userModel.getPassword());
        if(createdUserModel != null) {
            req.getSession().setAttribute("userModel", createdUserModel);
            return "redirect:newsfeed";
        } else {
            return "signup";
        }
    }
}
