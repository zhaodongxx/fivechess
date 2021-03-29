package fiveshess;

import java.util.ArrayList;
import java.util.List;

import static fiveshess.ChessBoard.GRIDS_NUM;
import static fiveshess.ChessmanEnum.BLACK_CHESS;
import static fiveshess.ChessmanEnum.WHITE_CHESS;

/**
 * Created by zhaod on 2017/2/16 10:31
 */
public class AI {

    private static int level = 3;

    public AI() {
    }

    public static Point bingo(int[][] chessBoard, ChessmanEnum chessmanEnum, int order) {
        // 前10步用评估算法
        if (order < 11) {
            return evaluate(chessBoard, chessmanEnum);
        }
        return alpha_beta(chessBoard, chessmanEnum);
    }

    /**
     * 评估算法
     */
    public static Point evaluate(int[][] chessBoard, ChessmanEnum chessmanEnum) {
        int bestX = 8;
        int bestY = 6;
        int bestScore = 1;
        for (int i = 0; i < GRIDS_NUM; i++) {
            for (int j = 0; j < GRIDS_NUM; j++) {
                if (chessBoard[i][j] != 1 && chessBoard[i][j] != 2) {
                    int score = Judge.evaluatePoint(chessBoard, i, j, chessmanEnum);
                    score += Judge.evaluatePoint(chessBoard, i, j, chessmanEnum.next());
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

    /**
     * 极大极小值+α-β剪枝算法
     */
    public static Point alpha_beta(int[][] chessBoard, ChessmanEnum chessmanEnum) {

        long start = System.currentTimeMillis();

        int bestScore = Integer.MIN_VALUE;
        Point bestPoint = new Point(0, 0);
        ArrayList<Point> points = ChessBoard.gen(chessBoard);
        List<Point> steps = new ArrayList<>();
        for (Point point : points) {
            int score = getScore(1, chessBoard, point.getX(), point.getY(), Integer.MIN_VALUE, Integer.MAX_VALUE, chessmanEnum);
            if (chessmanEnum == WHITE_CHESS && score >= bestScore) {
                bestScore = score;
                bestPoint = point;
            } else if (chessmanEnum == BLACK_CHESS && score <= bestScore) {
                bestScore = score;
                bestPoint = point;
            }
        }

        long l = (System.currentTimeMillis() - start);
        System.out.println("最优步数: x：" + bestPoint.getX() + ",Y：" + bestPoint.getY() + "(" + bestScore + ")，耗时：" + l + "ms");

        return bestPoint;
    }

    /**
     * 得到分数
     * <p>
     * WHITE_CHESS  MAX alpha
     * BLACK_CHESS MIN beta
     */
    public static int getScore(int deep, int[][] chessBoard, int x, int y, int alpha, int beta, ChessmanEnum chessmanEnum) {
        chessBoard[x][y] = chessmanEnum.getCode();
        if (deep == level) {
            int evaluate = Judge.evaluate(chessBoard, chessmanEnum);
            chessBoard[x][y] = 0;
            return evaluate;
        }

        ArrayList<Point> gen = ChessBoard.gen(chessBoard);
        ChessmanEnum nextChessmanEnum = chessmanEnum.next();

        for (Point point : gen) {
            int score = getScore(deep + 1, chessBoard, point.getX(), point.getY(), alpha, beta, nextChessmanEnum);
            if (nextChessmanEnum == WHITE_CHESS) {
                alpha = Math.max(alpha, score);
                // 剪枝
                if (alpha >= beta) {
                    chessBoard[x][y] = 0;
                    return alpha;
                }
            } else {
                beta = Math.min(beta, score);
                // 剪枝
                if (alpha >= beta) {
                    chessBoard[x][y] = 0;
                    return beta;
                }
            }
        }

        chessBoard[x][y] = 0;
        return nextChessmanEnum == WHITE_CHESS ? alpha : beta;
    }

}
