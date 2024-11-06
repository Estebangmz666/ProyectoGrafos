package com.github.estebangmz666.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {
    private Map<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addNode(String name) {
        nodes.putIfAbsent(name, new Node(name));
    }

    public void addEdge(String from, String to, int weight) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);
        if (fromNode != null && toNode != null) {
            fromNode.addNeighbor(toNode, weight);
            toNode.addNeighbor(fromNode, weight);
        }
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    // Algoritmo de Dijkstra
    public Map<String, Integer> dijkstra(String startName) {
        Map<String, Integer> distances = new HashMap<>();
        for (String nodeName : nodes.keySet()) {
            distances.put(nodeName, Integer.MAX_VALUE);
        }
        distances.put(startName, 0);

        PriorityQueue<NodeDistance> queue = new PriorityQueue<>((a, b) -> a.distance - b.distance);
        queue.add(new NodeDistance(nodes.get(startName), 0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            Node currentNode = current.node;

            for (Map.Entry<Node, Integer> neighbor : currentNode.getNeighbors().entrySet()) {
                int newDist = current.distance + neighbor.getValue();
                String neighborName = neighbor.getKey().getName();
                if (newDist < distances.get(neighborName)) {
                    distances.put(neighborName, newDist);
                    queue.add(new NodeDistance(neighbor.getKey(), newDist));
                }
            }
        }
        return distances;
    }

    // Algoritmo de Floyd-Warshall
    public Map<String, Map<String, Integer>> floydWarshall() {
        Map<String, Map<String, Integer>> distances = new HashMap<>();
        for (String node1 : nodes.keySet()) {
            distances.putIfAbsent(node1, new HashMap<>());
            for (String node2 : nodes.keySet()) {
                if (node1.equals(node2)) {
                    distances.get(node1).put(node2, 0);
                } else {
                    distances.get(node1).put(node2, Integer.MAX_VALUE);
                }
            }
        }

        for (Node node : nodes.values()) {
            for (Map.Entry<Node, Integer> neighbor : node.getNeighbors().entrySet()) {
                distances.get(node.getName()).put(neighbor.getKey().getName(), neighbor.getValue());
            }
        }

        for (String k : nodes.keySet()) {
            for (String i : nodes.keySet()) {
                for (String j : nodes.keySet()) {
                    if (distances.get(i).get(k) != Integer.MAX_VALUE && distances.get(k).get(j) != Integer.MAX_VALUE) {
                        int newDist = distances.get(i).get(k) + distances.get(k).get(j);
                        if (newDist < distances.get(i).get(j)) {
                            distances.get(i).put(j, newDist);
                        }
                    }
                }
            }
        }
        return distances;
    }

    private class NodeDistance {
        Node node;
        int distance;
        NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public void removeNode(String nodeName) {
    }
}