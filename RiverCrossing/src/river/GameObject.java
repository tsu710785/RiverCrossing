package river;

import java.awt.*;

public class GameObject {

    protected String label;
    protected Location location;
    protected Color color;

    GameObject(String label, Color color, Location location) {
        this.label = label;
        this.color = color;
        this.location = location;
    }

//    public static GameObject newGameObject(String label, Color color, Location location) {
//        GameObject object = new GameObject();
//        object.label = label;
//        object.color = color;
//        object.location = location;
//        return object;
//    }

    public String getLabel() {
        return label;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Color getColor() {
        return color;
    }

}
