package com.es.account.config;

@FunctionalInterface
public interface TiFunction<T, U, Z, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param z the third function argument
     * @return the function result
     */
    R apply(T t, U u, Z z);
}
