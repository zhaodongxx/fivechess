package fiveshess;

import java.util.HashMap;

/**
 * @author zhaodong5
 * @date 2021/3/23 20:25
 */
public class Config {


    public static HashMap<String, Integer> map = new HashMap<>();

    static {


        /**
         * *代表当前位置；
         * 0代表空位；
         * m代表己方颜色；
         */
        map.put("0000*0000", 1);
        map.put("000*0000", 1);
        map.put("00*0000", 1);
        map.put("0*0000", 1);
        map.put("*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
        map.put("0000*0000", 1);
    }
}
