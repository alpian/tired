package com.ind.tired.functional;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.ind.tired.web.json.util.JsonMiner.find;
import static com.ind.tired.web.json.util.JsonMiner.list;
import static com.ind.tired.web.json.util.JsonMiner.read;
import static com.ind.tired.web.json.util.JsonMiner.string;

final class PageIterator implements Iterator<List<JsonNode>> {
    private final RestActions actions;
    private Optional<JsonNode> node;

    public PageIterator(RestActions actions, JsonNode node) {
        this.actions = actions;
        this.node = Optional.of(node);
    }

    @Override
    public boolean hasNext() {
        return node.map(n -> list(n, "entries").size() > 0).orElse(false);
    }

    private Optional<JsonNode> nextNode() {
        return node.flatMap(n -> find(list(n, "links"), j -> string(j, "rel").equals("next")));
    }

    @Override
    public List<JsonNode> next() {
        final List<JsonNode> entries = list(node.get(), "entries");
        node = nextNode().map(n -> {
            final String href = string(n, "href");
            try {
                return read(actions.get(href).content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        return entries;
    }
}
