package kz.epam.store.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Returns message from Contents properties depending on provided locale.
 */
public class MessageManager {
    private static final ResourceBundle ruBundle = ResourceBundle.getBundle("Contents", new Locale("ru", "RU"));
    private static final ResourceBundle enBundle = ResourceBundle.getBundle("Contents", new Locale("en", "US"));

    public static String getMessage(String key, String locale) {
        if ("en_US".equalsIgnoreCase(locale)) {
            return enBundle.getString(key);
        }
        return ruBundle.getString(key);
    }
}
