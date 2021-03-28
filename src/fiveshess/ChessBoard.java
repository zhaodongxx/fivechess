package fiveshess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static javax.swing.SwingConstants.CENTER;

public class ChessBoard extends JPanel {

    private int space = 60;
    public static int GRIDS_NUM = 15;
    public int rad;
    String message = " ";
    static boolean gameOver = false;

    public int[][] chesses = new int[GRIDS_NUM][GRIDS_NUM];
    public HashMap<String, String> playOrder = new HashMap<>();
    public ChessmanEnum chessman = ChessmanEnum.BLACK_CHESS;
    Font font = new Font("雅黑", Font.BOLD, 25);
    JLabel jLabel;

    public static void main(String arg[]) {
        ChessBoard chessPanel = new ChessBoard();
    }

    /**
     * 棋盘构造方法
     */
    public ChessBoard() {
        initFont();
        initJFrame();
    }


    private MouseListener playChessHandler = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            // 游戏继续
            boolean pressedWithinTheScope = x <= GRIDS_NUM * space && x >= 0 && y <= GRIDS_NUM * space && y >= 0;

            if (!gameOver) {
                if (pressedWithinTheScope) {
                    if (chesses[round(x)][round(y)] == 0) {
                        chesses[round(x)][round(y)] = chessman.getCode();
                        playOrder.put(round(x) + "-" + round(y), String.valueOf(playOrder.size() + 1));
                        repaint();
                        judge(new Point(round(x), round(y)));
                        // AI
                        if (!gameOver) {
                            Point point = new AI(chesses).aiPlayer(chessman.next());
                            chesses[point.getX()][point.getY()] = chessman.getCode();
                            playOrder.put(point.getX() + "-" + point.getY(), String.valueOf(playOrder.size() + 1));
                            repaint();
                            judge(point);
                        }
                    }
                }
            } else {
                // JOptionPane.showMessageDialog(null, "GAME OVER");
            }
        }
    };

    public int round(float a) {
        if (a < space) {
            a = space;
        }
        float f = a / space;
        return Math.round(f) - 1;
    }

    private JFrame initJFrame() {
        //初始化一个界面,并设置标题大小等属性
        JFrame jf = new JFrame();
        jf.setTitle("五子棋");
        jf.setSize(1160, 1040);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new BorderLayout());//设置顶级容器JFrame为框架布局
        jf.setResizable(false);
        jf.setJMenuBar(buildMenuBar());

        //实现左边的界面，添加到框架布局的中间部分
        this.setPreferredSize(new Dimension(960, 0));//设置下棋界面的大小
        this.setBackground(Color.LIGHT_GRAY);//设置下棋界面的颜色
        //这里的话直接把左边的画板添加上去，指明是在框架布局的中间版块
        //若放在其他版块会有一些小问题
        jf.add(this, BorderLayout.CENTER);//添加到框架布局的中间部分

        //实现右边的JPanel容器界面
        JPanel jp = new JPanel(new FlowLayout(CENTER, 5, 50));//设置JPanel为流式布局
        jp.setPreferredSize(new Dimension(200, 0));//设置JPanel的大小
        jp.setBackground(Color.white);//设置右边的界面颜色为白色
        jf.add(jp, BorderLayout.EAST);//添加到框架布局的东边部分

        //接下来我们需要把按钮等组件依次加到那个JPanel上面
        //设置按钮数组
        JButton button = new JButton("开始游戏");
        button.setFont(font);
        button.setPreferredSize(new Dimension(180, 40));
        button.addActionListener(e -> init());
        jp.add(button);
        jLabel = new JLabel();
        jp.add(jLabel);

        jf.setVisible(true);
        this.rad = (space / 2) - 5;
        setBackground(new Color(200, 100, 50));

        return jf;
    }

    public void init() {
        clearGrids();
        playOrder.clear();
        chessman = ChessmanEnum.BLACK_CHESS;
        gameOver = false;
        repaint();
        jLabel.setText("");
    }

    /**
     * 设置全局的字体
     */
    public void initFont() {
        UIManager.put("Button.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("ColorChooser.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("ComboBoxItem.font", font);
        UIManager.put("InternalFrame.titleFont", font);
        UIManager.put("Label.font", font);
        UIManager.put("List.font", font);
        UIManager.put("MenuBar.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("RadioButtonMenuItem.font", font);
        UIManager.put("CheckBoxMenuItem.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("OptionPane.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("ProgressBar.font", font);
        UIManager.put("ScrollPane.font", font);
        UIManager.put("Viewport", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("PasswordFiled.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("TextPane.font", font);
        UIManager.put("EditorPane.font", font);
        UIManager.put("TitledBorder.font", font);
        UIManager.put("ToolBar.font", font);
        UIManager.put("ToolTip.font", font);
        UIManager.put("Tree.font", font);
    }

    public JMenuBar buildMenuBar() {
        UIManager.put("Label.font", font);
        JMenuBar chessJMenuBar = new JMenuBar();
        chessJMenuBar.setFont(font);
        JMenu optMenu = new JMenu("选项");
        optMenu.setFont(font);
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem startMenuItem = new JMenuItem("开始");
        JMenuItem exitMenuItem = new JMenuItem("退出");
        JMenuItem aboutMenuItem = new JMenuItem("关于");

        // 按钮-开始
        startMenuItem.addActionListener(e -> init());
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

        int pointRad = 6;
        //画点
        g.setColor(Color.BLACK);
        g.fillOval(4 * space - pointRad, 4 * space - pointRad, pointRad * 2, pointRad * 2);
        g.fillOval(12 * space - pointRad, 4 * space - pointRad, pointRad * 2, pointRad * 2);
        g.fillOval(8 * space - pointRad, 8 * space - pointRad, pointRad * 2, pointRad * 2);
        g.fillOval(4 * space - pointRad, 12 * space - pointRad, pointRad * 2, pointRad * 2);
        g.fillOval(12 * space - pointRad, 12 * space - pointRad, pointRad * 2, pointRad * 2);
    }

    /**
     * @param g
     * @param x
     * @param y
     * @param color
     */
    public void drawChess(Graphics g, int x, int y, int color) {
        ChessmanEnum chessmanEnum = ChessmanEnum.getByCode(color);
        // 画棋子
        g.setColor(chessmanEnum.getColor());
        g.fillOval(x * space - rad, y * space - rad, 2 * rad, 2 * rad);

        // 画落子次序
        String order = playOrder.get((x - 1) + "-" + (y - 1));
        if (order == null || order.equals("")) {
            return;
        }

        g.setColor(chessmanEnum.next().getColor());
        int length = order.length();
        if (length == 1) {
            g.setFont(new Font("雅黑", Font.PLAIN, 30));
            g.drawString(order, x * space - 8, y * space + 12);
        } else if (length == 2) {
            g.setFont(new Font("雅黑", Font.PLAIN, 30));
            g.drawString(order, x * space - 17, y * space + 12);
        } else if (length == 3) {
            g.setFont(new Font("雅黑", Font.PLAIN, 20));
            g.drawString(order, x * space - 17, y * space + 8);
        }
    }


    private void clearGrids() {
        for (int i = 0; i < GRIDS_NUM; i++) {
            for (int j = 0; j < GRIDS_NUM; j++) {
                chesses[i][j] = 0;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            String basePath = System.getProperty("user.dir");
            BufferedImage bi = ImageIO.read(new File(basePath + "\\background.jpg"));
            g.drawImage(bi, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setFont(new Font("mobang v1.0.1", Font.BOLD, 25));
//        g.drawString("-" + message, 210, 26);
        drawGrids(g);
        for (int i = 1; i <= GRIDS_NUM; i++) {
            for (int j = 1; j <= GRIDS_NUM; j++) {
                int chess = chesses[i - 1][j - 1];
                if (chess != 0) {
                    drawChess(g, i, j, chess);
                }
            }
        }
    }

    /**
     * 判断胜负
     */
    private void judge(Point point) {
        boolean win = Judge.win(chesses, chessman.getCode(), point);
        if (win) {
            gameOver = true;
            StringBuilder sb = new StringBuilder();
            sb.append("GAME OVER~");
            sb.append(chessman.getName() + " 获得胜利");

            JOptionPane.showMessageDialog(null, sb.toString());
            jLabel.setText(chessman.getName() + "获胜");
        }
        // 还手
        chessman = chessman.next();
    }
}