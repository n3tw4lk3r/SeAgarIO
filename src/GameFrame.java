import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    public GameFrame(int x, int y, int width, int height, int foodCnt) {
        super();
        setBounds(x, y, width, height);
        setVisible(true);
        gamePanel = new GamePanel(width, height, foodCnt);
        add(gamePanel);
    }
}
