package kz.epam.store.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {

    private static final String REFER_HEADER = "Referer";

    private final String requestURL;

    private final String referer;

    private final ActionMapping.RequestMethod requestMethod;

    private final Map<String, String[]> requestParameters;

    private final Map<String, Object> sessionAttributes;

    public RequestContent(HttpServletRequest request) {
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>(request.getParameterMap());
        requestURL = request.getServletPath();
        requestMethod = ActionMapping.RequestMethod.valueOf(request.getMethod());

        if (request.getHeader(REFER_HEADER) != null) {
            referer = request.getHeader(REFER_HEADER);
        } else referer = null;

        HttpSession currentSession = request.getSession(false);
        if (currentSession != null) {
            Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()) {
                String attributeName = sessionAttributeNames.nextElement();
                sessionAttributes.put(attributeName, currentSession.getAttribute(attributeName));
            }
        }
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getReferer() {
        return referer;
    }

    public ActionMapping.RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getRequestParameter(String parameter) {
        return requestParameters.get(parameter) == null ? null : requestParameters.get(parameter)[0];
    }

    public Object getSessionAttribute(String attribute) {
        return sessionAttributes.get(attribute);
    }

    public String[] getRequestParameterValues(String parameter) {
        return requestParameters.get(parameter);
    }
}
