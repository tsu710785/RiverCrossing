package river;

import java.awt.*;

public class GameObject {

    protected String label;
    protected Location location;
    protected Color color;

    GameObject() {
    }

    public static GameObject newGameObject(String label, Color color) {
        GameObject object = new GameObject();
        object.label = label;
        object.color = color;
        object.location = Location.START;
        return object;
    }

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
