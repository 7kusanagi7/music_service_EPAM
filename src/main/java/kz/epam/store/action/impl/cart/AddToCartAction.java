package kz.epam.store.action.impl.cart;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Disk;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.impl.DiskServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AddToCartAction implements Action {

    private static final DiskService diskService = new DiskServiceImpl();
    private static final int INITIAL_DISK_COUNT = 1;

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult result = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            Disk disk = diskService.getDiskById(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.DISK_ID)));


            Map<Integer, Integer> quantities = defineQuantities(requestContent, disk.getId());

            System.out.println(requestContent.getSessionAttribute(ParameterConstant.CART_PRODUCTS));

            Set<Disk> cartDisks = new LinkedHashSet<>();
            if (requestContent.getSessionAttribute(ParameterConstant.CART_PRODUCTS) != null) {
                ((Set<Disk>) requestContent.getSessionAttribute(ParameterConstant.CART_PRODUCTS)).add(disk);
            } else {
                cartDisks.add(disk);
                result.putSessionAttribute(ParameterConstant.CART_PRODUCTS, cartDisks);
            }

            result.putSessionAttribute(ParameterConstant.TOTAL_PRICE, calculateTotalPrice(requestContent, disk));
            result.putSessionAttribute(ParameterConstant.QUANTITIES, quantities);
            return result;
        } catch (ServiceException e){
            throw new ActionException(e);
        }
    }

    private BigDecimal calculateTotalPrice(RequestContent requestContent, Disk disk) {
        BigDecimal totalPrice = (BigDecimal)requestContent.getSessionAttribute(ParameterConstant.TOTAL_PRICE);
        if (totalPrice == null) {
            totalPrice = new BigDecimal(0);
        }
        return totalPrice.add(disk.getPrice());
    }

    private Map<Integer, Integer> defineQuantities(RequestContent requestContent, int diskId) {
        Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute(ParameterConstant.QUANTITIES);
        if (quantities == null) {
            quantities = new HashMap<>();
        }

        if (quantities.computeIfPresent(diskId, (k, v) -> v + 1) == null) {
            quantities.put(diskId, INITIAL_DISK_COUNT);
        }
        return quantities;
    }

}
