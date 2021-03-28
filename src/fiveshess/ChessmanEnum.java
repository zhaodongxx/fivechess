package fiveshess;

import java.awt.*;

/**
 * 棋子
 *
 * @author zhaodong5
 * @date 2021/3/23 19:31
 */
public enum ChessmanEnum {

    BLACK_CHESS(1, "黑棋", Color.BLACK),
    WHITE_CHESS(2, "白棋", Color.WHITE),
    ;

    private int code;
    private String name;
    private Color color;

    ChessmanEnum(Integer code, String name, Color color) {
        this.code = code;
        this.name = name;
        this.color = color;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }


    public ChessmanEnum next() {
        return this == BLACK_CHESS ? WHITE_CHESS : BLACK_CHESS;
    }

    public static ChessmanEnum getByCode(Integer code) {
        for (ChessmanEnum value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }

        return null;
    }
}
