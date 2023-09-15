package com.Algorithms;

import java.util.Iterator;

/**
 * Design and implement a PeekingIterator that support the peek() operation.
 * Peek at the element that will be returned by the next call to next().
 */
public class PeekingIterator implements Iterator<Integer> {
    Iterator<Integer> iterator;
    Integer cache = null;
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iterator = iterator;
        if (this.iterator.hasNext()) {
            cache = this.iterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        return cache != null;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer nextElement = cache;
        if (iterator.hasNext())
            cache = iterator.next();
        else
            cache = null;
        return nextElement;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return cache;
    }
}

//Add skip iterator class
