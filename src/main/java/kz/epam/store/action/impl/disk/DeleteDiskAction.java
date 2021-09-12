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

public class DeleteDiskAction implements Action {
    private static final DiskService diskService = new DiskServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            int diskId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.DISK_ID));
            if (diskService.getDiskById(diskId) == null) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                String msg = MessageManager.getMessage(MessageConstant.DISK_NOT_FOUND, locale);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            }
            genreService.deleteByDiskId(diskId);
            diskService.deleteById(diskId);
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
