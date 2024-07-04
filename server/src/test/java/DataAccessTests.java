import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.DataAccessError;
import org.junit.jupiter.api.*;

public class DataAccessTests {
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;
    public DataAccessTests() throws DataAccessError {
        userDataAccess = new UserDataAccess();
        gameDataAccess = new GameDataAccess();
    }
    @Test
    public void dbConnection() {

    }

    @Test
    public void tooLongUsername() {

    }

    @Test
    public void invalidAuth() {
        DataAccessError dataAccessError = Assertions.assertThrows(DataAccessError.class, () -> userDataAccess.validateAuthToken("asdflkj"));
        System.out.println(dataAccessError.getErrorCode() + " " + dataAccessError.getMessage());
    }

    @Test
    public void validAuth() {

    }

    //TODO: Test what happens if I pass more or fewer than required parameters into queryDB() and updateDB()
}
