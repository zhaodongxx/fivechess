package fiveshess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaod on 2017/2/16 10:31
 */
public class AI {

    private static int currColor;
    private static int grids = 15;

    public int[][] chesses = new int[grids + 1][grids + 1];

    public AI(int currColor, int[][] chesses) {
        this.currColor = currColor;
        this.chesses = chesses;
    }

    //ai下棋
    public List<Integer> aiPlayer() {

        int optimalX = 8;
        int optimalY = 6;
        int point = 6;
        for (int i = 1; i < grids + 1; i++) {
            for (int j = 1; j < grids + 1; j++) {
                if (chesses[i][j] != 1 && chesses[i][j] != 2) {
                    int curPoint = aiRule(i, j);
                    turnColor();
                    int curPoint2 = aiRule(i, j);//对手的棋
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
        System.out.println("" + optimalX + "  " + optimalY + "point:" + point + " " + currColor);
        List<Integer> list = new ArrayList<Integer>();
        list.add(optimalX);
        list.add(optimalY);
        return list;
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

    private int turnColor(int color) {
        if (color == 1) {   //1=白棋;2=黑棋
            color = 2;
        } else {
            color = 1;
        }
        return color;
    }
}
