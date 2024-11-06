package com.github.estebangmz666.controller;

import java.util.HashMap;
import java.util.Map;

import com.github.estebangmz666.model.Graph;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;

public class GraphController {

    @FXML
    private TextField nodeNameField;

    @FXML
    private TextField edgeWeightField;

    @FXML
    private Pane graphPane;

    @FXML
    private Label messageLabel;

    private Graph graph;
    private Map<String, Circle> nodeVisuals;
    @SuppressWarnings("unused")
    private Map<String, Text> nodeLabels;
    private Circle selectedNode;  // Nodo seleccionado para crear una arista
    private Circle startNode;  // Nodo de inicio para el algoritmo
    private Circle endNode;    // Nodo de destino para el algoritmo
    private int nodeCount = 0;
    private boolean isAddingEdge = false; // Indica si estamos en modo de agregar arista

    public GraphController() {
        graph = new Graph();
        nodeVisuals = new HashMap<>();
        nodeLabels = new HashMap<>();
    }

    @FXML
    private void handleAddNode() {
        String nodeName = nodeNameField.getText();
        if (nodeName != null && !nodeName.isEmpty() && !nodeVisuals.containsKey(nodeName)) {
            graph.addNode(nodeName);

            // Crear la representación visual del nodo como un StackPane
            Circle nodeCircle = new Circle(20, Color.BLUE);
            Text nodeLabel = new Text(nodeName);
            nodeLabel.setFill(Color.WHITE); // Cambiar el color del texto para que sea visible sobre el nodo

            // Crear un StackPane para contener el círculo y el texto juntos
            StackPane nodeStack = new StackPane();
            nodeStack.getChildren().addAll(nodeCircle, nodeLabel);

            // Calcular la disposición en forma de panal
            int row = nodeCount / 5;         // Número de fila
            int col = nodeCount % 5;         // Número de columna

            double x = 100 + col * 60;       // Ajusta la separación horizontal
            double y = 100 + row * 52;       // Ajusta la separación vertical

            // Ajuste para filas alternadas (estilo hexagonal)
            if (col % 2 == 1) {
                y += 26;  // Desplaza las filas alternadas hacia abajo
            }

            nodeStack.setLayoutX(x - nodeCircle.getRadius());
            nodeStack.setLayoutY(y - nodeCircle.getRadius());

            // Agregar el evento de clic para seleccionar el nodo
            nodeStack.setOnMouseClicked(event -> handleNodeClick(nodeName, nodeCircle));

            // Agregar nodo al mapa y al panel
            nodeVisuals.put(nodeName, nodeCircle);
            graphPane.getChildren().add(nodeStack);

            nodeNameField.clear();
            nodeCount++;
        }
    }

    private boolean doesEdgeIntersectNode(double startX, double startY, double endX, double endY) {
        for (Circle node : nodeVisuals.values()) {
            double centerX = node.getParent().getLayoutX() + node.getRadius();
            double centerY = node.getParent().getLayoutY() + node.getRadius();
            
            // Calcular la distancia del centro del nodo a la línea
            double distance = Math.abs((endY - startY) * centerX - (endX - startX) * centerY + endX * startY - endY * startX)
                            / Math.sqrt(Math.pow(endY - startY, 2) + Math.pow(endX - startX, 2));
    
            // Comprobar si la distancia es menor al radio del nodo (indica intersección)
            if (distance < node.getRadius() && !(Math.abs(centerX - startX) < 1 && Math.abs(centerY - startY) < 1)
                && !(Math.abs(centerX - endX) < 1 && Math.abs(centerY - endY) < 1)) {
                return true;
            }
        }
        return false;
    }    

