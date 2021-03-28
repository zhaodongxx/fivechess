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

    public static int evaluatePoint(int[][] board, int x, int y, ChessmanEnum chessmanEnum) {
        int length = board.length;
        int score = 0;
        if (x < 0 || x > length - 1 || y < 0 || y > length - 1) {
            return score;
        }

        int limit = 6;
        int xi = x;
        int yi = y;

        int code = chessmanEnum.getCode();
        int anotherColorCode = chessmanEnum.next().getCode();

        int count = 1;
        int block = 0;
        int empty = 0;

        if (x == 7 && y == 8) {
            System.out.println(1);
        }

        // 【-】 右边的5个点位
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
        // 【-】 左边的四个点位
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
        score = evaluateLineScore(xi, yi, count, empty, block);

        // 【|】 上边的四个点位
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
        // 【-】 下边的四个点位
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
        score += evaluateLineScore(xi, yi, count, empty, block);

        // 【/】 东北方位的四个点位
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
        // 【/】 西南方位的四个点位
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
        score += evaluateLineScore(xi, yi, count, empty, block);

        // 【\】 东南方位的四个点位
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
        // 【/】 西北方位的四个点位
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
        score += evaluateLineScore(xi, yi, count, empty, block);
        return score;
    }


    public static int evaluateLineScore(int xi, int yi, int count, int empty, int block) {
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
