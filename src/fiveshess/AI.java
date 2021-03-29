package fiveshess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaod on 2017/2/16 10:31
 */
public class AI {

    private static int grids = 15;
    public int[][] chesses;

    public AI(int[][] chesses) {
        this.chesses = chesses;
    }

    //ai下棋
    public Point aiPlayer(ChessmanEnum chessmanEnum) {
        int bestX = 8;
        int bestY = 6;
        int bestScore = 1;
        for (int i = 0; i < ChessBoard.GRIDS_NUM; i++) {
            for (int j = 0; j < ChessBoard.GRIDS_NUM; j++) {
                if (chesses[i][j] != 1 && chesses[i][j] != 2) {
                    int score = Judge.evaluatePoint(chesses, i, j, chessmanEnum);
                    score += Judge.evaluatePoint(chesses, i, j, chessmanEnum.next());
                    if (score > bestScore) {
                        bestScore = score;
                        bestX = i;
                        bestY = j;
                    }
                }
            }
        }
        return new Point(bestX, bestY);
    }

    public static Point getBestPoint(int[][] chessBoard, ChessmanEnum chessmanEnum) {
        int bestScore = 0;
        Point bestPoint = new Point(0, 0);
//        for (int i = 2; i <= ChessBoard.DEEP; i += 2) {
        ArrayList<Point> points = gen(chessBoard);
        List<Point> steps = new ArrayList<>();
        for (Point point : points) {
            int score = getScore(ChessBoard.DEEP, chessBoard, point.getX(), point.getY(), steps, chessmanEnum);
            if (/*chessmanEnum == BLACK_CHESS &&*/ score > bestScore) {
                bestScore = score;
                bestPoint = point;
            } /*else if (chessmanEnum == WHITE_CHESS && score < bestScore) {
                bestScore = score;
                bestPoint = point;
            }*/
        }
//        }

        System.out.println("最优步数: x：" + bestPoint.getX() + ",Y：" + bestPoint.getY() + "(" + bestScore + ")");
        return bestPoint;
    }

    /**
     * 得到分数
     */
    public static int getScore(int deep, int[][] chessBoard, int x, int y, List<Point> steps, ChessmanEnum chessmanEnum) {
        chessBoard[x][y] = chessmanEnum.getCode();
//        steps.add(new Point(x, y));

        if (deep == 0) {
            int evaluate = Judge.evaluate(chessBoard, chessmanEnum);
            chessBoard[x][y] = 0;
            return evaluate;
        }

        deep--;
        ArrayList<Point> gen = gen(chessBoard);
        ChessmanEnum nextChessmanEnum = chessmanEnum.next();

        int bestX = 0;
        int bestY = 0;
        int bestScore = 0;

        for (Point point : gen) {
            int score = getScore(deep, chessBoard, point.getX(), point.getY(), steps, nextChessmanEnum);
            bestScore = score > bestScore ? score : bestScore;
        }

        chessBoard[x][y] = 0;
        return bestScore;
    }

    /**
     * 生成所有可以落子的点
     */
    public static ArrayList<Point> gen(int[][] chessBoard) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < ChessBoard.GRIDS_NUM; i++) {
            for (int j = 0; j < ChessBoard.GRIDS_NUM; j++) {
                if (chessBoard[i][j] == 0) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }
}
