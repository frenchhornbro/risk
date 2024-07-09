package dataAccess;

import exceptions.DataAccessError;

import java.util.ArrayList;
import java.util.UUID;

public class UserDataAccess extends DataAccess {
    public UserDataAccess() throws DataAccessError {

    }
    public void usernameNotTaken(String username) throws DataAccessError {
        ArrayList<String> dbResponse = super.queryDB("SELECT username FROM userData WHERE username=?", username);
        if (!dbResponse.isEmpty()) throw new DataAccessError("Username is taken", 400);
    }
    public String storeCredentials(String email, String username, String password) throws DataAccessError {
        String authToken = UUID.randomUUID().toString();
        super.updateDB(true ,
                "INSERT INTO userData (email, username, password, authToken) VALUES (?, ?, ?, ?)",
                email, username, password, authToken);
        return authToken;
    }
    public void validateAuthToken(String authToken) throws DataAccessError {
        ArrayList<String> dbResponse = super.queryDB("SELECT authToken FROM userData WHERE authToken=?", authToken);
        if (dbResponse.isEmpty()) throw new DataAccessError("Unauthorized", 401);
    }
    public String getUsername(String authToken) throws DataAccessError {
        ArrayList<String> dbResponse = super.queryDB("SELECT username FROM userData WHERE authToken=?", authToken);
        if (dbResponse.isEmpty()) throw new DataAccessError("Username not found", 400);
        else return dbResponse.getFirst();
    }
}
