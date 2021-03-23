package fiveshess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChessBoard extends JPanel {

    private int space = 40;
    public static int GRIDS_NUM = 15;
    public int rad;
    String message = " ";
    static boolean gameOver = false;

    public int[][] chesses = new int[GRIDS_NUM + 1][GRIDS_NUM + 1];
    public ChessmanEnum chessman = ChessmanEnum.BLACK_CHESS;

    private JMenuBar chessJMenuBar = new JMenuBar();
    private JMenu optMenu = new JMenu("选项");
    private JMenu helpMenu = new JMenu("帮助");
    private JMenuItem startMenuItem = new JMenuItem("开始");
    private JMenuItem exitMenuItem = new JMenuItem("退出");
    private JMenuItem aboutMenuItem = new JMenuItem("关于");

    /**
     * 棋盘构造方法
     */
    public ChessBoard() {
        this.rad = space / 2;
        setBackground(new Color(200, 100, 50));
        setSize(760, 760);

        // 按钮-开始
        startMenuItem.addActionListener(e -> {
            clearGrids();
            chessman = ChessmanEnum.BLACK_CHESS;
            gameOver = false;
            repaint();
        });
        // 按钮-退出
        exitMenuItem.addActionListener(e -> System.exit(0));
        // 按钮-关于
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "zhaodongxx@outlook.com"));
        addMouseListener(playChessHandler);

        chessJMenuBar.add(optMenu);
        chessJMenuBar.add(helpMenu);
        optMenu.add(startMenuItem);
        optMenu.add(exitMenuItem);
        helpMenu.add(aboutMenuItem);
    }

    private MouseListener playChessHandler = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            // 游戏继续
            boolean gameContinues = !gameOver;
            boolean pressedWithinTheScope = x <= GRIDS_NUM * space && x >= 0 && y <= GRIDS_NUM * space && y >= 0;

            if (gameContinues) {
                if (pressedWithinTheScope) {
                    if (chesses[round(x)][round(y)] == 0) {
                        chesses[round(x)][round(y)] = chessman.getCode();
                        repaint();
                        judge(round(x), round(y));
                        // AI
                        if (gameContinues) {
                            AI ai = new AI(chessman.getCode(), chesses);
                            List<Integer> list = ai.aiPlayer();
                            repaint();
                            judge(list.get(0), list.get(1));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "再来一次");
                    }
                }
            } else {
                // JOptionPane.showMessageDialog(null, "GAME OVER");
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

        //画线
        for (int x = 1; x <= 15; x++) {
            g.drawLine(space, space * x, space * GRIDS_NUM, space * x);
            g.drawLine(space * x, space, space * x, space * GRIDS_NUM);
        }

        //画点
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
        for (int i = 0; i <= GRIDS_NUM; i++) {
            for (int j = 0; j <= GRIDS_NUM; j++) {
                chesses[i][j] = 0;
            }
        }
    }

    @Override
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
        for (int i = 1; i <= GRIDS_NUM; i++) {
            for (int j = 1; j <= GRIDS_NUM; j++) {
                if (chesses[i][j] != 0) {
                    drawChess(g, i, j, chesses[i][j]);
                }
            }
        }
    }

    /**
     * 判断胜负
     *
     * @param x
     * @param y
     */
    private void judge(int x, int y) {
        boolean win = Judge.win(chesses, chessman.getCode(), x, y);
        if (win) {
            gameOver = true;
            StringBuilder sb = new StringBuilder();
            sb.append("GAME OVER~");
            sb.append(chessman.getName() + " 获得胜利");

            JOptionPane.showMessageDialog(null, sb.toString());
        }
        // 还手
        chessman = chessman.next();
    }
}