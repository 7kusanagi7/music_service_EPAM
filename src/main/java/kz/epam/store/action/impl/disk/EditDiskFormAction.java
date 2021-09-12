package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Author;
import kz.epam.store.entity.Disk;
import kz.epam.store.entity.Genre;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.AuthorService;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.GenreService;
import kz.epam.store.service.impl.AuthorServiceImpl;
import kz.epam.store.service.impl.DiskServiceImpl;
import kz.epam.store.service.impl.GenreServiceImpl;

import java.util.List;

public class EditDiskFormAction implements Action {
    private static final DiskService diskService = new DiskServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(JspPath.EDIT_DISK_PAGE);

            Disk disk = diskService.getDiskById(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.DISK_ID)));
            List<Genre> diskGenres = genreService.getByDiskGenres(disk.getId());
            List<Genre> genres = genreService.getAll();

            List<Author> authors = authorService.getAll();
            actionResult.putRequestAttribute(ParameterConstant.AUTHORS, authors);

            actionResult.putRequestAttribute(ParameterConstant.GENRES, genres);
            actionResult.putRequestAttribute(ParameterConstant.DISK_GENRES, diskGenres);
            actionResult.putRequestAttribute(ParameterConstant.DISK, disk);

            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
