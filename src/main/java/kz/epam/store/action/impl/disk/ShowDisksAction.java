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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowDisksAction implements Action {
    private static final DiskService diskService = new DiskServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();


    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            List<Disk> disks = diskService.getAll();
            Map<Integer, List<Genre>> genres = genreService.getAllDiskGenres();
            Map<Integer, Author> authors = authorService.getAll().stream()
                    .collect(Collectors.toMap(Author::getId, Function.identity()));

            ActionResult actionResult = new ActionResult(JspPath.DISKS_PAGE);
            actionResult.putRequestAttribute(ParameterConstant.GENRES, genres);
            actionResult.putRequestAttribute(ParameterConstant.AUTHORS, authors);
            actionResult.putRequestAttribute(ParameterConstant.DISKS, disks);
            actionResult.putRequestAttribute(ParameterConstant.DISK_NUMBER, diskService.getDisksCount());
            return actionResult;
        } catch (ServiceException e){
            throw new ActionException(e);
        }
    }
}
