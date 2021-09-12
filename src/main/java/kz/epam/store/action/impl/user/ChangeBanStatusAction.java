package kz.epam.store.action.impl.user;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.User;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.UserService;
import kz.epam.store.service.impl.UserServiceImpl;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

public class ChangeBanStatusAction implements Action {
    private final UserService userService = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            int userId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.USER_ID));
            User user = userService.getUserById(userId);

            if (user == null) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.USER_NOT_FOUND, locale));
            } else if (user.isAdmin()) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.CANT_BAN_ADMIN, locale));
            }

            boolean banStatus = !user.isBanned();
            user.setBanned(banStatus);
            userService.updateUser(user);
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
