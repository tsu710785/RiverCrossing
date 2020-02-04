package river;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical interface for the River application
 * 
 * @author Gregory Kulczycki
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================

    private final Rectangle leftFarmerRect = new Rectangle(80, 215, 50, 50);
    private final Rectangle leftWolfRect = new Rectangle(20, 215, 50, 50);
    private final Rectangle leftGooseRect = new Rectangle(20, 275, 50, 50);
    private final Rectangle leftBeansRect = new Rectangle(80, 275, 50, 50);
    private final Rectangle leftBoatRect = new Rectangle(140, 275, 110, 50);
    private final Rectangle leftBoatDriverRect = new Rectangle(140, 215, 50, 50);
    private final Rectangle leftBoatPassengerRect = new Rectangle(200, 215, 50, 50);

    private final Rectangle rightFarmerRect = new Rectangle(730, 215, 50, 50);
    private final Rectangle rightWolfRect = new Rectangle(670, 215, 50, 50);
    private final Rectangle rightGooseRect = new Rectangle(670, 275, 50, 50);
    private final Rectangle rightBeansRect = new Rectangle(730, 275, 50, 50);
    private final Rectangle rightBoatRect = new Rectangle(550, 275, 110, 50);
    private final Rectangle rightBoatDriverRect = new Rectangle(550, 215, 50, 50);
    private final Rectangle rightBoatPassengerRect = new Rectangle(610, 215, 50, 50);

    private final Rectangle restartButtonRect = new Rectangle(350, 120, 100, 30);

    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private boolean restart = false;

    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {

        engine = new GameEngine();
        addMouseListener(this);
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        paintItem(g, Item.ITEM_0);
        paintItem(g, Item.ITEM_1);
        paintItem(g, Item.ITEM_2);
        paintItem(g, Item.ITEM_3);
        paintBoat(g);
        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        paintMessage(message, g);
        if (restart) {
            paintRestartButton(g);
        }

    }
    private void paintItem(Graphics g, Item item) {
        Color color;
        String label = "";
        if (item == Item.ITEM_3) {
            color = Color.MAGENTA;
        } else {
            color = Color.CYAN;
            if (item == Item.ITEM_2) {
                label = "W";
            } else if (item == Item.ITEM_1) {
                label = "G";
            } else { // item == Item.ITEM_0
                label = "B";
            }
        }
        Rectangle rectangle = getItemRectangle(item);
        paintRectangle(g, color, label, rectangle);
    }
    private void paintBoat(Graphics g) {
        Rectangle rectangle = getBoatRectangle();
        paintRectangle(g, Color.ORANGE, "", rectangle);
    }
    private Rectangle getItemRectangle(Item item) {
        if (engine.getItemLocation(item) == Location.START) {
            if (item == Item.ITEM_3) {
                return leftFarmerRect;
            } else if (item == Item.ITEM_2) {
                return leftWolfRect;
            } else if (item == Item.ITEM_1) {
                return leftGooseRect;
            } else {
                // ITEM_0
                return leftBeansRect;
            }
        } else if (engine.getItemLocation(item) == Location.FINISH) {
            if (item == Item.ITEM_3) {
                return rightFarmerRect;
            } else if (item == Item.ITEM_2) {
                return rightWolfRect;
            } else if (item == Item.ITEM_1) {
                return rightGooseRect;
            } else {
                // ITEM_0
                return rightBeansRect;
            }
        } else {
            // BOAT
            if (engine.getItemCurrentLocation() == Location.START) {
                if (item == Item.ITEM_3) {
                    return leftBoatDriverRect;
                } else {
                    return leftBoatPassengerRect;
                }

            } else {
                if (item == Item.ITEM_3) {
                    return rightBoatDriverRect;
                } else {
                    return rightBoatPassengerRect;
                }
            }
        }
    }

    private Rectangle getBoatRectangle() {
        if (engine.getItemCurrentLocation() == Location.START) {
            return leftBoatRect;
        } else {
            return rightBoatRect;
        }
    }

    public void paintStringInRectangle(String str, int x, int y, int width, int height, Graphics g) {
        g.setColor(Color.BLACK);
        int fontSize = (height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = x + width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = y + height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintRestartButton(Graphics g) {
        g.setColor(Color.BLACK);
        paintBorder(restartButtonRect, 3, g);
        g.setColor(Color.PINK);
        paintRectangle(restartButtonRect, g);
        paintStringInRectangle("Restart", restartButtonRect.x, restartButtonRect.y, restartButtonRect.width,
                restartButtonRect.height, g);
    }

    public void paintBorder(Rectangle r, int thickness, Graphics g) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }

    public void paintRectangle(Rectangle r, Graphics g) {
        g.fillRect(r.x, r.y, r.width, r.height);
    }
    private void paintRectangle(Graphics g, Color color, String label, Rectangle rect) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        paintStringInRectangle(label, rect.x, rect.y, rect.width, rect.height, g);
    }

        // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

        if (restart) {
            if (this.restartButtonRect.contains(e.getPoint())) {
                engine.resetGame();
                restart = false;
                repaint();
            }
            return;
        }

        if (leftFarmerRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_3) == Location.START) {
                engine.loadBoat(Item.ITEM_3);
            }
        } else if (leftWolfRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_2) == Location.START) {
                engine.loadBoat(Item.ITEM_2);
            }
        } else if (leftGooseRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_1) == Location.START) {
                engine.loadBoat(Item.ITEM_1);
            }
        } else if (leftBeansRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_0) == Location.START) {
                engine.loadBoat(Item.ITEM_0);
            }
        } else if (leftBoatDriverRect.contains(e.getPoint())) {
            if (engine.getItemCurrentLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.unloadBoat(Item.ITEM_3);
            }
        } else if (leftBoatPassengerRect.contains(e.getPoint())) {
            if (engine.getItemCurrentLocation() == Location.START) {
                if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_2);
                } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_1);
                } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_0);
                }
            }
        } else if (leftBoatRect.contains(e.getPoint())) {
            if (engine.getItemCurrentLocation() == Location.START && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.rowBoat();
            }
        } else if (rightFarmerRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_3) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_3);
            }
        } else if (rightWolfRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_2) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_2);
            }
        } else if (rightGooseRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_1) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_1);
            }
        } else if (rightBeansRect.contains(e.getPoint())) {
            if (engine.getItemLocation(Item.ITEM_0) == Location.FINISH) {
                engine.loadBoat(Item.ITEM_0);
            }
        } else if (rightBoatDriverRect.contains(e.getPoint())) {
            if (engine.getItemCurrentLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.unloadBoat(Item.ITEM_3);
            }
        } else if (rightBoatPassengerRect.contains(e.getPoint())) {
            if (engine.getItemCurrentLocation() == Location.FINISH) {
                if (engine.getItemLocation(Item.ITEM_2) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_2);
                } else if (engine.getItemLocation(Item.ITEM_1) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_1);
                } else if (engine.getItemLocation(Item.ITEM_0) == Location.BOAT) {
                    engine.unloadBoat(Item.ITEM_0);
                }
            }
        } else if (rightBoatRect.contains(e.getPoint())) {
            if (engine.getItemCurrentLocation() == Location.FINISH && engine.getItemLocation(Item.ITEM_3) == Location.BOAT) {
                engine.rowBoat();
            }
        } else {
            return;
        }
        repaint();
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
