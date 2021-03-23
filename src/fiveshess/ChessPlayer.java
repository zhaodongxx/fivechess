package fiveshess;

import javax.swing.*;
import java.awt.*;

public class ChessPlayer extends JFrame {

    private ChessBoard chessPanel = new ChessBoard();
    public static final String TITLE = "五子棋_V1.0";

    public ChessPlayer() {
        super(TITLE);

        Container contentPane = getContentPane();
        contentPane.add(chessPanel);
        setJMenuBar(chessPanel.getMenuBar());
        setSize(760, 760);
        setVisible(true);
        //使窗体大小不可改变。
        setResizable(false);
        //使窗体居中。
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String arg[]) {
        new ChessPlayer();
    }
//    public void
}
