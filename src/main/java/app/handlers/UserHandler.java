package app.handlers;

import app.dao.DAOUser;
import app.helpers.CryptoHelper;
import app.models.users.AddUserRequest;
import app.models.users.LoginRequest;
import app.models.users.User;
import lombok.var;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.*;

import static app.commons.Constants.Roles.*;
import static app.commons.Constants.Features.*;

public class UserHandler implements IUserHandler {
    private static final String USER_ATTRIBUTE  = "#user";
    private static final String HANDLER_KEY = "#userHandler";

    private static Map<String, String[]> permissionMap;
    private static DAOUser _dao;
    private static ServletContext _context;

    // Costruttore privato in quanto instanza singleton
    private UserHandler() {}

    public static void Init(ServletContext context) {
        _dao = new DAOUser();
        _context = context;
        _context.setAttribute(HANDLER_KEY, new UserHandler());
        permissionMap = GetFeaturePermissionsMap();
    }

    public static UserHandler getInstance() throws Exception {
        if (_context == null)
            throw new Exception("Context not provided, missing call to @Init method?");
        return (UserHandler) _context.getAttribute(HANDLER_KEY);
    }

    public User AddUser(AddUserRequest request, HttpSession session) throws Exception {

        if (!IsAuthorized(SIGN_UP, session))
            throw new Exception("User already logged in, require logout"); //  TODO: Eccezione fatta bene, 401 di risposta

        var users = _dao.GetUserByAccountAndPassword(request.Username, request.Email);

        if (!users.isEmpty()) {
            if (users.stream().anyMatch(user -> user.getAccount().equals(request.Username)))
                throw new Exception("User with same account already exists"); //  TODO: Eccezione fatta bene, 409 di risposta
            else
                throw new Exception("User with same email already exists"); //  TODO: Eccezione fatta bene, 409 di risposta
        }

        var encPsw = CryptoHelper.encryptSHA2(request.Password);
        var id = UUID.randomUUID().toString();
        var result = _dao.InsertUser(
                id, request.Username, request.Email, encPsw, request.Name, request.Surname, USER
        );

        if (!result)
            // TODO: Creare eccezione corretta
            throw new Exception("Cannot insert user: Unhandled error");

        var currUser = new User(id, USER);
        session.setAttribute(USER_ATTRIBUTE, currUser);
        return currUser;
    }

    public User Login(LoginRequest request, HttpSession session) throws Exception {

        if (!IsAuthorized(LOGIN, session))
            throw new Exception("User already logged in, require logout"); //  TODO: Eccezione fatta bene, 401 di risposta

        var encPsw = CryptoHelper.encryptSHA2(request.Password);
        var users = _dao.GetUserByAccountAndPassword(request.Account, encPsw);

        if (users.isEmpty())
            throw new Exception("User not found"); // TODO: Eccezione fatta bene, 404 di risposta

        if (users.size() > 1)
            throw new Exception("More than one user found"); // TODO: eccezione fatta bene, 500

        var currentUser = users.stream().findFirst().get();
        session.setAttribute(USER_ATTRIBUTE, currentUser);
        return currentUser;
    }

    public boolean IsAuthorized(String feature, HttpSession session) {

        // No user means log as guest
        if (session.getAttribute(USER_ATTRIBUTE) == null)
            return IsAuthorized(feature, GetDefaultUser());;

        return IsAuthorized(feature, (User)session.getAttribute(USER_ATTRIBUTE));
    }

    public boolean IsAuthorized(String feature, User user) {

        if (!permissionMap.containsKey(feature))
            return false;
        var roles = permissionMap.get(feature);
        return Arrays.stream(roles).anyMatch(user.getRole()::equals);

        // TODO: Implementare il check dell'id utente?
    }

    private static HashMap<String, String[]> GetFeaturePermissionsMap() {
        var map = new HashMap<String,String[]>();

        /// ** User **
        map.put(SIGN_UP, new String[] {GUEST} );
        map.put(LOGIN, new String[] {GUEST} );
        map.put(LOGOUT, new String[] {USER, ADMIN} );

        /// ** Repetition **
        map.put(REPETITIONS_GET_AVAILABLE, new String[] {GUEST, USER, ADMIN } );
        map.put(REPETITIONS_RESERVE, new String[]{USER});
        map.put(REPETITIONS_DELETE, new String[]{USER, ADMIN});
        map.put(REPETITIONS_SET_DONE, new String[]{USER});
        map.put(REPETITIONS_GET_FOR_USER, new String[]{USER, ADMIN});
        map.put(REPETITIONS_GET_FOR_ALL_USERS, new String[]{ADMIN});

        /// ** Course **
        map.put(COURSE_ADD, new String[] {ADMIN});
        map.put(COURSE_GET_PROFESSORS, new String[] {GUEST, USER, ADMIN});
        map.put(COURSE_GET_ALL, new String[] {GUEST, USER, ADMIN});

        // ** Professors **
        map.put(PROFESSOR_ADD, new String[] {ADMIN});
        map.put(PROFESSOR_ADD_COURSE, new String[] {ADMIN});

        return map;
    }

    private static User GetDefaultUser() {
        return new User("", GUEST);
    }

}
