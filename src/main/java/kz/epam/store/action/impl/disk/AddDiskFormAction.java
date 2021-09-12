package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Author;
import kz.epam.store.entity.Genre;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.AuthorService;
import kz.epam.store.service.GenreService;
import kz.epam.store.service.impl.AuthorServiceImpl;
import kz.epam.store.service.impl.GenreServiceImpl;

import java.util.List;

public class AddDiskFormAction implements Action {
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(JspPath.PERIODICAL_ADD_PAGE);
            List<Genre> genres = genreService.getAll();
            List<Author> authors = authorService.getAll();

            actionResult.putRequestAttribute(ParameterConstant.GENRES, genres);
            actionResult.putRequestAttribute(ParameterConstant.AUTHORS, authors);
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
