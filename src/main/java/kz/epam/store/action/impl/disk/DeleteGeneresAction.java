package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.GenreService;
import kz.epam.store.service.impl.DiskServiceImpl;
import kz.epam.store.service.impl.GenreServiceImpl;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteGeneresAction implements Action {
    private static final GenreService genreService = new GenreServiceImpl();
    private static final DiskService diskService = new DiskServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            for (String genreName : requestContent.getRequestParameterValues(ParameterConstant.GENRES)) {
                List<Integer> disksId = genreService.getDisksByGenreName(genreName);
                if (!disksId.isEmpty()) {
                    String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                    String msg = MessageManager.getMessage(MessageConstant.CANT_DELETE_ITEM, locale);
                    return new ActionResult(HttpServletResponse.SC_BAD_REQUEST, String
                            .format(msg, genreName, diskService.getDiskById(disksId.get(0)).getTitle()));
                } else {
                    genreService.deleteGenreByName(genreName);
                }
            }
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
