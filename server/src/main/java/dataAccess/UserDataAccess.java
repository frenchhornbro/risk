package dataAccess;

import exceptions.DataAccessError;

import java.util.ArrayList;

public class UserDataAccess extends DataAccess {
    public UserDataAccess() throws DataAccessError {

    }
    public ArrayList<String> getUsername(String username) throws DataAccessError {
        return super.queryDB("SELECT username FROM userData WHERE username=?", username);
    }
    public ArrayList<String> getUserData(String authToken) throws DataAccessError {
        return super.queryDB("SELECT username, authToken FROM userData WHERE authToken=?", authToken);
    }
    public void createUserData(String email, String username, String password, String authToken) throws DataAccessError {
        super.updateDB(true,
                "INSERT INTO userData (email, username, password, authToken) VALUES (?, ?, ?, ?)",
                email, username, password, authToken);
    }
}
