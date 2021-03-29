package fiveshess;

import static fiveshess.ChessBoard.GRIDS_NUM;

/**
 * 裁判
 *
 * @author zhaodong5
 * @date 2021/3/23 18:56
 */
public class Judge {

    /**
     * 判断胜负
     */
    public static boolean win(int[][] chesses, int currColor, Point point) {
        int x = point.getX();
        int y = point.getY();
        int count = 1;
        //Y
        for (int i = 1; i <= 4; i++) {
            if (y + i < GRIDS_NUM && currColor == chesses[x][y + i]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (y - i >= 0 && currColor == chesses[x][y - i]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }
        //X
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < GRIDS_NUM && currColor == chesses[x + i][y]) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (x - i >= 0 && currColor == chesses[x - i][y]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }

        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < GRIDS_NUM && y + i < GRIDS_NUM && currColor == chesses[x + i][y + i]) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (x - i >= 0 && y - i >= 0 && currColor == chesses[x - i][y - i]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }

        //  "\"
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x - i >= 0 && y + i < GRIDS_NUM && currColor == chesses[x - i][y + i]) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (x + i < GRIDS_NUM && y - i >= 0 && currColor == chesses[x + i][y - i]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }

        return false;
    }

    /**
     * 对局势进行评估。
     *
     * @param chessBoard
     * @param chessmanEnum
     * @return
     */
    public static int evaluate(int[][] chessBoard, ChessmanEnum chessmanEnum) {
        int blackScore = 0;
        int whiteScore = 0;
        for (int i = 0; i < ChessBoard.GRIDS_NUM; i++) {
            for (int j = 0; j < ChessBoard.GRIDS_NUM; j++) {
                if (chessBoard[i][j] == ChessmanEnum.BLACK_CHESS.getCode()) {
                    blackScore += evaluatePoint(chessBoard, i, j, ChessmanEnum.BLACK_CHESS);
                }
                if (chessBoard[i][j] == ChessmanEnum.WHITE_CHESS.getCode()) {
                    whiteScore += evaluatePoint(chessBoard, i, j, ChessmanEnum.WHITE_CHESS);
                }
            }
        }

        return whiteScore - blackScore;
    }

    /**
     * 评估指定位置棋子的分数，分数越高说明该位置越好
     *
     * @param board        棋盘数组
     * @param xi           指定棋子的X轴坐标，从0开始
     * @param yi           指定棋子的Y轴坐标，从0开始
     * @param chessmanEnum 指定棋子的颜色
     * @return 指定位置的棋子的分数
     */
    public static int evaluatePoint(int[][] board, int xi, int yi, ChessmanEnum chessmanEnum) {
        int length = board.length;
        int score = 0;
        if (xi < 0 || xi > length - 1 || yi < 0 || yi > length - 1) {
            return score;
        }

        int limit = 6;
        int code = chessmanEnum.getCode();
        int anotherColorCode = chessmanEnum.next().getCode();

        int count = 1;
        int block = 0;
        int empty = 0;

        // 【-】 右边的点位
        for (int i = 1; i < limit; i++) {
            if (xi + i > length - 1) {
                block++;
                break;
            }
            int point = board[xi + i][yi];
            if (point == 0) {
                if (xi + i + 1 <= length - 1 && board[xi + i + 1][yi] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        // 【-】 左边的点位
        for (int i = 1; i < limit; i++) {
            if (xi - i < 0) {
                block++;
                break;
            }
            int point = board[xi - i][yi];
            if (point == 0) {
                if (xi - i - 1 > 0 && board[xi - i - 1][yi] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        score = evaluateLineScore(count, empty, block);

        // 【|】 上边的点位
        count = 1;
        block = 0;
        empty = 0;
        for (int i = 1; i < limit; i++) {
            if (yi + i > length - 1) {
                block++;
                break;
            }
            int point = board[xi][yi + i];
            if (point == 0) {
                if (yi + i + 1 <= length - 1 && board[xi][yi + i + 1] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        // 【-】 下边的点位
        for (int i = 1; i < limit; i++) {
            if (yi - i < 0) {
                block++;
                break;
            }
            int point = board[xi][yi - i];
            if (point == 0) {
                if (yi - i - 1 > 0 && board[xi][yi - i - 1] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        score = Math.max(evaluateLineScore(count, empty, block), score);

        // 【/】 东北方位的点位
        count = 1;
        block = 0;
        empty = 0;
        for (int i = 1; i < limit; i++) {
            if (yi - i < 0 || xi + i > length - 1) {
                block++;
                break;
            }
            int point = board[xi + i][yi - i];
            if (point == 0) {
                if (xi + i + 1 <= length - 1 && yi - i - 1 > 0 && board[xi + i + 1][yi - i - 1] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        // 【/】 西南方位的点位
        for (int i = 1; i < limit; i++) {
            if (yi + i > length - 1 || xi - i < 0) {
                block++;
                break;
            }
            int point = board[xi - i][yi + i];
            if (point == 0) {
                if (xi - i - 1 > 0 && yi + i + 1 <= length - 1 && board[xi - i - 1][yi + i + 1] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        score = Math.max(evaluateLineScore(count, empty, block), score);

        // 【\】 东南方位的点位
        count = 1;
        block = 0;
        empty = 0;
        for (int i = 1; i < limit; i++) {
            if (yi + i > length - 1 || xi + i > length - 1) {
                block++;
                break;
            }
            int point = board[xi + i][yi + i];
            if (point == 0) {
                if (xi + i + 1 <= length - 1 && yi + i + 1 <= length - 1 && board[xi + i + 1][yi + i + 1] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        // 【/】 西北方位的点位
        for (int i = 1; i < limit; i++) {
            if (yi - i < 0 || xi - i < 0) {
                block++;
                break;
            }
            int point = board[xi - i][yi - i];
            if (point == 0) {
                if (xi - i - 1 > 0 && yi - i - 1 > 0 && board[xi - i - 1][yi - i - 1] == code && empty < 1) {
                    empty++;
                } else {
                    break;
                }
            }
            if (point == anotherColorCode) {
                block++;
                break;
            }
            if (point == code) {
                count++;
            }
        }
        return Math.max(evaluateLineScore(count, empty, block), score);
    }

    /**
     * 评估某一条线的分数
     *
     * @param count 己方颜色棋子的数量 1~9
     * @param empty 己方颜色棋子中空位的数量：0、1
     * @param block 被挡住的边数：0、1、2
     * @return 分数
     */
    public static int evaluateLineScore(int count, int empty, int block) {
        if (empty == 0) {
            if (count >= 5) {
                return ScoreEnum.FIVE.getScore();
            }
            if (block == 0) {
                switch (count) {
                    case 1:
                        return ScoreEnum.ONE.getScore();
                    case 2:
                        return ScoreEnum.TWO.getScore();
                    case 3:
                        return ScoreEnum.THREE.getScore();
                    case 4:
                        return ScoreEnum.FOUR.getScore();
                }
            }
            if (block == 1) {
                switch (count) {
                    case 1:
                        return ScoreEnum.BLOCKED_ONE.getScore();
                    case 2:
                        return ScoreEnum.BLOCKED_TWO.getScore();
                    case 3:
                        return ScoreEnum.BLOCKED_THREE.getScore();
                    case 4:
                        return ScoreEnum.BLOCKED_FOUR.getScore();
                }
            }
            if (block == 2) {
                return 0;
            }
        }

        if (empty == 1) {
            if (count > 4) {
                return ScoreEnum.FOUR.getScore();
            }
            if (block == 0) {
                switch (count) {
                    case 1:
                        return ScoreEnum.ONE.getScore();
                    case 2:
                        return ScoreEnum.EMPTY_TWO.getScore();
                    case 3:
                        return ScoreEnum.EMPTY_THREE.getScore();
                    case 4:
                        return ScoreEnum.EMPTY_FOUR.getScore();
                }
            }
            if (block == 1) {
                switch (count) {
                    case 1:
                        return ScoreEnum.ONE.getScore();
                    case 2:
                        return ScoreEnum.ONE.getScore();
                    case 3:
                        return ScoreEnum.EMPTY_TWO.getScore();
                    case 4:
                        return ScoreEnum.EMPTY_THREE.getScore();
                }
            }
            if (block == 2) {
                return 0;
            }
        }

        return 0;
    }
}
