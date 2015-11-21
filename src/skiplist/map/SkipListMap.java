package skiplist.map;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Jakub on 2015-11-21.
 */
public class SkipListMap implements Iterable<Integer> {
    MapNode mandrel;
    final MapNode lowestMandrel;

    Integer level,
            count;

    Random randGenerator = new Random();
    Integer randomLevelSeed = randGenerator.nextInt();

    public SkipListMap() {
        mandrel = new MapNode(null, null);
        lowestMandrel = mandrel;
        level = 0;
        count = 0;
    }

    public void put(Integer key, Integer value) {
        Integer newNodeMaxLevel = count != 0 ? getNodeMaxLevel() : 0,
                currentLevel = level;
        MapNode newNode = new MapNode(key, value),
                upperNewNode = null,
                currentNode = mandrel;

        if(newNodeMaxLevel > level) {
            manageNewMandrel(newNode);

            upperNewNode = newNode;
            newNode = new MapNode(key, value);
        }

//        Till we don't reach the last predecessor
        while(currentLevel != 0 || (currentNode.right != null && currentNode.right.compareTo(newNode) == -1)) {
//            If (...) then we go down the skip list
            if(currentNode.right == null || currentNode.right.compareTo(newNode) == 1) {
                if(currentLevel <= newNodeMaxLevel) {
                    insertNode(currentNode, newNode, upperNewNode, null);
                }

                upperNewNode = newNode;
                newNode = new MapNode(key, value);

                currentNode = currentNode.down;
                currentLevel--;
            } else {
                currentNode = currentNode.right;
            }
        }

        insertNode(currentNode, newNode, upperNewNode, null);
        count++;
    }

    void manageNewMandrel(MapNode newNode) {
        MapNode oldMandrel = mandrel;

        mandrel = new MapNode(null, null);
        insertMandrel(mandrel, oldMandrel);

        insertNode(mandrel, newNode, null, null);

        level++;
    }

    void insertMandrel(MapNode newTopMandrel, MapNode oldMandrel) {
        if(newTopMandrel == null || oldMandrel == null) {
            return;
        }

        newTopMandrel.down = oldMandrel;
        oldMandrel.upper = newTopMandrel;
    }

    void insertNode(MapNode first, MapNode second, MapNode secondUpper, MapNode secondDown) {
        MapNode firstRight = first.right;

        first.right = second;

        if(second != null) {
            second.left = first;
            second.right = firstRight;
            second.upper = secondUpper;
            second.down = secondDown;
        }

        if(secondUpper != null) {
            secondUpper.down = second;
        }

        if(secondDown != null) {
            secondDown.upper = second;
        }

        if(firstRight != null) {
            firstRight.left = second;
        }
    }

    public void remove(Integer key) {
        MapNode nodeWithGivenKey = getNodeByKey(key),
                currentNode;

        currentNode = nodeWithGivenKey.upper;
        while(currentNode != null) {
            removeNode(currentNode);
            currentNode = currentNode.upper;
        }

        currentNode = nodeWithGivenKey.down;
        while(currentNode != null) {
            removeNode(currentNode);
            currentNode = currentNode.down;
        }

        removeNode(nodeWithGivenKey);
    }

    void removeNode(MapNode nodeToRemove) {
        if(nodeToRemove.right != null) {
            nodeToRemove.right.left = nodeToRemove.left;
        }
        if(nodeToRemove.left != null) {
            nodeToRemove.left.right = nodeToRemove.right;
        }
    }

//    int getNodeMaxLevel() {
//        int newNodeMaxLevel = 0;
//        Random randGenerator = new Random();
//
//        while(newNodeMaxLevel < level + 1 && randGenerator.nextInt(2) == 1) {
//            newNodeMaxLevel++;
//        }
//
//        return newNodeMaxLevel;
//    }
//
//    public Integer get(Integer key) {
//        return getNodeByKey(key).value;
//    }

    int _getNodeMaxLevel() {
        int newNodeMaxLevel,
            found = 0;

        for(newNodeMaxLevel = -1; found == 0; newNodeMaxLevel++) {
            if(randomLevelSeed == 0) {
                randomLevelSeed = randGenerator.nextInt();
            }

            found = randomLevelSeed % 2;
            randomLevelSeed /= 2;
        }

        if(newNodeMaxLevel > level + 1) {
            newNodeMaxLevel = level + 1;
        }

        return newNodeMaxLevel;
    }

//    -----------------------------------------------------------

    private final Random seedGenerator = new Random();
    private int randomSeed = seedGenerator.nextInt() | 0x0100;

    private int getNodeMaxLevel() {
        int x = randomSeed;
        x ^= x << 13;
        x ^= x >>> 17;
        randomSeed = x ^= x << 5;
        if ((x & 0x8001) != 0) // test highest and lowest bits
            return 0;
        int level = 1;
        while (((x >>>= 1) & 1) != 0) ++level;

        // -----------------------
        if(level > this.level + 1) {
            level = this.level + 1;
        }
        // -----------------------

        return level;
    }

//    -----------------------------------------------------------

    public Integer get(Integer key) {
        return getNodeByKey(key).value;
    }

    MapNode getNodeByKey(Integer key) {
        MapNode currentNode = mandrel;

        while(currentNode != null) {
            if(currentNode.key != null && currentNode.key.compareTo(key) == 0) {
                return currentNode;
            }

            if(currentNode.right != null && currentNode.right.key != null && currentNode.right.key.compareTo(key) != 1) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.down;
            }
        }

        return null;
    }

    MapNode lowerNode(Integer key) {
        MapNode node = getLowestNodeByKey(key);

        while(node != null && node.key != null) {
            if(node.key.compareTo(key) == -1) {
                return node;
            }

            node = node.left;
        }

        return node;
    }

    public Integer higherKey(Integer key) {
        MapNode higherNode = higherNode(key);

        return higherNode != null ? higherNode.key : null;
    }

    MapNode higherNode(Integer key) {
        MapNode node = getLowestNodeByKey(key);

        while(node != null) {
            if(node.key.compareTo(key) == 1) {
                return node;
            }

            node = node.right;
        }

        return null;
    }

    public Integer lowerKey(Integer key) {
        MapNode lowerNode = lowerNode(key);

        return lowerNode != null ? lowerNode.key : null;
    }

    MapNode getLowestNodeByKey(Integer key) {
        MapNode topNode = getNodeByKey(key),
                currentNode = topNode;

        while(currentNode.down != null) {
            currentNode = currentNode.down;
        }

        return currentNode;
    }

    @Override
    public String toString() {
        MapNode currentMandrelNode = mandrel;
        String skipListAsString = new String();

        if(mandrel == null) {
            return "";
        }

        while(currentMandrelNode != null) {
            skipListAsString += singleRowToString(currentMandrelNode) + "\n";
            currentMandrelNode = currentMandrelNode.down;
        }

        return skipListAsString;
    }

    String singleRowToString(MapNode firstNode) {
        MapNode currentNode = firstNode;
        String rowAsString = new String();

        if(firstNode == null) {
            return "";
        }

        while(currentNode != null) {
            rowAsString += " -> " + currentNode.toString();
            currentNode = currentNode.right;
        }

        return rowAsString;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new SkipListMapIterator(lowestMandrel);
    }
}
