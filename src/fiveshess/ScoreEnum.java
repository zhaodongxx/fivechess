package fiveshess;

public enum ScoreEnum {

    /**
     * 眠一
     */
    BLOCKED_ONE(1),
    /**
     * 活一
     */
    ONE(10),
    /**
     * 眠二
     */
    BLOCKED_TWO(10),
    /**
     * 空二
     */
    EMPTY_TWO(50),
    /**
     * 眠三
     */
    BLOCKED_THREE(100),
    /**
     * 活二
     */
    TWO(100),
    /**
     * 空三
     */
    EMPTY_THREE(800),
    /**
     * 活三
     */
    THREE(1000),
    /**
     * 空四
     */
    EMPTY_FOUR(8000),
    /**
     * 空五
     */
    EMPTY_FIVE(8000),
    /**
     * 眠四
     */
    BLOCKED_FOUR(10000),
    /**
     * 活四
     */
    FOUR(100000),

    /**
     * 活五
     */
    FIVE(10000000),
    ;

    public int score;

    ScoreEnum(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
}
