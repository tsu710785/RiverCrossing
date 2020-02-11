package river;

import java.awt.*;
import java.util.HashMap;

public class FarmerGameEngine implements GameEngine {

    public static final Item BEANS = Item.ITEM_0;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;
    private int capacity;
    private boolean hasDriver;
    final private HashMap<Item, GameObject> map;

    public FarmerGameEngine() {
        map = new HashMap<>();
        map.put(WOLF, new GameObject("Wolf", Color.blue, Location.START));
        map.put(GOOSE, new GameObject("Goose", Color.blue, Location.START));
        map.put(BEANS, new GameObject("Beans", Color.blue, Location.START));
        map.put(FARMER, new GameObject("Farmer",  Color.pink, Location.START));
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
        if (map.get(id).getLocation() == boatLocation) {
            map.get(id).setLocation(Location.BOAT);
            capacity++;
            if (id == Item.ITEM_3) hasDriver = true;
        }
    }

    @Override
    public void unloadBoat(Item id) {
        if (map.get(id).getLocation() == Location.BOAT) {
            map.get(id).setLocation(boatLocation);
            if (id == Item.ITEM_3) hasDriver = false;
            capacity--;
        }
    }

    @Override
    public void rowBoat() {
        if (map.get(FARMER).getLocation() != Location.BOAT) return;
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
