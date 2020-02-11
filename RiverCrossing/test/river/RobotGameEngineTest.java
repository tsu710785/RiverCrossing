package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class RobotGameEngineTest {

    private RobotGameEngine engine;
    public static final Item SMALLBOT_1 = Item.ITEM_0;
    public static final Item SMALLBOT_2 = Item.ITEM_1;
    public static final Item TALLBOT_1 = Item.ITEM_2;
    public static final Item TALLBOT_2 = Item.ITEM_3;

    @Before
    public void setUp() throws Exception {
        engine = new RobotGameEngine();
    }

    private void transport(Item item) {
        /*
            only take care about item movement
        * */
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);
    }

    private void testGameResult(String s) {
        if (s.equals("win")) {
            Assert.assertFalse(engine.gameIsLost());
            Assert.assertTrue(engine.gameIsWon());
        } else if (s.equals("lost")) {
            Assert.assertTrue(engine.gameIsLost());
            Assert.assertFalse(engine.gameIsWon());
        } else {
            // still ongoing
            Assert.assertFalse(engine.gameIsLost());
            Assert.assertFalse(engine.gameIsWon());
        }

    }

    @Test
    public void TestInitObject() {
        Assert.assertEquals("SMALLBOT_1", engine.getItemLabel(SMALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(Color.blue, engine.getItemColor(SMALLBOT_1));
        Assert.assertEquals("SMALLBOT_2", engine.getItemLabel(SMALLBOT_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_2));
        Assert.assertEquals(Color.blue, engine.getItemColor(SMALLBOT_2));
        Assert.assertEquals("TALLBOT_1", engine.getItemLabel(TALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(Color.pink, engine.getItemColor(TALLBOT_1));
        Assert.assertEquals("TALLBOT_2", engine.getItemLabel(TALLBOT_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_2));
        Assert.assertEquals(Color.pink, engine.getItemColor(TALLBOT_2));
    }

    @Test
    public void TestSmallTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_1));
        transport(SMALLBOT_1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(SMALLBOT_1));
    }

    @Test
    public void TestSmallLoadBoatFail() {
        engine.loadBoat(TALLBOT_1);
        engine.loadBoat(SMALLBOT_1);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(TALLBOT_1));
        engine.unloadBoat(TALLBOT_1);
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(TALLBOT_1));
    }

    @Test
    public void TestTallLoadBoatFail() {
        engine.loadBoat(SMALLBOT_1);
        engine.loadBoat(TALLBOT_1);
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(SMALLBOT_1));
        engine.unloadBoat(SMALLBOT_1);
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(SMALLBOT_1));
    }

    @Test
    public void TestGetBoatLocation() {
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        engine.loadBoat(TALLBOT_1);
        engine.rowBoat();
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
    }

    @Test
    public void TestTallTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_1));
        transport(TALLBOT_1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(TALLBOT_1));
    }

    @Test
    public void TestWinning() {
        /*
        *   ssTT() - (empty)
        * */
        engine.loadBoat(SMALLBOT_1);
        engine.loadBoat(SMALLBOT_2);
        /*
         *   TT(ss) - (empty)
         * */
        engine.rowBoat();
        /*
         *   TT - (ss)(empty)
         * */
        testGameResult("during");
        engine.unloadBoat(SMALLBOT_2);
        /*
         *   TT - (s)s
         * */
        engine.rowBoat();
        /*
         *   TT(s) - s
         * */
        testGameResult("during");
        engine.unloadBoat(SMALLBOT_1);
        engine.loadBoat(TALLBOT_1);
        /*
         *   sT(T) - s
         * */
        engine.rowBoat();
        /*
         *   sT - (T)s
         * */
        testGameResult("during");
        engine.unloadBoat(TALLBOT_1);
        engine.loadBoat(SMALLBOT_2);
        /*
         *   sT - (s)T
         * */
        engine.rowBoat();
        /*
         *   sT(s) - T
         * */
        testGameResult("during");
        engine.loadBoat(SMALLBOT_1);
        /*
         *   T(ss) - T
         * */
        engine.rowBoat();
        testGameResult("during");
        engine.unloadBoat(SMALLBOT_1);
        /*
         *   T - (s)Ts
         * */
        engine.rowBoat();
        /*
         *   T(s) - Ts
         * */
        testGameResult("during");
        engine.unloadBoat(SMALLBOT_2);
        engine.loadBoat(TALLBOT_2);
        /*
         *   s(T) - Ts
         * */
        engine.rowBoat();
        testGameResult("during");
        engine.unloadBoat(TALLBOT_2);
        engine.loadBoat(SMALLBOT_1);
        /*
         *   s - (s)TT
         * */
        engine.rowBoat();
        testGameResult("during");
        engine.loadBoat(SMALLBOT_2);
        /*
         *   (ss) - TT
         * */
        engine.rowBoat();
        testGameResult("during");
        engine.unloadBoat(SMALLBOT_1);
        engine.unloadBoat(SMALLBOT_2);
        testGameResult("win");
    }

    @Test
    public void TestLosing() {

    }

    @Test
    public void TestResetGame() {
        engine.resetGame();
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_2));
    }

    @Test
    public void TestNoLoadFromOtherShore() {
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        engine.loadBoat(SMALLBOT_1);
        engine.loadBoat(SMALLBOT_2);
        engine.rowBoat();
        engine.unloadBoat(SMALLBOT_1);

        engine.rowBoat();
        engine.unloadBoat(SMALLBOT_2);
        engine.loadBoat(TALLBOT_1);
        engine.rowBoat();
        engine.unloadBoat(TALLBOT_1);

        Assert.assertEquals(Location.FINISH, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
        engine.rowBoat();
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
        engine.loadBoat(SMALLBOT_1);
        engine.loadBoat(TALLBOT_1);
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(TALLBOT_1));
        engine.unloadBoat(SMALLBOT_1);
        engine.loadBoat(TALLBOT_1);
        engine.loadBoat(SMALLBOT_1);
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(SMALLBOT_1));
    }
}