package com.pinyougou.common.util;

/**
 * @author 邱长海
 */
@FunctionalInterface
public interface Function2<E, T> {
    T invoke(E e);
}
