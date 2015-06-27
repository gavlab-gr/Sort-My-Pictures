/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptux;

/**
 *
 * @author Vasileios Gerodimos
 * A.M. 2025200900014
 * 
 * @author Vasileios Stamos
 * A.M. 2025200900070
 */
public class KDNode<T extends KDNodeComparator<T>> {
    KDNode<T> left;
    KDNode<T> right;
    T location;

    public KDNode( KDNode<T> left, KDNode<T> right, T location ) {
        this.left = left;
        this.right = right;
        this.location = location;
    }
}
