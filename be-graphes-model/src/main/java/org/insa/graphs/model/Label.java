package org.insa.graphs.model;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;


public class Label implements Comparable<Label>{
	
	private Node currentNode;
	private boolean marque;
	private double cost;
	private Arc pere;
	
    public Label(Node node) {
        this.currentNode = node;
        marque = false;
        cost = Double.POSITIVE_INFINITY;
        pere = null;
    }

	
	public Label(Node current, boolean marque, double cost, Arc pere) {
		this.currentNode = current;
		this.marque = marque;
		this.cost = cost;
		this.pere = pere;
	}
	
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double newCost) {
		this.cost = newCost;
	}
	
	public void setCost(double newCost, Arc pred) {
		this.cost = newCost;
		this.pere = pred;
	}


	public Node getCurrentNode() {
		return currentNode;
	}


	public boolean isMarque() {
		return marque;
	}
	
	public void mark() {
		this.marque = true;
	}


	public Arc getPere() {
		return pere;
	}
	
	public int compareTo(Label l) {
		int comp = Double.compare(cost, l.getCost());
		if(comp == 0 && this.getClass() == LabelStar.class && l.getClass() == LabelStar.class)
			return Double.compare(((LabelStar)this).getEstimatedCost(), ((LabelStar)l).getEstimatedCost());
		else
			return comp;
	}
}
