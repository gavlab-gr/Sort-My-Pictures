/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptux;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Vasileios Gerodimos
 * A.M. 2025200900014
 * 
 * @author Vasileios Stamos
 * A.M. 2025200900070
 */
public class KDTree<T extends KDNodeComparator<T>> {
    private KDNode<T> root;

    public KDTree( List<T> items ) {
        root = createKDTree(items, 0);
    }

    public T findNearest( T search ) {
        
        return findNearest(root, search, 0).location;
    }
        
    // Only ever goes to log2(items.length) depth so lack of tail recursion is a non-issue
    private KDNode<T> createKDTree( List<T> items, int depth ) {
        if ( items.isEmpty() ) {
            return null;
        }
        Collections.sort(items, items.get(0).getComparator(depth % 3));
        int currentIndex = items.size()/2;
        return new KDNode<T>(createKDTree(items.subList(0, currentIndex), depth+1), createKDTree(items.subList(currentIndex + 1, items.size()), depth+1), items.get(currentIndex));
    }

    private KDNode<T> findNearest(KDNode<T> currentNode, T search, int depth) {
        int direction = search.getComparator(depth % 3).compare( search, currentNode.location );
        KDNode<T> next = (direction < 0) ? currentNode.left : currentNode.right;
        KDNode<T> other = (direction < 0) ? currentNode.right : currentNode.left;
        KDNode<T> best = (next == null) ? currentNode : findNearest(next, search, depth + 1); // Go to a leaf
        
        if ( currentNode.location.squaredDistance(search) < best.location.squaredDistance(search) ) {
            best = currentNode; // Set best as required
        } 
        if ( other != null ) {
            if ( currentNode.location.axisSquaredDistance(search, depth % 3) < best.location.squaredDistance(search) ) {
                KDNode<T> possibleBest = findNearest( other, search, depth + 1 );
                if (  possibleBest.location.squaredDistance(search) < best.location.squaredDistance(search) ) {
                    best = possibleBest;
                }
            }
        }
        return best; // Work back up
    }
}
