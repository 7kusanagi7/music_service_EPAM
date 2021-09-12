package kz.epam.store.action;

import kz.epam.store.action.impl.BannedAction;
import kz.epam.store.action.impl.ChangeLocaleAction;
import kz.epam.store.action.impl.ErrorAction;
import kz.epam.store.action.impl.IndexAction;
import kz.epam.store.action.impl.admin.ShowAdminPanelAction;
import kz.epam.store.action.impl.admin.ShowUsersAction;
import kz.epam.store.action.impl.cart.*;
import kz.epam.store.action.impl.disk.*;
import kz.epam.store.action.impl.user.*;
import kz.epam.store.util.Pair;

import java.util.HashMap;
import java.util.Map;

import static kz.epam.store.config.UrlMapping.*;

public enum ActionMapping {

    //General actions
    INDEX(INDEX_URL, RequestMethod.GET, new IndexAction()),
    CHANGE_LOCALE(CHANGE_LOCALE_URL, RequestMethod.GET, new ChangeLocaleAction()),
    ERROR_GET(ERROR_URL, RequestMethod.GET, new ErrorAction()),
    ERROR_POST(ERROR_URL, RequestMethod.POST, new ErrorAction()),

    //User actions
    LOGIN_FORM(LOGIN_URL, RequestMethod.GET, new LoginFormAction()),
    LOGIN(LOGIN_URL, RequestMethod.POST, new LogInAction()),
    LOGOUT(LOGOUT_URL, RequestMethod.GET, new LogOutAction()),
    REGISTER_FORM(REGISTER_URL, RequestMethod.GET, new RegisterFormAction()),
    REGISTER(REGISTER_URL, RequestMethod.POST, new RegisterAction()),
    PROFILE(PROFILE_URL, RequestMethod.GET, new ShowProfileAction()),
    EDIT_PROFILE_FORM(EDIT_PROFILE_URL, RequestMethod.GET, new EditProfileFormAction()),
    EDIT_PROFILE(EDIT_PROFILE_URL, RequestMethod.POST, new EditProfileAction()),
    CHANGE_PASSWORD(CHANGE_PASSWORD_URL, RequestMethod.POST, new ChangePasswordAction()),
    BANNED(BANNED_URL, RequestMethod.GET, new BannedAction()),

    //Disk actions
    DISK_LIST(DISK_LIST_URL, RequestMethod.GET, new ShowDisksAction()),
    DISK_SEARCH(DISK_SEARCH_URL, RequestMethod.GET, new SearchDisksAction()),

    //Cart actions
    SHOW_CART(CART_URL, RequestMethod.GET, new ShowCartAction()),
    ADD_TO_CART(CART_URL, RequestMethod.POST, new AddToCartAction()),
    CART_CHANGE_PRICE(CART_CHANGE_PRICE_URL, RequestMethod.POST, new CartChangePriceAction()),
    CART_DELETE_ITEM(CART_DELETE_ITEM_URL, RequestMethod.POST, new CartDeleteItemAction()),
    CART_CHECKOUT(CART_CHECKOUT_URL, RequestMethod.POST, new CheckOutAction()),
    PAY_LOAN(PAY_LOAN_URL, RequestMethod.POST, new PayLoanAction()),

    //Admin actions
    ADMIN_PANEL(ADMIN_PANEL_URL, RequestMethod.GET, new ShowAdminPanelAction()),
    ADMIN_GENRE(ADMIN_GENRE_URL, RequestMethod.POST, new AddGenreAction()),
    ADMIN_AUTHOR(ADMIN_AUTHOR_URL, RequestMethod.POST, new AddAuthorAction()),
    ADMIN_DISK_FORM(ADMIN_DISK_URL, RequestMethod.GET, new AddDiskFormAction()),
    ADMIN_DISK_ADD(ADMIN_DISK_URL, RequestMethod.POST, new AddDiskAction()),
    ADMIN_GENRES_DELETE(ADMIN_DELETE_GENRES_URL, RequestMethod.POST, new DeleteGeneresAction()),
    ADMIN_AUTHORS_DELETE(ADMIN_DELETE_AUTHORS_URL, RequestMethod.POST, new DeleteAuthorsAction()),
    ADMIN_DISK_DELETE(ADMIN_DELETE_DISK_URL, RequestMethod.GET, new DeleteDiskAction()),
    ADMIN_DISK_EDIT_FORM(ADMIN_EDIT_DISK_URL, RequestMethod.GET, new EditDiskFormAction()),
    ADMIN_DISK_EDIT(ADMIN_EDIT_DISK_URL, RequestMethod.POST, new EditDiskAction()),
    ADMIN_USERS(ADMIN_USERS_URL, RequestMethod.GET, new ShowUsersAction()),
    ADMIN_BAN_USER(ADMIN_BAN_USER_URL, RequestMethod.POST, new ChangeBanStatusAction());

    private final String url;
    private final Action action;
    private final RequestMethod method;

    ActionMapping(String url, RequestMethod method, Action action) {
        this.url = url;
        this.method = method;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public RequestMethod getMethod() {
        return method;
    }

    private static final Map<Pair<String, RequestMethod>, ActionMapping> map;
    static {
        map = new HashMap<>();
        for (ActionMapping v : ActionMapping.values()) {
            map.put(new Pair<>(v.url, v.method), v);
        }
    }
    public static ActionMapping findByUrlAndMethod(Pair<String, RequestMethod> urlAndMethod) {
        return map.get(urlAndMethod);
    }


    public enum RequestMethod {

        GET(),
        POST();

        RequestMethod() {
        }
    }

}
