package kz.epam.store.action.impl.admin;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.User;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.UserService;
import kz.epam.store.service.impl.UserServiceImpl;

import java.util.List;

public class ShowUsersAction implements Action {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(JspPath.USERS_PAGE);
            List<User> users = userService.getAllUsers();
            actionResult.putRequestAttribute(ParameterConstant.USERS, users);
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
