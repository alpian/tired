package com.ind.tired.web.json;

import com.google.common.collect.ImmutableList;

import java.net.URI;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class Page<S, T> {
    public final S self;
    public final Iterable<Link> links;
    public final Iterable<T> entries;

    public Page(S self, Iterable<Link> links, Iterable<T> entries) {
        this.self = self;
        this.links = links;
        this.entries = entries;
    }

    public static <S, T> Page<S, T> of(S self, URI base, int index, int itemsPerPage, Collection<T> entries) {
        final ImmutableList.Builder<Link> links = new ImmutableList.Builder<Link>().add(
                Link.self(base),
                Link.head(base, itemsPerPage, entries.size()));
        if (entries.size() > 0) {
            links.add(Link.first(base), Link.last(base, entries.size()));
        }

        final int nextIndex = index + itemsPerPage;
        if (nextIndex < entries.size()) {
            links.add(Link.next(base, nextIndex, entries.size(), itemsPerPage));
        }
        return new Page<>(self, links.build(), entries.stream().skip(index).limit(itemsPerPage).collect(toList()));
    }
}
