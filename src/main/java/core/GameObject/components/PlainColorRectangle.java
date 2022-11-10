package core.GameObject.components;

import java.awt.Color;
import java.awt.Graphics2D;

public class PlainColorRectangle extends Component {
    private int x,y,width,height;
    private Color color;

    public PlainColorRectangle(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.setColor(color);
        g2D.fillRect(x,y,width,height);
    }
}
