package controller;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class Signin extends HttpServlet {

    // TODO
    // session vs cookies: http://stackoverflow.com/questions/5082846/how-to-implement-stay-logged-in-when-user-login-in-to-the-web-application/5083809#5083809

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final String username = req.getParameter("username").toString();
        final String password = req.getParameter("password").toString();

        final User user = service.User.signin(username, password);
        if(user != null) {
            req.getSession().setAttribute("id", user.getId());
            req.getSession().setAttribute("username", user.getUsername());
            resp.sendRedirect("newsfeed.jsp");
        } else {
            resp.sendRedirect("signin.jsp");
        }
    }
}
