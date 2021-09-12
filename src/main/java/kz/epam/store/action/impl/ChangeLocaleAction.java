package kz.epam.store.action.impl;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

public class ChangeLocaleAction implements Action {

    @Override
    public ActionResult execute(RequestContent requestContent) {
        ActionResult commandResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
        String selectedLocale = requestContent.getRequestParameter(ParameterConstant.LOCALE);

        if (selectedLocale == null ||
                (!selectedLocale.equalsIgnoreCase(ParameterConstant.EN_LOCALE) && !selectedLocale.equalsIgnoreCase(ParameterConstant.RU_LOCALE))) {
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            String msg = MessageManager.getMessage(MessageConstant.INVALID_LOCALE, locale);
            return new ActionResult(HttpServletResponse.SC_BAD_REQUEST, msg);
        }

        commandResult.putSessionAttribute(ParameterConstant.LOCALE, selectedLocale);
        return commandResult;
    }
}
