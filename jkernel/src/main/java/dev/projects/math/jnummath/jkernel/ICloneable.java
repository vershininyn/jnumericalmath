package dev.projects.math.jnummath.jkernel;

/**
 * @author vyn
 * 
 */
public interface ICloneable<TCloneableOutput> extends Cloneable
{
    TCloneableOutput deepCopy();
}
