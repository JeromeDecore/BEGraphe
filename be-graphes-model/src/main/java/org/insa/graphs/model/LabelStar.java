package org.insa.graphs.model;

import org.insa.graphs.model.Node;



public class LabelStar extends Label {
	
	private final double estimatedCost;
	
	public LabelStar(Node current, double estimatedCost) {
		super(current);
		this.estimatedCost = estimatedCost;
	}
	
    public double getTotalCost() {
        return super.getCost() + getEstimatedCost();
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

}
