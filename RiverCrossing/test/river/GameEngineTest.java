package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;

public class GameEngineTest {
    private GameEngine engine;
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
        Assert.assertEquals("Farmer", engine.getItemName(Item.FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.FARMER));
        Assert.assertEquals("", engine.getItemSound(Item.FARMER));
        Assert.assertEquals("Wolf", engine.getItemName(Item.WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals("Howl", engine.getItemSound(Item.WOLF));
        Assert.assertEquals("Goose", engine.getItemName(Item.GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals("Honk", engine.getItemSound(Item.GOOSE));
        Assert.assertEquals("Beans", engine.getItemName(Item.BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals("", engine.getItemSound(Item.BEANS));
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        transport(Item.GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.GOOSE));
    }

    @Test
    public void testWinningGame() {

        // transport the goose
        engine.loadBoat(Item.FARMER);
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // pickup beans at start & put beans at end
        transport(Item.BEANS);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //  pickup goose & put goose at start
        transport(Item.GOOSE);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //  pickup wolf & put wolf at end
        transport(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        goBackAlone();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport goose
        transport(Item.GOOSE);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // unload goose, player & win the game
        engine.unloadBoat(Item.FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {

        // transport the goose
        transport(Item.GOOSE);

        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport wolf, make goose, wolf in same place
        goBackAlone();
        transport(Item.WOLF);

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
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(Item.WOLF);
        Location midLoc = engine.getItemLocation(Item.GOOSE);
        Location bottomLoc = engine.getItemLocation(Item.BEANS);
        Location playerLoc = engine.getItemLocation(Item.FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals(midLoc, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals(playerLoc, engine.getItemLocation(Item.FARMER));
    }
}
