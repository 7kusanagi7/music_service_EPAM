package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.config.UrlMapping;
import kz.epam.store.entity.Disk;
import kz.epam.store.entity.Genre;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.GenreService;
import kz.epam.store.service.impl.DiskServiceImpl;
import kz.epam.store.service.impl.GenreServiceImpl;
import kz.epam.store.util.DataValidator;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddDiskAction implements Action {
    private static final DiskService diskService = new DiskServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, UrlMapping.ADMIN_PANEL_URL);
            Disk disk = setDiskData(requestContent);
            if (!DataValidator.validateDisk(disk)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_DISK, locale));
            }

            diskService.addDisk(disk);
            genreService.addDiskGenres(disk.getId(),
                    createGenresList(requestContent));
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private Disk setDiskData(RequestContent requestContent) {
        Disk disk = new Disk();
        if (requestContent.getRequestParameter(ParameterConstant.AUTHOR) != null &&
                !requestContent.getRequestParameter(ParameterConstant.AUTHOR).isEmpty()) {
            disk.setAuthorId(Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.AUTHOR)));
        }
        disk.setCoverImage(requestContent.getRequestParameter(ParameterConstant.COVER_IMAGE));
        disk.setPrice(new BigDecimal(requestContent.getRequestParameter(ParameterConstant.PRICE)));
        disk.setTitle(requestContent.getRequestParameter(ParameterConstant.TITLE));
        disk.setDescription(requestContent.getRequestParameter(ParameterConstant.DESCRIPTION));
        return disk;
    }

    private List<Genre> createGenresList(RequestContent requestContent) throws ServiceException {
        List<Genre> genres = new ArrayList<>();
        for (String genreName: requestContent.getRequestParameterValues(ParameterConstant.GENRES)) {
            genres.add(genreService.getGenreByName(genreName));
        }
        return genres;
    }
}
