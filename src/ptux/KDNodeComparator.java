/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptux;

import java.util.Comparator;

/**
 *
 * @author Vasileios Gerodimos
 * A.M. 2025200900014
 * 
 * @author Vasileios Stamos
 * A.M. 2025200900070
 */
public abstract class KDNodeComparator<T> { 
    // This should return a comparator for whatever axis is passed in
    protected abstract Comparator<T> getComparator(Integer axis);
    
    // Return squared distance between current and other
    protected abstract <T> Double squaredDistance(T other);
    
    // Return squared distance between one axis only
    protected abstract <T> Double axisSquaredDistance(T other, Integer axis);
}
