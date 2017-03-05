package reco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    // TODO
    // session vs cookies: http://stackoverflow.com/questions/5082846/how-to-implement-stay-logged-in-when-user-login-in-to-the-web-application/5083809#5083809

    private final UserService userService;

    @Autowired
    public SigninController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String signinPage(final HttpServletRequest req) {

        if(req.getSession() != null && req.getSession().getAttribute("userModel") != null) {
            return "redirect:newsfeed";
        } else {
            return "signin";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String signinForm(@ModelAttribute("UserModel") final UserModel userModel, final HttpServletRequest req) {

        final UserModel existingUserModel = userService.signin(userModel.getUsername(), userModel.getPassword());
        if(existingUserModel != null) {
            req.getSession().setAttribute("userModel", userModel);
            return "redirect:newsfeed";
        } else {
            return "signin";
        }
    }
}
