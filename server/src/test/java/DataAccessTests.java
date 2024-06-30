import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class DataAccessTests {
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;
    public DataAccessTests() {
        userDataAccess = new UserDataAccess();
        gameDataAccess = new GameDataAccess();
    }
    @Test
    public void dbConnection() throws Exception {
        Connection conn = userDataAccess.connectToDB();
        conn.close();
    }
}
