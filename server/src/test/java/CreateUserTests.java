import exceptions.DataAccessError;
import org.junit.jupiter.api.*;
import service.Service;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateUserTests {
    private final Service service;
    public CreateUserTests() throws DataAccessError {
        service = new Service();
    }

    @Test
    public void checkEmails() {
        ArrayList<String> goodEmails = new ArrayList<>(Arrays.asList(
                "taco@taco.com",
                "bob@bob.bob",
                "gmailUser@gmail.com",
                "1@1.edu",
                "cosmo@byu.edu"
        ));
        for (int i = 0; i < goodEmails.size(); i++) {
            String email = goodEmails.get(i);
            String username = "user" + i;
            String password = "th1s1sAPassword!";
            Assertions.assertDoesNotThrow(() -> service.validateCredentials(email, username, password, password));
        }
    }
}
