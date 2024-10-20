import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private final int frameWidth;
    private final int frameHeight;
    private Ship ship1;
    private Ship ship2;
    private final Timer timer;
    private final ArrayList<Food> foodArrayList;
    private boolean gameIsActive;
    private BufferedImage backgroundImage;

    public GamePanel(int frameWidth, int frameHeight, int foodCnt) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.foodArrayList = new ArrayList<>();
        this.gameIsActive = true;
        this.ship1 = new Ship(300, 100, 20, 20);
        this.ship2 = new Ship(frameWidth - 300, frameHeight - 100, 20, 20);
        setFocusable(true);
        addKeyListener(this);
        for (int i = 0; i < foodCnt; ++i)
            foodArrayList.add(new Food(frameWidth, frameHeight));
        timer = new Timer(10, this);
        timer.start();
    }

    private void processIntersections() {
        if (ship1.intersectsShip(ship2)) {
            int score1 = ship1.getScore();
            int score2 = ship2.getScore();
            if (score1 > score2) {
                System.out.println("Player 1 won!");
            }
            if (score1 < score2) {
                System.out.println("Player 2 won!");
            }
            if (score1 == score2) {
                System.out.println("Draw..");
            }
            gameIsActive = false;
            if (timer.isRunning())
                timer.stop();
        }
        for (Food food : foodArrayList) {
            if (ship1.intersectsFood(food)) {
                ship1.onFoodIntersection(food);
                food.reposition(frameWidth, frameHeight);
            }
            if (ship2.intersectsFood(food)) {
                ship2.onFoodIntersection(food);
                food.reposition(frameWidth, frameHeight);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            backgroundImage = ImageIO.read(new File("resources/sea1.jpg"));
            Image ship1Image = ImageIO.read(new File("resources/ship1.png"));
            Image ship2Image = ImageIO.read(new File("resources/submarine.png"));
            Image foodImage = ImageIO.read(new File("resources/beer.jpg"));
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            ship1.setImage(ship1Image);
            ship2.setImage(ship2Image);
            for (Food food : foodArrayList)
                food.setImage(foodImage);
        } catch (java.io.IOException e) {
            System.out.println("File read error: " + e.getMessage());
        }
        g.setColor(Color.green);
        g.drawString("Player 1: " + ship1.getScore(), 20, 20);
        g.drawString("Player 2: " + ship2.getScore(), 20,40);
        g.setColor(Color.ORANGE);
        ship1.draw(g);
        ship2.draw(g);
        for (Food food : foodArrayList)
            food.draw(g, frameWidth, frameHeight);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                ship1.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_D:
                ship1.setDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_S:
                ship1.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
                ship1.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_UP:
                ship2.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                ship2.setDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                ship2.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                ship2.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_SPACE:
                if (this.gameIsActive) {
                    if (timer.isRunning())
                        timer.stop();
                    else
                        timer.start();
                }
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (ship1 != null && ship2 != null) {
            ship1.move();
            ship2.move();
            processIntersections();
            repaint();
        }
    }
}
