package kz.epam.store.config;

public class UrlMapping {

    //main mapping
    public static final String INDEX_URL = "/";
    public static final String CHANGE_LOCALE_URL = "/locale";
    public static final String ERROR_URL = "/error";

    //user mapping
    public static final String LOGIN_URL = "/login";
    public static final String REGISTER_URL = "/register";
    public static final String LOGOUT_URL = "/logout";
    public static final String PROFILE_URL = "/profile";
    public static final String EDIT_PROFILE_URL = "/profile/edit";
    public static final String CHANGE_PASSWORD_URL = "/profile/change-password";
    public static final String BANNED_URL = "/banned";

    //disk mapping
    public static final String DISK_LIST_URL = "/disk";
    public static final String DISK_SEARCH_URL = "/disk/search";

    //cart mapping
    public static final String CART_URL = "/cart";
    public static final String CART_CHANGE_PRICE_URL = "/cart/change-price";
    public static final String CART_DELETE_ITEM_URL = "/cart/delete";
    public static final String CART_CHECKOUT_URL = "/cart/checkout";
    public static final String PAY_LOAN_URL = "/cart/pay-loan";

    //admin mapping
    public static final String ADMIN_PANEL_URL = "/admin";
    public static final String ADMIN_GENRE_URL = "/admin/genre";
    public static final String ADMIN_AUTHOR_URL = "/admin/author";
    public static final String ADMIN_DISK_URL = "/admin/disk";
    public static final String ADMIN_DELETE_AUTHORS_URL = "/admin/delete-authors";
    public static final String ADMIN_DELETE_GENRES_URL = "/admin/delete-genres";
    public static final String ADMIN_DELETE_DISK_URL = "/admin/delete-disk";
    public static final String ADMIN_EDIT_DISK_URL = "/admin/edit-disk";
    public static final String ADMIN_USERS_URL = "/admin/users";
    public static final String ADMIN_BAN_USER_URL = "/admin/ban-user";
}
