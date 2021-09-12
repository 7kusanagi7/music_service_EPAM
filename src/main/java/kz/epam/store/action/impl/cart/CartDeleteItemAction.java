package kz.epam.store.action.impl.cart;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Disk;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.impl.DiskServiceImpl;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class CartDeleteItemAction implements Action {
    private static final DiskService diskService = new DiskServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            Disk disk = diskService.getDiskById(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.DISK_ID)));
            Set<Disk> disks = (Set<Disk>)requestContent.getSessionAttribute(ParameterConstant.CART_PRODUCTS);

            if (disks.contains(disk)) {
                disks.remove(disk);
            } else {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new ActionResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.CART_PRODUCT_NOT_FOUND, locale));
            }

            BigDecimal totalPrice = calculateTotalPrice(requestContent, disk);
            actionResult.putSessionAttribute(ParameterConstant.TOTAL_PRICE, totalPrice);
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private BigDecimal calculateTotalPrice(RequestContent requestContent, Disk disk) {
        BigDecimal totalPrice = (BigDecimal)requestContent.getSessionAttribute(ParameterConstant.TOTAL_PRICE);
        Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute(ParameterConstant.QUANTITIES);
        int quantity = quantities.get(disk.getId());
        totalPrice = totalPrice.subtract(disk.getPrice().multiply(BigDecimal.valueOf(quantity)));
        quantities.remove(disk.getId());
        return totalPrice;
    }
}
