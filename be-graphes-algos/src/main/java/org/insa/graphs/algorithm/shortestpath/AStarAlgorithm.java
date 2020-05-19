package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.insa.graphs.model.GraphStatistics.NO_MAXIMUM_SPEED;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected List<Label> generateLabels(ShortestPathData data) {
        List<Node> nodes = data.getGraph().getNodes();
        List<Label> labels = new ArrayList<>();
        int maxSpeed = getMaxSpeed(data);
        for (Node node : nodes) {
            double estimatedCost;
            // if the cost is the distance, take straight line distance as heuristic
            if (data.getMode() == AbstractInputData.Mode.LENGTH) {
                estimatedCost = Point.distance(node.getPoint(), data.getDestination().getPoint());

            } else {    // if the cost is the time, multiply straight line distance with the maximum speed
                estimatedCost = Point.distance(node.getPoint(), data.getDestination().getPoint()) / maxSpeed;
            }
            labels.add(new LabelStar(node, estimatedCost));
        }
        return labels;
    }

    private int getMaxSpeed(ShortestPathData data) {
        int dataMaxSpeed = data.getMaximumSpeed();
        int graphMaxSpeed = data.getGraph().getGraphInformation().getMaximumSpeed();
        if (dataMaxSpeed == NO_MAXIMUM_SPEED && graphMaxSpeed == NO_MAXIMUM_SPEED)
            return (int) (130/3.6);
        if (dataMaxSpeed != NO_MAXIMUM_SPEED && graphMaxSpeed != NO_MAXIMUM_SPEED)
            return (int) (Math.min(dataMaxSpeed, graphMaxSpeed)/3.6);
        if (dataMaxSpeed != NO_MAXIMUM_SPEED)
            return (int) (dataMaxSpeed/3.6);
        return (int) (graphMaxSpeed/3.6);
    }
    

}
