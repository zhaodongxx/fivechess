package fiveshess;

import javax.swing.*;
import java.awt.*;

public class ChessPlayer extends JFrame {
    private ChessBoard chessPanel = new ChessBoard(40, 15);

    public ChessPlayer(String title) {
        super(title);

        Container contentPane = getContentPane();
        contentPane.add(chessPanel);
        setJMenuBar(chessPanel.getMenuBar());
        setSize(760, 760);
        setVisible(true);
        //ʹ�����С���ɸı䡣
        setResizable(false);
        //ʹ������С�
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String arg[]) {
        new ChessPlayer("������_V1.0");
    }
}
