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


public class RegisterAction implements Action {

    private final UserService service = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        User user = generateUser(requestContent);
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, UrlMapping.LOGIN_URL);
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            if (!DataValidator.validateUser(user)) {
                String msg = MessageManager.getMessage(MessageConstant.INVALID_USER_DATA, locale);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            } else if (service.getUserByUsername(user.getUsername()) != null) {
                String msg = MessageManager.getMessage(MessageConstant.USERNAME_EXISTS, locale);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            } else if (service.getUserByEmail(user.getEmail()) != null) {
                String msg = MessageManager.getMessage(MessageConstant.EMAIL_EXISTS, locale);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            } else {
                service.register(user);
            }
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private User generateUser(RequestContent requestContent){
        String username = requestContent.getRequestParameter(ParameterConstant.USERNAME);
        String password = requestContent.getRequestParameter(ParameterConstant.PASSWORD);
        String email = requestContent.getRequestParameter(ParameterConstant.EMAIL);
        boolean admin = false;
        String firstName = requestContent.getRequestParameter(ParameterConstant.FIRST_NAME);
        String lastName = requestContent.getRequestParameter(ParameterConstant.LAST_NAME);
        String city = requestContent.getRequestParameter(ParameterConstant.CITY);
        String address = requestContent.getRequestParameter(ParameterConstant.ADDRESS);
        String postalIndex = requestContent.getRequestParameter(ParameterConstant.POSTAL_INDEX);

        User user = new User();
        user.setUsername(username).setPassword(password).setEmail(email).setAdmin(admin).setFirstName(firstName)
                .setLastName(lastName).setCity(city).setAddress(address).setPostalIndex(postalIndex);
        return user;
    }
}
