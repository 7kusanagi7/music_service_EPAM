package kz.epam.store.util;

import kz.epam.store.entity.Disk;
import kz.epam.store.entity.User;

public class DataValidator {
    private static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9-_.]{2,32}$";
    private static final String NAME_REGEX = "^[A-Z\\u0400-\\u04ff][a-z\\u0400-\\u04ff.'-]{2,35}$";
    private static final String CITY_REGEX = "^[a-zA-Z\\u0400-\\u04ff]+(?:[\\s-][a-zA-Z\\u0400-\\u04ff]+)*$";
    private static final String PASSWORD_REGEX = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String POSTAL_REGEX = "^[0-9]{6}$";
    private static final String ADDRESS_REGEX = "^[a-zA-Z\\u0400-\\u04ff]+(?:[\\s-.,/\\\\]+[a-zA-Z\\u0400-\\u04ff0-9]+)*$";
    private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    private static final String GENRE_REGEX = "^[А-ЯЁA-Z][А-ЯЁA-Zа-яёa-z\\s]{2,50}$";
    private static final String AUTHOR_REGEX = "^[А-ЯЁA-Z][А-ЯЁA-Zа-яёa-z\\s]{2,99}$";
    private static final String TITLE_REGEX = "^[А-ЯЁA-Z][A-Za-z\\u0400-\\u04ff\\s]{2,255}$";
    private static final String PRICE_REGEX = "^\\d+(([.,])\\d{1,2})?$";
    private static final String DESCRIPTION_REGEX = "^[А-ЯЁA-Z][A-Za-z\\u0400-\\u04ff\\s\\p{Punct}\\p{Digit}«»—]{3,63000}$";

    public static boolean validateGenreName(String genreName) {
        return genreName != null && genreName.matches(GENRE_REGEX);
    }

    public static boolean validateUser(User user) {
        return user.getUsername().matches(USERNAME_REGEX) &&
                validatePassword(user.getPassword()) &&
                user.getAddress().matches(ADDRESS_REGEX) &&
                user.getCity().matches(CITY_REGEX) &&
                user.getEmail().matches(EMAIL_REGEX) &&
                user.getFirstName().matches(NAME_REGEX) &&
                user.getLastName().matches(NAME_REGEX) &&
                user.getPostalIndex().matches(POSTAL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateAuthorName(String authorName) {
        return authorName != null && authorName.matches(AUTHOR_REGEX);
    }

    public static boolean validateDisk(Disk disk) {
        return disk.getTitle().matches(TITLE_REGEX) &&
                disk.getPrice().toString().matches(PRICE_REGEX) &&
                disk.getDescription().matches(DESCRIPTION_REGEX);
    }
}
