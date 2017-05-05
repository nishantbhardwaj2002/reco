package reco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Controller
@RequestMapping(value = "index")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String signinPage() {

        return "index";
    }
}
