package kz.epam.store.action.impl.admin;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.AuthorService;
import kz.epam.store.service.GenreService;
import kz.epam.store.service.impl.AuthorServiceImpl;
import kz.epam.store.service.impl.GenreServiceImpl;

public class ShowAdminPanelAction implements Action {

    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(JspPath.ADMIN_PANEL_PAGE);
            actionResult.putRequestAttribute(ParameterConstant.GENRES, genreService.getAll());
            actionResult.putRequestAttribute(ParameterConstant.AUTHORS, authorService.getAll());
            return actionResult;
        } catch (ServiceException e){
            throw new ActionException(e);
        }
    }
}
