package kz.epam.store.action.impl.user;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.exception.ActionException;

public class LogOutAction implements Action {

    @Override
    public ActionResult execute(RequestContent requestContent) {
        ActionResult result = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
        result.setSessionInvalidated(true);
        return result;
    }
}
