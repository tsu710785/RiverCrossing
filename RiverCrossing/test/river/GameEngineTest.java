package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;
import river.GameEngine.Location;

public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testObjects() {
        GameObject farmer = GameObject.newGameObject("Farmer", "");
        Assert.assertEquals("Farmer", farmer.getName());
        Assert.assertEquals(Location.START, farmer.getLocation());
        Assert.assertEquals("", farmer.getSound());
        GameObject wolf = GameObject.newGameObject("Wolf", "Howl");
        Assert.assertEquals("Wolf", wolf.getName());
        Assert.assertEquals(Location.START, wolf.getLocation());
        Assert.assertEquals("Howl", wolf.getSound());
        GameObject goose = GameObject.newGameObject("Goose", "Honk");
        Assert.assertEquals("Goose", goose.getName());
        Assert.assertEquals(Location.START, goose.getLocation());
        Assert.assertEquals("Honk", goose.getSound());
        GameObject beans = GameObject.newGameObject("Beans", "");
        Assert.assertEquals("Beans", beans.getName());
        Assert.assertEquals(Location.START, beans.getLocation());
        Assert.assertEquals("", beans.getSound());
    }

    @Test
    public void testMidTransport() {
        GameEngine engine = new GameEngine();
        Assert.assertEquals(Location.START, engine.getLocation(Item.MID));
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
        Assert.assertEquals(Location.FINISH, engine.getLocation(Item.MID));
    }

    @Test
    public void testWinningGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.MID);
        engine.loadBoat(Item.PLAYER);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // pickup beans at start
        engine.loadBoat(Item.BOTTOM);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // put beans at end & pickup goose
        engine.unloadBoat(Item.BOTTOM);
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // put goose at start & pickup wolf
        engine.unloadBoat(Item.MID);
        engine.loadBoat(Item.TOP);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // put wolf at end
        engine.unloadBoat(Item.TOP);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // pickup goose
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // unload goose, player & win the game
        engine.unloadBoat(Item.MID);
        engine.unloadBoat(Item.PLAYER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // pickup wolf
        engine.rowBoat();
        engine.loadBoat(Item.TOP);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // put wolf, goose together & leave, test lose
        engine.unloadBoat(Item.TOP);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        GameEngine.Location topLoc = engine.getLocation(Item.TOP);
        GameEngine.Location midLoc = engine.getLocation(Item.MID);
        GameEngine.Location bottomLoc = engine.getLocation(Item.BOTTOM);
        GameEngine.Location playerLoc = engine.getLocation(Item.PLAYER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.TOP);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getLocation(Item.TOP));
        Assert.assertEquals(midLoc, engine.getLocation(Item.MID));
        Assert.assertEquals(bottomLoc, engine.getLocation(Item.BOTTOM));
        Assert.assertEquals(playerLoc, engine.getLocation(Item.PLAYER));
    }
}
