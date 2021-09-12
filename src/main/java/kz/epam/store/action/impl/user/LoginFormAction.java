package kz.epam.store.action.impl.user;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;

public class LoginFormAction implements Action {

    @Override
    public ActionResult execute(RequestContent requestContent) {
        return new ActionResult(JspPath.LOGIN_PAGE);
    }
}
