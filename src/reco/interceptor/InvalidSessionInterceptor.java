package reco.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nishantbhardwaj2002 on 3/5/17.
 */
public class InvalidSessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest req, final HttpServletResponse resp, final Object handler) throws Exception {

        // if session is invalid, redirect to signin page.
        // signup and signin pages are excluded in SpringConfiguration.
        if(req.getSession() == null || req.getSession().getAttribute("userModel") == null) {
            resp.sendRedirect("signin");
            return false;
        }

        return true;
    }
}
