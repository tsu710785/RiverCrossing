package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class GameEngineTest {
    private GameEngine engine;
    public static final Item BEANS = Item.ITEM_0;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item FARMER = Item.ITEM_3;
    @Before
    public void setUp() throws Exception {
        engine = new GameEngine();
    }
    private void transport(Item item) {
        /*
            only take care about item,
            assume farmer is always on the boat.
        * */
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);
    }
    private void goBackAlone() {
        engine.rowBoat();
    }
    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("Farmer", engine.getItemLabel(FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Color.pink, engine.getItemColor(FARMER));
        Assert.assertEquals("Wolf", engine.getItemLabel(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Color.blue, engine.getItemColor(WOLF));
        Assert.assertEquals("Goose", engine.getItemLabel(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Color.blue, engine.getItemColor(GOOSE));
        Assert.assertEquals("Beans", engine.getItemLabel(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Color.blue, engine.getItemColor(BEANS));
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        transport(GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE));
    }

    @Test
    public void testWinningGame() {

        // transport the goose
        engine.loadBoat(FARMER);
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // pickup beans at start & put beans at end
        transport(BEANS);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //  pickup goose & put goose at start
        transport(GOOSE);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //  pickup wolf & put wolf at end
        transport(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport goose
        transport(GOOSE);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // unload goose, player & win the game
        engine.unloadBoat(FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {

        // transport the goose
        transport(GOOSE);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport wolf, make goose, wolf in same place
        goBackAlone();
        transport(WOLF);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // leave, test lose
        goBackAlone();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(WOLF);
        Location midLoc = engine.getItemLocation(GOOSE);
        Location bottomLoc = engine.getItemLocation(BEANS);
        Location playerLoc = engine.getItemLocation(FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(WOLF);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(WOLF));
        Assert.assertEquals(midLoc, engine.getItemLocation(GOOSE));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(BEANS));
        Assert.assertEquals(playerLoc, engine.getItemLocation(FARMER));
    }
}
