package com.ind.tired.util;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class StringsTest {
    @Test public void
    gets_all_prefixes() {
        assertThat(Strings.allPrefixes("abcde"), contains("abcde", "abcd", "abc", "ab", "a"));
    }
}
