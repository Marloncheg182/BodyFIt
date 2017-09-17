package com.goodguys.bodyfit.common;

import android.support.annotation.NonNull;

public class CheckNull {
    @FunctionalInterface
    public interface OnAction<T> {
        void onAction(T argument);
    }

    @FunctionalInterface
    public interface OnNull {
        void onNull();
    }

    /**
     *
     * @param argument argument to check
     * @param onAction if argument notNull
     * @param <T> argument Type
     */

    public static <T> void check(T argument, @NonNull OnAction<T> onAction) {
        if (argument != null) {
            onAction.onAction(argument);
        }
    }

    /**
     *
     * @param argument argument to check
     * @param onAction if argument notNull
     * @param onNull if argument null
     * @param <T> argument Type
     */
    public static <T> void check(T argument, @NonNull OnAction<T> onAction, @NonNull OnNull onNull) {
        if (argument != null) {
            onAction.onAction(argument);
        } else {
            onNull.onNull();
        }
    }
}
