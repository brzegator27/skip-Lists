package skiplist.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Jakub on 2015-11-21.
 */
public class SkipListMapIterator implements Iterator<Integer> {

    private MapNode currentNode;

    public SkipListMapIterator(MapNode lowestMandrel) {
        this.currentNode = lowestMandrel;
    }

    @Override
    public Integer next() {
        if(hasNext()) {
            currentNode = currentNode.right;
            return currentNode.key;
        } else {
            throw new NoSuchElementException("There are no more elements");
        }
    }

    @Override
    public boolean hasNext() {
        return currentNode.right != null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Removal is not supported in skipList");
    }
}
