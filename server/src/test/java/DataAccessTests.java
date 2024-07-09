import dataAccess.GameDataAccess;
import dataAccess.UserDataAccess;
import exceptions.DataAccessError;
import game.GameData;
import game.PlayerData;
import org.junit.jupiter.api.*;
import pieces.*;

import java.util.HashMap;

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

    @Test
    public void printGameDataToString() {
        GameData gameData = new GameData();
        gameData.setPhase(GameData.phaseType.Purchase);
        gameData.setPlayerTurn("thisPlayerTurn");
        PlayerData player1 = new PlayerData("username1");
        player1.setPurchasedPieces(
                new HashMap<>() {{
                    put(new Infantry(), 3);
                    put(new Tank(), 2);
                }});
        player1.setPurchasedPieces(new HashMap<>(){});
        Assertions.assertDoesNotThrow(() -> gameData.updatePlayer(player1, true));
        Assertions.assertDoesNotThrow(() -> gameData.updatePlayer(new PlayerData("username2"), true));
        Assertions.assertDoesNotThrow(() -> gameData.updatePlayer(new PlayerData("username3"), true));
        Assertions.assertDoesNotThrow(() -> gameData.updatePlayer(new PlayerData("username4"), true));
        System.out.println(gameData);
    }

    //TODO: Test what happens if I pass more or fewer than required parameters into queryDB() and updateDB()
}
