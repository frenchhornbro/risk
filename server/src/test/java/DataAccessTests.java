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
}
