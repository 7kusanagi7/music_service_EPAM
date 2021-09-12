package kz.epam.store.action.impl;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;
import kz.epam.store.exception.ActionException;

public class BannedAction implements Action {
    @Override
    public ActionResult execute(RequestContent requestContent) {
        return new ActionResult(JspPath.BANNED_PAGE);
    }
}
