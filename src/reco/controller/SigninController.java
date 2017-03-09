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
@RequestMapping(value = "signin")
public class SigninController {

    private final UserService userService;

    @Autowired
    public SigninController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String signinPage(Model model) {

        model.addAttribute("command", new UserModel());
        return "signin";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String signinForm(@ModelAttribute("command") final UserModel userModel, final HttpServletRequest req) {

        final UserModel existingUserModel = userService.signin(userModel.getUsername(), userModel.getPassword());
        if(existingUserModel != null) {
            req.getSession().setAttribute("userModel", existingUserModel);
            return "redirect:newsfeed";
        } else {
            return "signin";
        }
    }
}
