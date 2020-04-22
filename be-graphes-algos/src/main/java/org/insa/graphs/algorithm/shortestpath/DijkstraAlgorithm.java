package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.algorithm.utils.PriorityQueue;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	final ShortestPathData data = getInputData();
    	Graph graph = data.getGraph();
        
    	final int nbNodes = graph.size();
    	final List<Node> nodes = graph.getNodes();
    
        ShortestPathSolution solution = null;
        
        //Binary heap for labels
        PriorityQueue<Label> heap = new BinaryHeap<>();
        		
        //Initialize array of labels with the nodes of the graph
        Label[] labels = new Label[nbNodes];
        
        //Initialization
        double inf = Double.POSITIVE_INFINITY;
        for(Node node : nodes) {
        	labels[node.getId()] = new Label(node, false, inf, null);
        }
        
        Node s = data.getOrigin();
        labels[s.getId()].setCost(0);
        heap.insert(labels[s.getId()]);
        
        while (!(heap.isEmpty())) {
        	Label currentLabel = heap.deleteMin();
        	currentLabel.mark();
        	Node currentNode = currentLabel.getCurrentNode();
        	
        	for(Arc arc : currentNode.getSuccessors()) {
        		Node dest = arc.getDestination();
        		Label destLabel = labels[dest.getId()];
        		if(!destLabel.isMarque() && data.isAllowed(arc)) {
        			double arcCost = data.getCost(arc);
        			double newCost = currentLabel.getCost() + arcCost;
        			double oldCost = destLabel.getCost();
        			if(newCost < oldCost) {
        				try {
        					heap.remove(destLabel);
        				} catch(ElementNotFoundException ignored) {}
        				destLabel.setCost(newCost, arc);
        				heap.insert(destLabel);
        				if(Double.isInfinite(oldCost))
        					notifyNodeReached(dest);
        			}
        			
        		}
        	}
        }
        Node desti = data.getDestination();
        Label destiLabel = labels[desti.getId()];
        
        if(Double.isInfinite(destiLabel.getCost())) {
        	return new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        }
        
        notifyDestinationReached(desti);
        
        List<Arc> path = new ArrayList<>();
        Label currentLabel = labels[desti.getId()];
        while(!currentLabel.getCurrentNode().equals(s)) {
        	Arc arc = currentLabel.getPere();
        	path.add(arc);
        	currentLabel = labels[arc.getOrigin().getId()];
        }
        
        Collections.reverse(path);
        
        return new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, new Path(graph, path));
    }

}
