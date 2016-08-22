package com.ind.tired.util;


import com.google.common.collect.ImmutableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

public class Strings {
    public static Collection<String> allPrefixes(String s) {
        if (s.length() == 1) {
            return ImmutableList.of(s);
        }
        final List<String> list = new ArrayList<>(s.length());
        list.add(s);
        list.addAll(allPrefixes(s.substring(0, s.length() - 1)));
        return list;
    }

    public static Set<String> splitAndTrim(String input, String separator) {
        return stream(input.split(separator)).map(String::trim).collect(toSet());
    }

    public static String cut(String s, int startIndex, int length) {
        return s.substring(startIndex, startIndex + length);
    }

    public static String slurp(InputStream input) {
        final StringBuilder s = new StringBuilder();
        try(BufferedReader r = new BufferedReader(new InputStreamReader(input))) {
            String line = r.readLine();
            while (line != null) {
                s.append(line).append('\n');
                line = r.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s.toString();
    }

    public static String charAt(String s, int index) {
        return s.substring(index, index + 1);
    }
}
