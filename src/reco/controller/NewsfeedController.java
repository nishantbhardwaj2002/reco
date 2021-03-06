package reco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reco.model.UserModel;
import reco.service.NewsRecommendationService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "newsfeed")
public class NewsfeedController {

    private final NewsRecommendationService newsRecommendationService;

    @Autowired
    public NewsfeedController(final NewsRecommendationService newsRecommendationService) {
        this.newsRecommendationService = newsRecommendationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String newsfeedPage(final ModelMap modelMap, final HttpServletRequest req) {

        final UserModel userModel = (UserModel) req.getSession().getAttribute("userModel");
        modelMap.addAttribute("username", userModel.getUsername());
        return "newsfeed";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    protected String newsfeedAjax(@RequestParam final String context, final HttpServletRequest req) {

        return newsRecommendationService.getRecommendedNewsHeads(context, (UserModel) req.getSession().getAttribute("userModel"));
    }
}
