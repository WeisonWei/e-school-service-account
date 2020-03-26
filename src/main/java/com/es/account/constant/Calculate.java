package com.es.account.constant;

import com.es.base.util.DataUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum Calculate {
    LEVEL1((count) -> count == 1, () -> DataUtils.mockFloatBetween(8.00f, 10.00f)),
    LEVEL2((count) -> 2 <= count, () -> DataUtils.mockFloatBetween(1.00f, 7.00f));

    @Getter
    private Predicate<Long> predicate;

    @Getter
    private Supplier<Float> supplier;


    Calculate(Predicate<Long> predicate, Supplier<Float> supplier) {
        this.predicate = predicate;
        this.supplier = supplier;
    }

    public static Float getRedEnvelopeAmount(Long count) {

        return Arrays.stream(Calculate.values())
                .filter(calculate -> calculate.getPredicate().test(count))
                .findAny()
                .map(calculate -> calculate.getSupplier().get())
                .orElse(0.00f);
    }
}
