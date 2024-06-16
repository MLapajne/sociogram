package si.zitnik.sociogram.gui.graph.jung;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.collections15.Transformer;

import si.zitnik.sociogram.gui.graph.GraphUtil;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public class SocLayout implements Layout<Vertex, Edge> {
	public static final int margin = 30;
	private double width;
	private double height;
	private Graph<Vertex, Edge> graph;
	@SuppressWarnings("unused")
	private GraphUtil graphUtil;
	HashMap<Vertex, Point2D> vertexToPoint2D;
	HashMap<Vertex, Double> vertexToRadius;
	
	public SocLayout(Graph<Vertex, Edge> graph, GraphUtil graphUtil, int size) {
		this.graph = graph;
		this.graphUtil = graphUtil;
		this.width = size;
		this.height = size;
		this.setSize(new Dimension(size, size));
		initialize();
	}

	@Override
	public Graph<Vertex, Edge> getGraph() {
		return this.graph;
	}

	@Override
	public Dimension getSize() {
		return new Dimension((int)width, (int)height);
	}

	@Override
	public void initialize() {
		if (this.graph.getVertexCount() == 0){
			return;
		}
		
		double radiusInc = (this.getSize().getWidth()-2*SocLayout.margin)*1.0/2/5;
		//double[] radiuses = new double[]{radiusInc, radiusInc*2, radiusInc*3, radiusInc*4, radiusInc*5};
		//int[] maxVertexesOnRadius = new int[]{getMaxVertexes(radiuses[0]), getMaxVertexes(radiuses[1]), getMaxVertexes(radiuses[2]),
		//		getMaxVertexes(radiuses[3]), getMaxVertexes(radiuses[4])};
		
		//for each input count we have number of vertexes
		HashMap<Integer, HashSet<Vertex>> vertexesForInputCount = getVertexesForInputCount();
		
		ArrayList<ArrayList<Vertex>> inputCountGroups = new ArrayList<ArrayList<Vertex>>();
		for (int i = 0; i<5; i++){
			inputCountGroups.add(new ArrayList<Vertex>());
		}
		{
			int maxInputCount = Integer.MIN_VALUE;
			int minInputCount = Integer.MAX_VALUE;
			for (Integer inputCount : vertexesForInputCount.keySet()) {
				if (inputCount < minInputCount){
					minInputCount = inputCount;
				}
				if (inputCount > maxInputCount){
					maxInputCount = inputCount;
				}
			}
			inputCountGroups.get(4).addAll(vertexesForInputCount.get(minInputCount));
			if (minInputCount != maxInputCount)
				inputCountGroups.get(0).addAll(vertexesForInputCount.get(maxInputCount));
			vertexesForInputCount.remove(maxInputCount);
			vertexesForInputCount.remove(minInputCount);
			
		}
		//normalize to middle 3 circles
		ArrayList<Integer> inputCounts = new ArrayList<Integer>();
		inputCounts.addAll(vertexesForInputCount.keySet());
		Collections.sort(inputCounts);
		int third = inputCounts.size()/3;
		for (int i = 0; i<third; i++){
			inputCountGroups.get(1).addAll(vertexesForInputCount.get(inputCounts.get(0)));
			vertexesForInputCount.remove(inputCounts.get(0));
			inputCounts.remove(0);
		}
		for (int i = 0; i<third; i++){
			inputCountGroups.get(2).addAll(vertexesForInputCount.get(inputCounts.get(0)));
			vertexesForInputCount.remove(inputCounts.get(0));
			inputCounts.remove(0);
		}
		while (!inputCounts.isEmpty()) {
			inputCountGroups.get(3).addAll(vertexesForInputCount.get(inputCounts.get(0)));
			vertexesForInputCount.remove(inputCounts.get(0));
			inputCounts.remove(0);
		}

		
		if (vertexesForInputCount.keySet().size() > 0) {
			try {
				throw new Exception("ERROR: Did not process all vertexes!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		//graph is supposed to be represented in box shape
		double radius = radiusInc;
		double centerX = this.getSize().getWidth()*1.0/2;
		double centerY = this.getSize().getHeight()*1.0/2;
		vertexToPoint2D = new HashMap<Vertex, Point2D>();
		vertexToRadius = new HashMap<Vertex, Double>();
		int countGroupIdx = 0;
		for (Collection<Vertex> countGroup : inputCountGroups) {
			int numOfVertexesOnCircle = countGroup.size();
			double fi = 2.0/numOfVertexesOnCircle*Math.PI;
			double tempFi = countGroupIdx*0.5;
			for (Vertex vertex : countGroup) {
				Point2D point = new Point((int)(centerX+radius*Math.cos(tempFi)), (int)(centerY-radius*Math.sin(tempFi)));
				vertexToPoint2D.put(vertex, point);
				vertexToRadius.put(vertex, radius);
				tempFi += fi;
			}
			countGroupIdx++;
			//System.out.println(radius);
			radius += radiusInc;
		}
	}

	

	@SuppressWarnings("unused")
	private int getMaxVertexes(double radius) {
		return (int) Math.floor(2*Math.PI*radius/15);
	}

	@Override
	public boolean isLocked(Vertex arg0) {
		try {
			throw new Exception("ERROR: Not implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void lock(Vertex arg0, boolean arg1) {
		try {
			throw new Exception("ERROR: Not implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		try {
			throw new Exception("ERROR: Not implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setGraph(Graph<Vertex, Edge> graph) {
		this.graph = graph;
	}

	@Override
	public void setInitializer(Transformer<Vertex, Point2D> arg0) {
		try {
			throw new Exception("ERROR: Not implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setLocation(Vertex vertex, Point2D point) {
		//we are calculating in EAST NORTH
		
		//x says two new points
		//y says if lower or upper
		double p1 = point.getX()-this.getSize().getWidth()/2;
		double p2 = point.getY()-this.getSize().getHeight()/2;
		if (Math.abs(p1) > vertexToRadius.get(vertex)){
			if (p1<0){
				p1 = -1*vertexToRadius.get(vertex);
			} else {
				p1 = vertexToRadius.get(vertex);
			}
		}
		if (Math.abs(p2) > vertexToRadius.get(vertex)){
			if (p2<0){
				p2 = -1*vertexToRadius.get(vertex);
			} else {
				p2 = vertexToRadius.get(vertex);
			}
		}
		
		double newx = p1;
		double newy = Math.sqrt(vertexToRadius.get(vertex)*vertexToRadius.get(vertex)/(1+p1*p1/p2/p2));
		
		if (p2 < 0) {
			newy = -1*newy;
		}
		
		Point2D newPoint = new Point((int)(newx+this.getSize().getWidth()/2),(int)(newy+this.getSize().getHeight()/2));
		
		this.vertexToPoint2D.put(vertex, newPoint);		
	}

	@Override
	public void setSize(Dimension dim) {
		this.width = dim.getWidth();
		this.height = dim.getHeight();		
	}

	@Override
	public Point2D transform(Vertex vertex) {
		//System.out.println(this.vertexToPoint2D.get(vertex));
		return this.vertexToPoint2D.get(vertex);
	}
	
	private HashMap<Integer, HashSet<Vertex>> getVertexesForInputCount() {
		HashMap<Integer, HashSet<Vertex>> retVal = new HashMap<Integer, HashSet<Vertex>>();
		
		for (Vertex vertex : this.graph.getVertices()) {
			int inputCount = this.graph.inDegree(vertex);
			if (retVal.get(inputCount) == null){
				retVal.put(inputCount, new HashSet<Vertex>());
			}
			retVal.get(inputCount).add(vertex);
		}
		
		return retVal;
	}

}
