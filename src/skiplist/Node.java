package skiplist;

/**
 * Created by Jakub on 2015-11-13.
 */
class Node implements Comparable<Node> {
    Node left,
            right,
            upper,
            down;

    Integer key;

    Node(Integer key) {
        this.key = key;
    }

    public int compareTo(Node other) {
        return this.key.compareTo(other.key);
    }

    @Override
    public String toString() {
        String thisNodeKey = key != null ? key.toString() : "n",
                leftKey = left != null ? (left.key != null ? left.key.toString() : "n") : "n",
                rightKey = right != null ? (right.key != null ? right.key.toString() : "n") : "n",
                upperKey = upper != null ? (upper.key != null ? upper.key.toString() : "n") : "n",
                downKey = down != null ? (down.key != null ? down.key.toString() : "n") : "n";

        return thisNodeKey + "(" + leftKey + ","  + rightKey + ","  + upperKey + ","  + downKey + ") ";
    }
}
