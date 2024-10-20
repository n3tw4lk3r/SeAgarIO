import java.awt.*;

public class Ship {
    private int x;
    private int y;
    private int width;
    private int height;
    private int score;
    private Image image;
    Direction direction;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public Ship(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.score = 0;
        this.image = null;
        this.direction = Direction.HOLD;
    }

    public void draw(Graphics g) {
        if (image == null) {
            g.drawRect(x, y, width, height);
            return;
        }
        g.drawImage(image, x, y, width, height, null);
    }

    public void move() {
        switch (direction) {
            case UP:
                y -= 5;
                break;
            case RIGHT:
                x += 5;
                break;
            case DOWN:
                y += 5;
                break;
            case LEFT:
                x -= 5;
                break;
            default:
                break;
        }
    }

    boolean intersectsFood(Food food) {
        Rectangle shipRect = new Rectangle(x, y, width, height);
        Rectangle foodRect = new Rectangle(food.getX(), food.getY(), food.getWidth(), food.getWidth());
        return shipRect.intersects(foodRect);
    }

    void onFoodIntersection(Food food) {
        width += food.getWidth() / 3;
        height += food.getWidth() / 3;
        score += food.getWidth();
    }

    boolean intersectsShip(Ship ship) {
        Rectangle thisShipRect = new Rectangle(x, y, width, height);
        Rectangle otherShipRect = new Rectangle(ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());
        return thisShipRect.intersects(otherShipRect);
    }
}
