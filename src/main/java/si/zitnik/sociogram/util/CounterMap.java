package si.zitnik.sociogram.util;

/**
 * Created by slavkoz on 23/09/14.
 */
import java.util.HashMap;
import java.util.HashSet;


/**
 * Created by IntelliJ IDEA.
 * User: slavkoz
 * Date: 3/9/12
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */

public class CounterMap<K> {
    public HashMap<K, Integer> map = new HashMap<K, Integer>();

    public void put(K k) {
        if (map.containsKey(k)) {
            map.put(k, map.get(k) + 1);
        } else {
            map.put(k, 1);
        }
    }

    /**
     * Returns a set of key which have counter values at least 3
     * @param cutoff
     * @return
     */
    public HashSet<K> toSet(Integer cutoff) {
        HashSet<K> retVal = new HashSet<K>();
        for (K k : map.keySet()) {
            if (map.get(k) >= cutoff) {
                retVal.add(k);
            }
        }
        return retVal;
    }

    public Integer getCount(K w, Integer defVal) {
        if (this.map.containsKey(w)) {
            return this.map.get(w);
        } else {
            return defVal;
        }
    }
}
