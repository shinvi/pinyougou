package com.pinyougou.common.util;

/**
 * @author 邱长海
 */
@FunctionalInterface
public interface Function<E> {
    void invoke(E e);
}
