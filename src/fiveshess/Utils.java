package fiveshess;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhaodong5
 * @date 2021/3/29 11:36
 */
public class Utils {

    /**
     * 获取map的最后一个entry
     */
    public static <K, V> Map.Entry<K, V> getTail(LinkedHashMap<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }
}
