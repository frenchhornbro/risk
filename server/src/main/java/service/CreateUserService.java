package service;

import exceptions.DataAccessError;
import exceptions.ServiceError;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUserService extends Service {
    public CreateUserService() throws DataAccessError {

    }

    /**
     * Check if the email and password follow regex, the password and confirmation match, and that the username isn't taken
     */
    public void validateCredentials(String email, String username, String password, String confirm) throws ServiceError, DataAccessError {
        //Credentials are not null
        if (email == null || username == null || password == null || confirm == null) throw new ServiceError("Credentials can't be null", 400);

        //Email follows regex
        Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher emailMatcher = emailRegex.matcher(email);
        if (!emailMatcher.matches()) throw new ServiceError("Invalid email", 400);

        //Password follows regex
        Pattern passwordRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher passwordMatcher = passwordRegex.matcher(password);
        if (!passwordMatcher.matches()) throw new ServiceError("Invalid password", 400);

        //Passwords match
        if (!password.equals(confirm)) throw new ServiceError("Passwords don't match", 400);

        //Username is not already taken
        ArrayList<String> dbResponse = userDataAccess.getUsername(username);
        if (!dbResponse.isEmpty()) throw new ServiceError("Username is taken", 400);
    }

    /**
     * @return authToken
     */
    public String storeCredentials(String email, String username, String password) throws DataAccessError {
        //Generate, store, and return authToken
        String authToken = UUID.randomUUID().toString();
        userDataAccess.createUserData(email, username, password, authToken);
        return authToken;
    }
}
