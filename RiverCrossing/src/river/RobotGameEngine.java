package river;

import java.awt.*;
import java.util.HashMap;

public class RobotGameEngine implements GameEngine {
    public static final Item SMALLBOT_1 = Item.ITEM_0;
    public static final Item SMALLBOT_2 = Item.ITEM_1;
    public static final Item TALLBOT_1 = Item.ITEM_2;
    public static final Item TALLBOT_2 = Item.ITEM_3;
    private Location boatLocation;
    private int capacity;
    private boolean hasDriver;
    final private HashMap<Item, GameObject> map;

    public RobotGameEngine() {
        map = new HashMap<>();
        map.put(SMALLBOT_1, new GameObject("SMALLBOT_1", Color.blue, Location.START));
        map.put(SMALLBOT_2, new GameObject("SMALLBOT_2", Color.blue, Location.START));
        map.put(TALLBOT_1, new GameObject("TALLBOT_1", Color.pink, Location.START));
        map.put(TALLBOT_2, new GameObject("TALLBOT_2",  Color.pink, Location.START));
        boatLocation = Location.START;
        capacity = 0;
        hasDriver = false;
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
        // check full before load
        if (capacity >= 2) return;
        if (id == Item.ITEM_2 || id == Item.ITEM_3) {
            if (capacity >= 1) return;
        }
        if (map.get(id).getLocation() == boatLocation) {
            map.get(id).setLocation(Location.BOAT);
            if (id == Item.ITEM_2 || id == Item.ITEM_3) capacity += 2;
            else capacity++;
        }
    }

    @Override
    public void unloadBoat(Item id) {
        if (map.get(id).getLocation() == Location.BOAT) {
            map.get(id).setLocation(boatLocation);
            if (id == Item.ITEM_2 || id == Item.ITEM_3) capacity -= 2;
            else capacity--;
        }
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        for (Item key: map.keySet()) {
            if(map.get(key).getLocation() == Location.BOAT) {
                if (boatLocation == Location.START) {
                    boatLocation = Location.FINISH;
                } else {
                    boatLocation = Location.START;
                }
                break;
            }
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
