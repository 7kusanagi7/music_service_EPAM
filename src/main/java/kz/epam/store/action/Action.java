package kz.epam.store.action;

import kz.epam.store.exception.ActionException;

public interface Action {

    ActionResult execute(RequestContent requestContent) throws ActionException;

}
