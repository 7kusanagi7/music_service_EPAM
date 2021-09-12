package kz.epam.store.action.impl.disk;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Disk;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.AuthorService;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.impl.AuthorServiceImpl;
import kz.epam.store.service.impl.DiskServiceImpl;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteAuthorsAction implements Action {
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final DiskService diskService = new DiskServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            for (String authorIdParam : requestContent.getRequestParameterValues(ParameterConstant.AUTHORS)) {
                int authorId = Integer.parseInt(authorIdParam);
                List<Disk> disks = diskService.getByAuthorId(authorId);
                if (!disks.isEmpty()) {
                    String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                    String msg = MessageManager.getMessage(MessageConstant.CANT_DELETE_ITEM, locale);
                    return new ActionResult(HttpServletResponse.SC_BAD_REQUEST, String
                            .format(msg, authorService.getById(authorId).getFullName(), disks.get(0).getTitle()));
                } else {
                    authorService.deleteById(authorId);
                }
            }
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
