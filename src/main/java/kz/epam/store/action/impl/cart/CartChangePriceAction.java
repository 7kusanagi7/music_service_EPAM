package kz.epam.store.action.impl.cart;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.config.UrlMapping;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.impl.DiskServiceImpl;

import java.math.BigDecimal;
import java.util.Map;

public class CartChangePriceAction implements Action {
    private final DiskService diskService = new DiskServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        ActionResult actionResult = new ActionResult(UrlMapping.CART_URL);

        Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute(ParameterConstant.QUANTITIES);
        Integer id = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.DISK_ID));
        int quantity = quantities.get(id);
        quantities.put(id, Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.QUANTITY)));
        BigDecimal totalPrice = calculateTotalPrice(requestContent, id, quantity, quantities);

        actionResult.putSessionAttribute(ParameterConstant.TOTAL_PRICE, totalPrice);
        return actionResult;
    }

    private BigDecimal calculateTotalPrice(RequestContent requestContent, Integer id, int quantity,
                                           Map<Integer, Integer> quantities) throws ActionException {
        try {
            BigDecimal price = diskService.getDiskById(id).getPrice();
            BigDecimal oldSubtotal = price.multiply(new BigDecimal(quantity));
            BigDecimal newSubtotal = price.multiply(new BigDecimal(quantities.get(id)));
            BigDecimal totalPrice = (BigDecimal)requestContent.getSessionAttribute(ParameterConstant.TOTAL_PRICE);
            totalPrice = totalPrice.subtract(oldSubtotal);
            totalPrice = totalPrice.add(newSubtotal);
            return totalPrice;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
