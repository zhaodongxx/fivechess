package fiveshess;

/**
 * 棋子
 *
 * @author zhaodong5
 * @date 2021/3/23 19:31
 */
public enum ChessmanEnum {

    BLACK_CHESS(1, "黑棋"),
    WHITE_CHESS(2, "白棋"),
    ;

    private Integer code;
    private String name;

    ChessmanEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ChessmanEnum next() {
        return this == BLACK_CHESS ? WHITE_CHESS : BLACK_CHESS;
    }
}
