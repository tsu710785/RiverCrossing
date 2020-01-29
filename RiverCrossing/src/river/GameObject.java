package river;

public class GameObject {

    protected String name;
    protected Location location;
    protected String sound;

    GameObject() {
    }

    public static GameObject newGameObject(String name, String sound) {
        GameObject object = new GameObject();
        object.name = name;
        object.sound = sound;
        object.location = Location.START;
        return object;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getSound() {
        return sound;
    }

}
