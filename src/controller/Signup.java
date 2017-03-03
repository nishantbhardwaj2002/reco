package controller;

import model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nishantbhardwaj2002 on 3/3/17.
 */
public class Signup extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final String username = req.getParameter("username").toString();
        final String password = req.getParameter("password").toString();

        final String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        final User user = service.User.create(username, passwordHash);
        if(user != null) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("newsfeed.jsp");
        } else {
            resp.sendRedirect("signup.jsp");
        }
    }
}
