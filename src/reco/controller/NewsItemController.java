package reco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reco.service.NewsItemService;

import javax.servlet.http.HttpServlet;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
@Controller
@RequestMapping(value = "newsItem")
public class NewsItemController extends HttpServlet {

    private final NewsItemService newsItemService;

    @Autowired
    public NewsItemController(final NewsItemService newsItemService) {
        this.newsItemService = newsItemService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    protected String newsItemPage (@RequestParam final String newsId) {

        return newsItemService.getNewsItem(newsId).getNewsBody();
    }
}
