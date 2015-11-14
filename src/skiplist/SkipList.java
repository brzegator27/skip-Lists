package skiplist;

import java.util.Random;

/**
 * Created by Jakub on 2015-11-13.
 */
public class SkipList {
    Node mandrel;
    final Node lowestMandrel;

    Integer level,
            count;

    public SkipList() {
        mandrel = new Node(null);
        lowestMandrel = mandrel;
        level = 0;
        count = 0;
    }

    public void add(Integer key) {
        Integer newNodeMaxLevel = count != 0 ? getNodeMaxLevel() : 0,
                currentLevel = level;
        Node newNode = new Node(key),
                upperNewNode = null,
                currentNode = mandrel;

        System.out.println(newNodeMaxLevel);
        if(newNodeMaxLevel > level) {
            manageNewMandrel(newNode);

            upperNewNode = newNode;
            newNode = new Node(key);
        }

//        Till we don't reach the last predecessor
        while(currentLevel != 0 || (currentNode.right != null && currentNode.right.compareTo(newNode) == -1)) {
//            If (...) then we go down the skip list
            if(currentNode.right == null || currentNode.right.compareTo(newNode) == 1) {
                if(currentLevel <= newNodeMaxLevel) {
                    insertNode(currentNode, newNode, upperNewNode, null);
                }

                upperNewNode = newNode;
                newNode = new Node(key);

                currentNode = currentNode.down;
                currentLevel--;
            } else {
                currentNode = currentNode.right;
            }
        }

        insertNode(currentNode, newNode, upperNewNode, null);
        count++;
    }

    void manageNewMandrel(Node newNode) {
        Node oldMandrel = mandrel;

        mandrel = new Node(null);
        insertMandrel(mandrel, oldMandrel);

        insertNode(mandrel, newNode, null, null);

        level++;
    }

    void insertMandrel(Node newTopMandrel, Node oldMandrel) {
        if(newTopMandrel == null || oldMandrel == null) {
            return;
        }

        newTopMandrel.down = oldMandrel;
        oldMandrel.upper = newTopMandrel;
    }

    void insertNode(Node first, Node second, Node secondUpper, Node secondDown) {
        Node firstRight = first.right;

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

    int getNodeMaxLevel() {
        int newNodeMaxLevel = 0;
        Random randGenerator = new Random();

        while(newNodeMaxLevel < level + 1 && randGenerator.nextInt(2) == 1) {
            newNodeMaxLevel++;
        }

        return newNodeMaxLevel;
    }

    public Integer get(Integer key) {

    }

    Node getNodeByKey(Integer key) {
        Node currentNode = mandrel;

        //        Till we don't reach the last predecessor
        while(currentLevel != 0 || (currentNode.right != null && currentNode.right.compareTo(newNode) == -1)) {
//            If (...) then we go down the skip list
            if(currentNode.right == null || currentNode.right.compareTo(newNode) == 1) {
                if(currentLevel <= newNodeMaxLevel) {
                    insertNode(currentNode, newNode, upperNewNode, null);
                }

                upperNewNode = newNode;
                newNode = new Node(key);

                currentNode = currentNode.down;
                currentLevel--;
            } else {
                currentNode = currentNode.right;
            }
        }
    }

    @Override
    public String toString() {
        Node currentMandrelNode = mandrel;
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

    String singleRowToString(Node firstNode) {
        Node currentNode = firstNode;
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
}
