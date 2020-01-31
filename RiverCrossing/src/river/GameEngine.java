package river;

import java.util.HashMap;

public class GameEngine {

    public static final Item BEANS = Item.ITEM_0;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;
    final private HashMap<Item, GameObject> map;

    public GameEngine() {
        map = new HashMap<>();
        map.put(WOLF, GameObject.newGameObject("Wolf", "Howl"));
        map.put(GOOSE, GameObject.newGameObject("Goose", "Honk"));
        map.put(BEANS, GameObject.newGameObject("Beans", ""));
        map.put(FARMER, GameObject.newGameObject("Farmer", ""));
        boatLocation = Location.START;

    }

    public String getItemName(Item id) {
        return map.get(id).getName();
    }

    public Location getItemLocation(Item id) {
        return map.get(id).getLocation();
    }

    public String getItemSound(Item id) {
        return map.get(id).getSound();
    }

    public Location getItemCurrentLocation() {
        return boatLocation;
    }

    public void loadBoat(Item id) {
        if (map.get(id).getLocation() == boatLocation) {
            for (Item key: map.keySet()) {
                if(key != id && map.get(key).getLocation() !=Location.BOAT) {
                    map.get(id).setLocation(Location.BOAT);
                }
            }
        }
    }

    public void unloadBoat(Item id) {
        if (map.get(id).getLocation() == Location.BOAT) {
            map.get(id).setLocation(boatLocation);
        }
    }

    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    public boolean gameIsWon() {
        for (GameObject o: map.values()) {
            if (o.getLocation() != Location.FINISH) return false;
        }
        return true;
    }

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

    public void resetGame() {
        for (GameObject o: map.values()) {
            o.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }

}
