package river;

public class GameEngine {

    public enum Item {
        TOP, MID, BOTTOM, PLAYER;
    }

    private GameObject wolf;
    private GameObject goose;
    private GameObject beans;
    private GameObject farmer;
    private Location currentLocation;

    public GameEngine() {
        wolf = GameObject.newGameObject("Wolf", "Howl");
        goose = GameObject.newGameObject("Goose", "Honk");
        beans = GameObject.newGameObject("Beans", "");
        farmer = GameObject.newGameObject("Farmer", "");
        currentLocation = Location.START;
    }

    public String getName(Item id) {
        switch (id) {
        case TOP:
            return wolf.getName();
        case MID:
            return goose.getName();
        case BOTTOM:
            return beans.getName();
        default:
            return farmer.getName();
        }
    }

    public Location getLocation(Item id) {
        switch (id) {
        case TOP:
            return wolf.getLocation();
        case MID:
            return goose.getLocation();
        case BOTTOM:
            return beans.getLocation();
        default:
            return farmer.getLocation();
        }
    }

    public String getSound(Item id) {
        switch (id) {
        case TOP:
            return wolf.getSound();
        case MID:
            return goose.getSound();
        case BOTTOM:
            return beans.getSound();
        default:
            return farmer.getSound();
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void loadBoat(Item id) {

        switch (id) {
        case TOP:
            if (wolf.getLocation() == currentLocation && goose.getLocation() != Location.BOAT
                    && beans.getLocation() != Location.BOAT) {
                wolf.setLocation(Location.BOAT);
            }
            break;
        case MID:
            if (goose.getLocation() == currentLocation && wolf.getLocation() != Location.BOAT
                    && beans.getLocation() != Location.BOAT) {
                goose.setLocation(Location.BOAT);
            }
            break;
        case BOTTOM:
            if (beans.getLocation() == currentLocation && wolf.getLocation() != Location.BOAT
                    && goose.getLocation() != Location.BOAT) {
                beans.setLocation(Location.BOAT);
            }
            break;
        case PLAYER:
            if (farmer.getLocation() == currentLocation) {
                farmer.setLocation(Location.BOAT);
            }
        default: // do nothing
        }
    }

    public void unloadBoat(Item id) {
        switch (id) {
        case TOP:
            if (wolf.getLocation() == Location.BOAT) {
                wolf.setLocation(currentLocation);
            }
            break;
        case MID:
            if (goose.getLocation() == Location.BOAT) {
                goose.setLocation(currentLocation);
            }
            break;
        case BOTTOM:
            if (beans.getLocation() == Location.BOAT) {
                beans.setLocation(currentLocation);
            }
            break;
        case PLAYER:
            if (farmer.getLocation() == Location.BOAT) {
                farmer.setLocation(currentLocation);
            }
        default: // do nothing
        }
    }

    public void rowBoat() {
        assert (currentLocation != Location.BOAT);
        if (currentLocation == Location.START) {
            currentLocation = Location.FINISH;
        } else {
            currentLocation = Location.START;
        }
    }

    public boolean gameIsWon() {
        return wolf.getLocation() == Location.FINISH && goose.getLocation() == Location.FINISH
                && beans.getLocation() == Location.FINISH && farmer.getLocation() == Location.FINISH;
    }

    public boolean gameIsLost() {
        if (goose.getLocation() == Location.BOAT) {
            return false;
        }
        if (goose.getLocation() == farmer.getLocation()) {
            return false;
        }
        if (goose.getLocation() == currentLocation) {
            return false;
        }
        if (goose.getLocation() == wolf.getLocation()) {
            return true;
        }
        if (goose.getLocation() == beans.getLocation()) {
            return true;
        }
        return false;
    }

    public void resetGame() {
        wolf.setLocation(Location.START);
        goose.setLocation(Location.START);
        beans.setLocation(Location.START);
        farmer.setLocation(Location.START);
        currentLocation = Location.START;
    }

}
