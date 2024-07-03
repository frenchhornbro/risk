package dataAccess;

import exceptions.DataAccessError;

public class UserDataAccess extends DataAccess {
    public UserDataAccess() throws DataAccessError {

    }
    public void validateAuthToken(String authToken) throws DataAccessError {
        //TODO
    }
    public String getUsername(String authToken) throws DataAccessError {
        //TODO
        return "placeholderUsername";
    }
}
