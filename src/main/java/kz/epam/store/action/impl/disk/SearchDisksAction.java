package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Author;
import kz.epam.store.entity.Disk;
import kz.epam.store.entity.Genre;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.BadRequestException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.AuthorService;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.GenreService;
import kz.epam.store.service.impl.AuthorServiceImpl;
import kz.epam.store.service.impl.DiskServiceImpl;
import kz.epam.store.service.impl.GenreServiceImpl;
import kz.epam.store.util.MessageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchDisksAction implements Action {

    private static final DiskService diskService = new DiskServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        ActionResult actionResult = new ActionResult(JspPath.DISKS_PAGE);
        try {
            List<Disk> foundDisks = findDisks(requestContent);

            Map<Integer, List<Genre>> genres = genreService.getAllDiskGenres();
            Map<Integer, Author> authors = authorService.getAll().stream()
                    .collect(Collectors.toMap(Author::getId, Function.identity()));

            actionResult.putRequestAttribute(ParameterConstant.GENRES, genres);
            actionResult.putRequestAttribute(ParameterConstant.AUTHORS, authors);
            actionResult.putRequestAttribute(ParameterConstant.DISKS, foundDisks);
            actionResult.putRequestAttribute(ParameterConstant.DISK_NUMBER, foundDisks.size());
            return actionResult;
        } catch (ServiceException | BadRequestException e) {
            throw new ActionException(e);
        }
    }

    private List<Disk> findDisks(RequestContent requestContent) throws ServiceException, BadRequestException {
        if (requestContent.getRequestParameter(ParameterConstant.GENRE) != null) {
            List<Disk> foundDisks = new ArrayList<>();
            for (int diskId : genreService.getDisksByGenreName(requestContent.getRequestParameter(ParameterConstant.GENRE))) {
                foundDisks.add(diskService.getDiskById(diskId));
            }
            return foundDisks;
        } else if (requestContent.getRequestParameter(ParameterConstant.AUTHOR) != null) {
            return diskService.getByAuthorId(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.AUTHOR)));
        }
        String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
        String msg = MessageManager.getMessage(MessageConstant.NOTHING_FOUND, locale);
        throw new BadRequestException(msg);
    }
}
