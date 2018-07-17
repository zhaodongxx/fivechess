package fiveshess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class ChessBoard extends JPanel {

    private static int space = 40;
    private static int grids = 15;
    public static int rad = space / 2;
    String message = " ";
    static boolean gameOver = false;

    public int[][] chesses = new int[grids + 1][grids + 1];
    public int currColor = 1;

    private JMenuBar chessJMenuBar = new JMenuBar();
    private JMenu optMenu = new JMenu("选项");
    private JMenu helpMenu = new JMenu("帮助");
    private JMenuItem startMenuItem = new JMenuItem("开始");
    private JMenuItem exitMenuItem = new JMenuItem("退出");
    private JMenuItem aboutMenuItem = new JMenuItem("关于");

    private ActionListener startHandler = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            clearGrids();
            currColor = 1;
            gameOver = false;
            repaint();
        }
    };
    //关于
    private ActionListener aboutHandler = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, " zhaodongxx@outlook.com");
        }
    };

    //退出
    private ActionListener exitHandler = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    //初始化棋盘
    public ChessBoard(int space, int grids) {
        this.space = space;
        this.grids = grids;
        this.rad = space / 2;
        setBackground(new Color(200, 100, 50));
        setSize(760, 760);
        startMenuItem.addActionListener(startHandler);
        exitMenuItem.addActionListener(exitHandler);
        aboutMenuItem.addActionListener(aboutHandler);
        addMouseListener(playChessHandler);

        chessJMenuBar.add(optMenu);
        chessJMenuBar.add(helpMenu);
        optMenu.add(startMenuItem);
        optMenu.add(exitMenuItem);
        helpMenu.add(aboutMenuItem);
    }

    private MouseListener playChessHandler = new MouseAdapter() {


        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if (gameOver == false) {
                if (x <= grids * space && x >= 0 && y <= grids * space && y >= 0) {
                    if (chesses[round(x)][round(y)] == 0) {
                        chesses[round(x)][round(y)] = currColor;
                        repaint();
                        judge(round(x), round(y));
                        System.out.println(round(x) + " " + round(y));
                        turncolor();
                        if (!gameOver) {  //AI
                            AI ai = new AI(currColor, chesses);
                            List<Integer> list = ai.aiPlayer();
                            repaint();
                            judge(list.get(0), list.get(1));
                            turncolor();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "once again");
                    }
                }
            } else {
                //JOptionPane.showMessageDialog(null, "GAME OVER");
            }
        }
    };

    public int round(float a) {
        float f = a / space;
        return Math.round(f);
    }

    public JMenuBar getMenuBar() {
        return chessJMenuBar;
    }

    /**
     * 画棋盘
     *
     * @param g
     */
    public void drawGrids(Graphics g) {
        g.setColor(Color.black);
        for (int x = 1; x <= 15; x++) {
            g.drawLine(space, space * x, space * grids, space * x);
            g.drawLine(space * x, space, space * x, space * grids);
        }

        /**
         * ????????
         */
        g.setColor(Color.BLACK);
        g.fillOval(4 * space - 4, 4 * space - 4, 8, 8);
        g.fillOval(12 * space - 4, 4 * space - 4, 8, 8);
        g.fillOval(8 * space - 4, 8 * space - 4, 8, 8);
        g.fillOval(4 * space - 4, 12 * space - 4, 8, 8);
        g.fillOval(12 * space - 4, 12 * space - 4, 8, 8);
        g.fillOval(4 * space - 4, 8 * space - 4, 8, 8);
        g.fillOval(8 * space - 4, 12 * space - 4, 8, 8);
        g.fillOval(8 * space - 4, 4 * space - 4, 8, 8);
        g.fillOval(12 * space - 4, 8 * space - 4, 8, 8);
    }


    /**
     * @param g
     * @param x
     * @param y
     * @param color
     */
    public void drawChess(Graphics g, int x, int y, int color) {

        g.setColor(color == 1 ? Color.BLACK : Color.WHITE);
        g.fillOval(x * space - rad, y * space - rad, space, space);
    }

    private void clearGrids() {
        for (int i = 0; i <= grids; i++) {
            for (int j = 0; j <= grids; j++) {
                chesses[i][j] = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        try {
            String basePath = System.getProperty("user.dir");
            System.out.println(basePath + "\\background.jpg");
            BufferedImage bi = ImageIO.read(new File(basePath + "\\background.jpg"));
            g.drawImage(bi, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setFont(new Font("mobang v1.0.1", Font.BOLD, 25));
        g.drawString("-" + message, 210, 26);

        drawGrids(g);
        for (int i = 1; i <= grids; i++) {
            for (int j = 1; j <= grids; j++) {
                if (chesses[i][j] != 0)
                    drawChess(g, i, j, chesses[i][j]);
            }
        }
    }

    //判断胜负
    private void judge(int x, int y) {
        int count = 1;
        //Y
        for (int i = 1; i <= 4; i++) {
            if (y + i < grids + 1 && currColor == chesses[x][y + i]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (y - i > 0 && currColor == chesses[x][y - i]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }
        //X
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < grids + 1 && currColor == chesses[x + i][y]) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (x - i > 0 && currColor == chesses[x - i][y]) {
                count++;

            } else {
                break;
            }
        }
        if (count >= 5) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }

        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < grids + 1 && y + i < grids + 1 && currColor == chesses[x + i][y + i]) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (x - i > 0 && y - i > 0 && currColor == chesses[x - i][y - i]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }

        //  "\"
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x - i > 0 && y + i < grids + 1 && currColor == chesses[x - i][y + i]) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (x + i < grids + 1 && y - i > 0 && currColor == chesses[x + i][y - i]) {
                count++;

            } else {
                break;
            }
        }
        if (count >= 5) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }
    }

    private void turncolor() {
        if (currColor == 1) {
            currColor = 2;
        } else {
            currColor = 1;
        }
    }
}