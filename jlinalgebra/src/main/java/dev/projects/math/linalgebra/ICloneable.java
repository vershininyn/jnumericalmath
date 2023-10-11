/**
 * 
 */
package dev.projects.math.linalgebra;


import dev.projects.utils.exception.LoggableException;

/**
 * @author vyn
 * 
 */
public interface ICloneable<T> extends Cloneable
{
    T deepCopy() throws LoggableException;
}
