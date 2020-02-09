package river;

import java.awt.*;
import java.util.HashMap;

public class RobotGameEngine implements GameEngine {
    public static final Item SMALLBOT_1 = Item.ITEM_0;
    public static final Item SMALLBOT_2 = Item.ITEM_2;
    public static final Item TALLBOT_1 = Item.ITEM_1;
    public static final Item TALLBOT_2 = Item.ITEM_3;
    private Location boatLocation;
    final private HashMap<Item, GameObject> map;

    public RobotGameEngine() {
        map = new HashMap<>();
        map.put(SMALLBOT_1, GameObject.newGameObject("SMALLBOT_1", Color.blue));
        map.put(SMALLBOT_2, GameObject.newGameObject("SMALLBOT_2", Color.blue));
        map.put(TALLBOT_1, GameObject.newGameObject("TALLBOT_1", Color.pink));
        map.put(TALLBOT_2, GameObject.newGameObject("TALLBOT_2",  Color.pink));
        boatLocation = Location.START;

    }

    @Override
    public String getItemLabel(Item id) {
        return map.get(id).getLabel();
    }

    @Override
    public Location getItemLocation(Item id) {
        return map.get(id).getLocation();
    }

    @Override
    public Color getItemColor(Item id) {
        return map.get(id).getColor();
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item id) {
        if (id == Item.ITEM_3) {
            if (map.get(id).getLocation() == boatLocation) {
                map.get(id).setLocation(Location.BOAT);
            }
        } else {
            if (map.get(id).getLocation() == boatLocation) {
                for (Item key: map.keySet()) {
                    if(key != id && key != Item.ITEM_3 && map.get(key).getLocation() == Location.BOAT) {
                        return;
                    }
                }
                map.get(id).setLocation(Location.BOAT);
            }
        }
    }

    @Override
    public void unloadBoat(Item id) {
        if (map.get(id).getLocation() == Location.BOAT) {
            map.get(id).setLocation(boatLocation);
        }
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        for (GameObject o: map.values()) {
            if (o.getLocation() != Location.FINISH) return false;
        }
        return true;
    }

    @Override
    public boolean gameIsLost() {
        if (map.get(Item.ITEM_1).getLocation() == Location.BOAT
                || map.get(Item.ITEM_1).getLocation() == map.get(Item.ITEM_3).getLocation()
                || map.get(Item.ITEM_1).getLocation() == boatLocation) {
            return false;
        }
        if (map.get(Item.ITEM_1).getLocation() == map.get(Item.ITEM_2).getLocation()
                || map.get(Item.ITEM_1).getLocation() == map.get(Item.ITEM_0).getLocation()) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        for (GameObject o: map.values()) {
            o.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }
}
