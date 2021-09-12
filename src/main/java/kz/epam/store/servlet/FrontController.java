package kz.epam.store.servlet;

import kz.epam.store.action.*;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ActionNotFoundException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info(request.getRequestURI());

        RequestContent requestContent = new RequestContent(request);

        try {
            Action action = ActionProvider.getAction(requestContent);
            ActionResult actionResult = action.execute(requestContent);
            actionResult.updateRequest(request);

            if(RoutingType.FORWARD.equals(actionResult.getRoutingType()))
                getServletContext().getRequestDispatcher(actionResult.getPage()).forward(request, response);
            else if(RoutingType.REDIRECT.equals(actionResult.getRoutingType()))
                response.sendRedirect(request.getContextPath() + actionResult.getPage());
            else
                response.sendError(actionResult.getErrorCode(), actionResult.getErrorMessage());

        } catch (ActionException e) {
            LOGGER.warn(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ActionNotFoundException e) {
            LOGGER.warn(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
