package reco.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ValidSessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception {

        // if session is valid, redirect to newsfeed page.
        // only signup and signin pages are included in SpringConfiguration.
        if(req.getSession() != null && req.getSession().getAttribute("userModel") != null) {
            resp.sendRedirect("newsfeed");
            return false;
        }

        return true;
    }
}
