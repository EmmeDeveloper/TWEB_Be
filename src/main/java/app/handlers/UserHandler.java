package app.handlers;

import app.commons.Constants;
import app.dao.DAOCourse;
import app.dao.DAOUser;
import app.helpers.CryptoHelper;
import app.models.users.AddUserRequest;
import app.models.users.LoginRequest;
import app.models.users.User;
import lombok.var;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.*;

public class UserHandler implements IUserHandler {
    private static final String USER_ATTRIBUTE  = "#user";
    private static final String HANDLER_KEY = "#userHandler";

    private static Map<String, String[]> PermissionMap;
    private static DAOUser _dao;
    private static ServletContext _context;

    // Costruttore privato in quanto instanza singleton
    private UserHandler() {}

    public static void Init(ServletContext context) {
        _dao = new DAOUser();
        _context = context;
        _context.setAttribute(HANDLER_KEY, new UserHandler());

        PermissionMap = new HashMap<>();
        PermissionMap.put(Constants.Features.ADD_COURSE, new String[] {"ok", "ok", "ok"} );
    }

    public static UserHandler getInstance() throws Exception {
        if (_context == null)
            throw new Exception("Context not provided, missing call to @Init method?");
        return (UserHandler) _context.getAttribute(HANDLER_KEY);
    }

    public User AddUser(AddUserRequest request) throws Exception {
        return null;
    }

    public User Login(LoginRequest request, HttpSession session) throws Exception {

        var encPsw = CryptoHelper.encryptSHA2(request.Password);
        var users = _dao.GetUserByAccountAndPassword(request.Account, encPsw);

        if (users.isEmpty())
            throw new Exception("User not found"); // TODO: Eccezione fatta bene, 404 di risposta

        if (users.size() > 1)
            throw new Exception("More than one user found"); // TODO: eccezione fatta bene, 500

        var currentUser = users.stream().findFirst().get();
        session.setAttribute("user", currentUser);
        return currentUser;
    }

    public boolean IsAuthorized(String feature, HttpSession session) {

        if (session.getAttribute(USER_ATTRIBUTE) == null)
            return  false;

        return IsAuthorized(feature, (User)session.getAttribute(USER_ATTRIBUTE));
    }

    public boolean IsAuthorized(String feature, User user) {

        if (!PermissionMap.containsKey(feature))
            return false;
        var roles = PermissionMap.get(feature);
        return Arrays.stream(roles).anyMatch(user.getRole()::equals);
    }

    private static HashMap<String, String[]> GetFeaturePermissionsMap() {
        var map = new HashMap<String,String[]>();
        map.put(Constants.Features.ADD_COURSE, new String[] {"ok", "ok", "ok"} );

        return map;
    }
}
