package com.github.estebangmz666.model;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private String name;
    private Map<Node, Integer> neighbors;

    public Node(String name) {
        this.name = name;
        this.neighbors = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addNeighbor(Node node, int weight) {
        neighbors.put(node, weight);
    }

    public Map<Node, Integer> getNeighbors() {
        return neighbors;
    }
}