import java.awt.*;
import java.util.Random;

public class Food {
    private int x;
    private int y;
    private int width;
    private Image image;
    Random random;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    Food(int frameWidth, int frameHeight) {
        this.random = new Random();
        this.x = random.nextInt(0, frameWidth - 50);
        this.y = random.nextInt(0, frameHeight - 50);
        this.width = random.nextInt(15, 45);
        this.image = null;
    }

    public void draw(Graphics g, int frameWidth, int frameHeight) {
        if (this.image == null) {
            g.fillOval(x, y, width, width);
            return;
        }
        g.drawImage(image, x, y, width, width, null);
    }

    public void reposition(int frameWidth, int frameHeight) {
        x = random.nextInt(0, frameWidth - 50);
        y = random.nextInt(0, frameHeight - 50);
        width = random.nextInt(15, 45);
    }
}
