package app.dao;

import app.models.users.User;
import lombok.var;

import java.util.ArrayList;

public class DAOUser extends DAOBase {

    private static final String GET_USER_BY_PSW_AND_ACCOUNT_OR_EMAIL_QUERY = "SELECT * FROM users where (account = '?' OR email = '?' ) AND password = '?'";
    private static final String GET_USER_BY_ACCOUNT_OR_EMAIL_QUERY = "SELECT * FROM users where account = '?' OR email = '?' ";

    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users where ID = '?'";

    private static final String INSERT_USER_QUERY = "INSERT INTO users (ID, account, email, password, name, surname, role) VALUES ('?','?','?','?','?','?','?')";


    public DAOUser() {
        super();
    }

    public ArrayList<User> GetUserByAccountAndPassword(String account, String password) throws Exception {
        return GetUsers(GET_USER_BY_PSW_AND_ACCOUNT_OR_EMAIL_QUERY, account, account, password);
    }

    public ArrayList<User> GetUserByAccountOrEmail(String account, String email) throws Exception {
        return GetUsers(GET_USER_BY_ACCOUNT_OR_EMAIL_QUERY, account, email);
    }

    public ArrayList<User> GetUserByID(String id) throws Exception {
        return GetUsers(GET_USER_BY_ID_QUERY, id);
    }

    private ArrayList<User> GetUsers(String query, Object... args) throws Exception {
        var list = new ArrayList<User>();
        var result = super.executeQuery(query, args);
        if (result != null) {
            while (result.next()) {
                var u = new User(
                    result.getString("ID"),
                    result.getString("role")
                );
                u.setAccount(result.getString("account"));
                u.setEmail(result.getString("email"));
                list.add(u);
            }
        }

        return list;
    }

    ///
    /// Returns if user was insert correctly
    ///
    public boolean InsertUser(
            String ID, String username, String email, String password, String name, String surname, String role
    ) throws Exception {
        // MM-NOTA: Usiamo un guid al posto degli id e mettiamo sul db PK sulla mail e sull'account,
        // risparmiamo query e controlli facendo fare il tutto al db
        int addRows = super.executeUpdateQuery(
                INSERT_USER_QUERY,
                ID, username, email, password, name, surname, role
        );
        return addRows > 0;
    }

}
