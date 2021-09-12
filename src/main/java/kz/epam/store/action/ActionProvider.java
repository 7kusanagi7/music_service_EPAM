package kz.epam.store.action;

import kz.epam.store.exception.ActionNotFoundException;
import kz.epam.store.util.Pair;

public class ActionProvider {

    public static Action getAction(RequestContent requestContent) throws ActionNotFoundException {

        Pair<String, ActionMapping.RequestMethod> actionKey = new Pair<>(requestContent.getRequestURL(),
                requestContent.getRequestMethod());

        ActionMapping actionMapping = ActionMapping.findByUrlAndMethod(actionKey);

        if(actionMapping == null)
            throw new ActionNotFoundException();
        return actionMapping.getAction();
    }
}
