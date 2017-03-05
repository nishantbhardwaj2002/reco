package reco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Controller
@RequestMapping(value = "newsItem")
public class NewsItemController extends HttpServlet {

    @RequestMapping(method = RequestMethod.GET)
    protected String newsItemPage () {
        return "redirect:newsfeed";
    }
}
