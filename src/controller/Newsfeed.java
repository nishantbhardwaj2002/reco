package controller;

import service.NewsRecommendation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class Newsfeed extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String context = req.getParameter("context").toString();
        final NewsRecommendation newsRecommendation = new NewsRecommendation();

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(newsRecommendation.getRecommendedNewsHeads(context));
    }
}
