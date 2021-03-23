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
     *
     * @param x
     * @param y
     */
    public static boolean win(int[][] chesses, int currColor, int x, int y) {
        int count = 1;
        //Y
        for (int i = 1; i <= 4; i++) {
            if (y + i < GRIDS_NUM + 1 && currColor == chesses[x][y + i]) {
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
            return true;
        }
        //X
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < GRIDS_NUM + 1 && currColor == chesses[x + i][y]) {
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
            return true;
        }

        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < GRIDS_NUM + 1 && y + i < GRIDS_NUM + 1 && currColor == chesses[x + i][y + i]) {
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
            return true;
        }

        //  "\"
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x - i > 0 && y + i < GRIDS_NUM + 1 && currColor == chesses[x - i][y + i]) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (x + i < GRIDS_NUM + 1 && y - i > 0 && currColor == chesses[x + i][y - i]) {
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
}
