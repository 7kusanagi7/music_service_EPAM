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
import kz.epam.store.util.DataValidator;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

public class ChangePasswordAction implements Action {
    private static final String OLD_PASSWORD = "old_password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String CONFIRM_PASSWORD = "confirm_password";

    private final UserService userService = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            String oldPassword = requestContent.getRequestParameter(OLD_PASSWORD);
            String newPassword = requestContent.getRequestParameter(NEW_PASSWORD);
            String confirmNewPassword = requestContent.getRequestParameter(CONFIRM_PASSWORD);
            if (!DataValidator.validatePassword(newPassword) || !newPassword.equals(confirmNewPassword)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_NEW_PASSWORD, locale));
            }
            User currentUser = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
            User user = userService.logIn(currentUser.getUsername(), oldPassword);
            if (user != null) {
                userService.updateUserPassword(user, newPassword);
            } else {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_OLD_PASSWORD, locale));
            }
            return new ActionResult(RoutingType.REDIRECT, UrlMapping.PROFILE_URL);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
