package fiveshess;

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
        for (int i = 0; i < grids; i++) {
            for (int j = 0; j < grids; j++) {
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
}
