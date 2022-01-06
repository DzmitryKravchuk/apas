package com.belpost.apas.service.utils;

import static org.mockito.ArgumentMatchers.argThat;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.mockito.ArgumentMatcher;

public class InAnyOrderListMatcher implements ArgumentMatcher<Long[]> {

    private final Long[] expected;

    public InAnyOrderListMatcher(Long[] expected) {
        this.expected = expected;
    }


    public static Long[] inAnyOrderListMatcherEq(Long[] expected) {
        return argThat(new InAnyOrderListMatcher(expected));
    }


    @Override
    public boolean matches(Long[] actual) {
        List<Long> exp = Arrays.asList(expected);

        if (actual != null) {
            List<Long> actualList = Arrays.asList(actual);
            return CollectionUtils.isEqualCollection(exp, actualList);
        }

        return false;
    }
}
