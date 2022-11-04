package app.dao;

import app.models.users.User;
import lombok.var;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOUser extends DAOBase {

    private static final String GET_USER_BY_PSW_AND_ID_QUERY = "SELECT * FROM users where (account = ? OR email = ? ) AND password = ?";

    public DAOUser() {
        super();
    }

    public ArrayList<User> GetUserByAccountAndPassword(String account, String password) throws Exception {
        return GetUsers(GET_USER_BY_PSW_AND_ID_QUERY, account, account, password);
    }

    private ArrayList<User> GetUsers(String query, Object... args) throws Exception {
        var list = new ArrayList<User>();
        var result = super.executeQuery(query,args);
        while(result.next()) {
            list.add(new User(
                    result.getString("id"),
                    result.getString("role")
            )
            );
        }
        return list;
    }

}
