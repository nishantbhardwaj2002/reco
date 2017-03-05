package reco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Controller
@RequestMapping(value = "signout")
public class SignoutController {

    @RequestMapping(method = RequestMethod.GET)
    public String signoutPage(final HttpServletRequest req) {

        req.getSession().invalidate();
        return "signin";
    }
}
