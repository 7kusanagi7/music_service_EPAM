package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Genre;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.GenreService;
import kz.epam.store.service.impl.GenreServiceImpl;
import kz.epam.store.util.DataValidator;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

public class AddGenreAction implements Action {

    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            String genreName = requestContent.getRequestParameter(ParameterConstant.GENRE);
            if (!DataValidator.validateGenreName(genreName)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_GENRE_NAME, locale));
            } else {
                genreService.addGenre(new Genre(genreName));
            }
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
