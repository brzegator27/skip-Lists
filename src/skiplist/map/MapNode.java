package skiplist.map;

/**
 * Created by Jakub on 2015-11-21.
 */
class MapNode implements Comparable<MapNode> {
    MapNode left,
            right,
            upper,
            down;

    Integer key,
            value;

    MapNode(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public int compareTo(MapNode other) {
        return this.key.compareTo(other.key);
    }

    @Override
    public String toString() {
        String thisNodeKey = key != null ? key.toString() : "n",
                rightKey = right != null ? (right.key != null ? right.key.toString() : "n") : "n",
                downKey = down != null ? (down.key != null ? down.key.toString() : "n") : "n";

        return thisNodeKey + "(" + rightKey + ","  + downKey + ") ";
    }
}
