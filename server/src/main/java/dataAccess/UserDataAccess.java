package dataAccess;

import exceptions.DataAccessError;

import java.util.ArrayList;

public class UserDataAccess extends DataAccess {
    public UserDataAccess() throws DataAccessError {

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
