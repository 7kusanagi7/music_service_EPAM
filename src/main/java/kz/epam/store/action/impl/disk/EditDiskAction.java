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

public class EditDiskAction implements Action {
    private static final DiskService diskService = new DiskServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, UrlMapping.DISK_LIST_URL);
            int diskId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.DISK_ID));

            Disk disk = diskService.getDiskById(diskId);
            if (!DataValidator.validateDisk(disk)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_DISK, locale));
            }

            genreService.deleteByDiskId(diskId);
            genreService.addDiskGenres(disk.getId(), createGenresList(requestContent));
            diskService.updateDisk(setUpdatedDiskData(requestContent, disk));
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private Disk setUpdatedDiskData(RequestContent requestContent, Disk disk) {
        if (requestContent.getRequestParameter(ParameterConstant.AUTHOR) != null) {
            disk.setAuthorId(Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.AUTHOR)));
        }
        disk.setCoverImage(requestContent.getRequestParameter(ParameterConstant.COVER_IMAGE));
        disk.setPrice(new BigDecimal(requestContent.getRequestParameter(ParameterConstant.PRICE)));
        disk.setTitle(requestContent.getRequestParameter(ParameterConstant.TITLE));
        disk.setDescription(requestContent.getRequestParameter(ParameterConstant.DESCRIPTION));
        return disk;
    }

    private List<Genre> createGenresList(RequestContent requestContent) {
        List<Genre> genres = new ArrayList<>();
        for (String genreName : requestContent.getRequestParameterValues(ParameterConstant.GENRES)) {
            genres.add(new Genre(genreName));
        }
        return genres;
    }
}
