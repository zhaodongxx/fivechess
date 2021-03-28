package fiveshess.ai;

import fiveshess.Point;

import java.util.regex.Pattern;

/**
 * Created by zhaod on 2017/2/16 10:31
 */
public class AI {

    private static int currColor;
    private static int grids = 15;

    public int[][] chesses;

    public AI(int currColor, int[][] chesses) {
        AI.currColor = currColor;
        this.chesses = chesses;
    }

    //ai下棋
    public Point aiPlayer() {
        int optimalX = 8;
        int optimalY = 6;
        int point = 6;
        for (int i = 1; i < grids + 1; i++) {
            for (int j = 1; j < grids + 1; j++) {
                if (chesses[i][j] != 1 && chesses[i][j] != 2) {
                    int curPoint = aiRule(i, j);
                    turnColor();
                    //对手的棋
                    int curPoint2 = aiRule(i, j);
                    turnColor();
                    if (curPoint + curPoint2 > point) {
                        point = curPoint + curPoint2;
                        optimalX = i;
                        optimalY = j;
                    }
                }
            }
        }
        chesses[optimalX][optimalY] = currColor;

        return new Point(optimalX, optimalY);
    }

    private int aiRule(int x, int y) {
        //Y轴。
        int point = 0;
        int count = 1;
        Boolean stat1, stat2;
        for (int i = 1; i <= 4; i++) {
            if (y + i < grids && currColor == chesses[x][y + i]) {
                count++;
            } else {
                stat1 = true;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (y - i > 0 && currColor == chesses[x][y - i]) {
                count++;
            } else {
                stat2 = true;
            }
        }

        point += getPoint(count);
        //X轴。
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < grids && currColor == chesses[x + i][y]) {
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
        point += getPoint(count);
        //"/"轴。
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x + i < grids && y + i < grids && currColor == chesses[x + i][y + i]) {
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
        point += getPoint(count);
        //"\"轴。
        count = 1;
        for (int i = 1; i <= 4; i++) {
            if (x - i > 0 && y + i < grids && currColor == chesses[x - i][y + i]) {
                count++;
            } else {
                break;
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (x + i < grids && y - i > 0 && currColor == chesses[x + i][y - i]) {
                count++;

            } else {
                break;
            }
        }
        point += getPoint(count);
        System.out.println(point);
        return point;
    }

    private int getPoint(int count) {
        int point = 0;
        switch (count) {
            case 1:
                point += 1;
                break;
            case 2:
                point += 5;
                break;
            case 3:
                point += 20;
                break;
            case 4:
                point += 100;
                break;
            case 5:
                point += 999;
                break;
        }
        return point;
    }

    private void turnColor() {
        //1=白棋;2=黑棋
        currColor = (currColor == 2 ? 1 : 2);
    }


//    /**
//     * o代表空位；
//     * m代表己方颜色；
//     */
//    private int getScore(String layout) {
//        int length = layout.length();
//
//        // 死一、死二、死三、死四
//        if (length < 5) {
//            return 0;
//        }
//
//        // 眠二
//        if (isMatch("^mmo", layout)) {
//            return 2;
//        }
//
//
//    }

    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
}