package kz.epam.store.action.impl.user;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.config.UrlMapping;
import kz.epam.store.entity.User;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.UserService;
import kz.epam.store.service.impl.UserServiceImpl;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

public class LogInAction implements Action {

    private final UserService service = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        ActionResult actionResult;
        if(!requestContent.getRequestURL().equals(UrlMapping.LOGIN_URL))
            actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
        else actionResult = new ActionResult(RoutingType.REDIRECT, UrlMapping.INDEX_URL);

        String username = requestContent.getRequestParameter(ParameterConstant.USERNAME);
        String password = requestContent.getRequestParameter(ParameterConstant.PASSWORD);

        try {
            User user = service.logIn(username, password);
            if (user != null) {
                actionResult.putSessionAttribute(ParameterConstant.USER, user);
            } else {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_CREDENTIALS, locale));
            }
            return actionResult;
        } catch (ServiceException e){
            throw new ActionException(e);
        }
    }
}
