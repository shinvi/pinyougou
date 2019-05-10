package com.pinyougou.common.util;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 邱长海
 */
public class ObjectUtils {

    public static <E> E with(E e, Function<E> fun) {
        fun.invoke(e);
        return e;
    }

    public static <E extends AutoCloseable> void autoClose(E e, Function<E> fun) {
        fun.invoke(e);
    }

    public static <E extends AutoCloseable, T> T autoClose(E e, Function2<E, T> fun) {
        return fun.invoke(e);
    }

    public static boolean in(Object source, Object... other) {
        if (other == null) {
            return false;
        }
        Set<Object> others = new HashSet<>(Arrays.asList(other));
        return others.contains(source);
    }

}
