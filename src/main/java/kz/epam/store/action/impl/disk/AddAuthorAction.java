package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Author;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.AuthorService;
import kz.epam.store.service.impl.AuthorServiceImpl;
import kz.epam.store.util.DataValidator;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

import static kz.epam.store.action.RoutingType.REDIRECT;

public class AddAuthorAction implements Action {
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(REDIRECT, requestContent.getReferer());
            String authorName = requestContent.getRequestParameter(ParameterConstant.AUTHOR);
            if (!DataValidator.validateAuthorName(authorName)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_AUTHOR_NAME, locale));
            } else {
                authorService.addAuthor(new Author(authorName));
            }
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