    private void handleNodeClick(String nodeName, Circle nodeCircle) {
        if (isAddingEdge) {
            if (selectedNode == null) {
                selectedNode = nodeCircle;
                nodeCircle.setFill(Color.RED); // Cambia de color para indicar selección
            } else {
                if (!selectedNode.equals(nodeCircle)) {
                    try {
                        int weight = Integer.parseInt(edgeWeightField.getText());
    
                        // Añadir la arista en el modelo del grafo
                        String selectedNodeName = getNodeNameByCircle(selectedNode);
                        graph.addEdge(selectedNodeName, nodeName, weight);
    
                        // Obtener el StackPane de los nodos seleccionados
                        StackPane startNodeStack = (StackPane) selectedNode.getParent();
                        StackPane endNodeStack = (StackPane) nodeCircle.getParent();
    
                        double startX = startNodeStack.getLayoutX() + selectedNode.getRadius();
                        double startY = startNodeStack.getLayoutY() + selectedNode.getRadius();
                        double endX = endNodeStack.getLayoutX() + nodeCircle.getRadius();
                        double endY = endNodeStack.getLayoutY() + nodeCircle.getRadius();
    
                        // Decidir entre línea recta y curva
                        if (doesEdgeIntersectNode(startX, startY, endX, endY)) {
                            // Crear una curva si la línea intersecta un nodo
                            QuadCurve edgeCurve = new QuadCurve();
                            edgeCurve.setStartX(startX);
                            edgeCurve.setStartY(startY);
                            edgeCurve.setEndX(endX);
                            edgeCurve.setEndY(endY);
    
                            // Calcular el punto de control para la curva
                            double controlX = (startX + endX) / 2;
                            double controlY = (startY + endY) / 2 - 50; // Ajusta -50 para desviar la curva hacia arriba
    
                            edgeCurve.setControlX(controlX);
                            edgeCurve.setControlY(controlY);
                            edgeCurve.setStroke(Color.BLACK);
                            edgeCurve.setFill(null); // Deja el interior vacío
    
                            // Añadir texto con el peso de la arista en el centro de la curva
                            Text weightLabel = new Text(controlX, controlY - 10, String.valueOf(weight));
    
                            // Asegurarse de que la curva y el peso se agreguen detrás de los nodos
                            graphPane.getChildren().add(0, edgeCurve);
                            graphPane.getChildren().add(0, weightLabel);
                        } else {
                            // Crear una línea recta si no intersecta ningún nodo
                            Line edge = new Line();
                            edge.setStartX(startX);
                            edge.setStartY(startY);
                            edge.setEndX(endX);
                            edge.setEndY(endY);
    
                            // Añadir texto con el peso de la arista en el centro de la línea
                            Text weightLabel = new Text((startX + endX) / 2, (startY + endY) / 2, String.valueOf(weight));
    
                            // Asegurarse de que la línea y el peso se agreguen detrás de los nodos
                            graphPane.getChildren().add(0, edge);
                            graphPane.getChildren().add(0, weightLabel);
                        }
    
                        // Restablecer el estado de selección
                        selectedNode.setFill(Color.BLUE);
                        selectedNode = null;
                        edgeWeightField.clear();
                        messageLabel.setText("Arista agregada entre " + selectedNodeName + " y " + nodeName + " con peso " + weight);
                    } catch (NumberFormatException e) {
                        messageLabel.setText("Por favor, ingrese un número válido para el peso de la arista.");
                    }
                }
            }
        } else {
            if (startNode == null) {
                startNode = nodeCircle;
                nodeCircle.setFill(Color.GREEN);
            } else if (endNode == null && startNode != nodeCircle) {
                endNode = nodeCircle;
                nodeCircle.setFill(Color.ORANGE);
            } else {
                resetNodeSelection();
            }
        }
    }    

    private void resetNodeSelection() {
        if (startNode != null) startNode.setFill(Color.BLUE);
        if (endNode != null) endNode.setFill(Color.BLUE);
        startNode = null;
        endNode = null;
    }

    @FXML
    private void handleDijkstra() {
        if (startNode != null && endNode != null) {
            String startNodeName = getNodeNameByCircle(startNode);
            String endNodeName = getNodeNameByCircle(endNode);
            Map<String, Integer> distances = graph.dijkstra(startNodeName);

            Integer distance = distances.get(endNodeName);
            if (distance != null) {
                messageLabel.setText("Distancia mínima (Dijkstra) de " + startNodeName + " a " + endNodeName + ": " + distance);
            } else {
                messageLabel.setText("No hay un camino entre " + startNodeName + " y " + endNodeName + ".");
            }
            resetNodeSelection();
        }
    }

    @FXML
    private void handleFloydWarshall() {
        if (startNode != null && endNode != null) {
            String startNodeName = getNodeNameByCircle(startNode);
            String endNodeName = getNodeNameByCircle(endNode);
            Map<String, Map<String, Integer>> allDistances = graph.floydWarshall();

            Integer distance = allDistances.getOrDefault(startNodeName, new HashMap<>()).get(endNodeName);
            if (distance != null && distance != Integer.MAX_VALUE) {
                messageLabel.setText("Distancia mínima (Floyd-Warshall) de " + startNodeName + " a " + endNodeName + ": " + distance);
            } else {
                messageLabel.setText("No hay un camino entre " + startNodeName + " y " + endNodeName + ".");
            }
            resetNodeSelection();
        }
    }

    @FXML
    private void enableAddEdgeMode() {
        // Alternar el modo de agregar arista
        isAddingEdge = !isAddingEdge;
        resetNodeSelection();  // Resetea la selección de nodos para los algoritmos
        if (isAddingEdge) {
            messageLabel.setText("Modo de agregar arista activado. Seleccione dos nodos.");
        } else {
            messageLabel.setText("Modo de agregar arista desactivado.");
            selectedNode = null; // Desselecciona cualquier nodo seleccionado
        }
    }

    private String getNodeNameByCircle(Circle circle) {
        for (Map.Entry<String, Circle> entry: nodeVisuals.entrySet()) {
            if (entry.getValue().equals(circle)) {
                return entry.getKey();
            }
        }
        return null;
    }
}