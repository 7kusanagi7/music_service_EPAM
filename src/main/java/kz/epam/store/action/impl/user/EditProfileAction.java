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

public class EditProfileAction implements Action {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, UrlMapping.PROFILE_URL);
            User user = setUpdatedUserData(requestContent);
            if (!DataValidator.validateUser(user)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_USER_DATA, locale));
            } else {
                userService.updateUser(user);
            }
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private User setUpdatedUserData(RequestContent requestContent) {
        User user = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
        user.setUsername(requestContent.getRequestParameter(ParameterConstant.USERNAME));
        user.setEmail(requestContent.getRequestParameter(ParameterConstant.EMAIL));
        user.setFirstName(requestContent.getRequestParameter(ParameterConstant.FIRST_NAME));
        user.setLastName(requestContent.getRequestParameter(ParameterConstant.LAST_NAME));
        user.setCity(requestContent.getRequestParameter(ParameterConstant.CITY));
        user.setAddress(requestContent.getRequestParameter(ParameterConstant.ADDRESS));
        user.setPostalIndex(requestContent.getRequestParameter(ParameterConstant.POSTAL_INDEX));
        return user;
    }
}
