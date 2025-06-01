//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: A class of vertices for a graph which implements VertexInterface
// 
//

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.LinkedList;
/** A class of vertices for a graph. */
class Vertex<T> implements VertexInterface<T> {
	private T label;
	private List<Edge> edgeList;	// Edges to neighbors
	private boolean visited;	// True if visited
	private VertexInterface<T> previousVertex;	// On path to this vertex
	private double cost;	// Of path to vertex

	public Vertex(T vertexLabel) {
		label = vertexLabel;
		edgeList = new LinkedList<>();
		visited = false;
		previousVertex = null;
		cost = 0;
	}	// end constructor

	/** Gets this vertex's label.
	 * @return The object that labels the vertex.
	 */
	@Override
	public T getLabel() {
		return label;
	}

	/** Marks this vertex as visited. */
	@Override
	public void visit() {
		visited = true;
	}

	/** Removes this vertex's visited mark. */
	@Override
	public void unvisit() {
		visited = false;
	}

	/** Sees whether the vertex is marked as visited.
	 * @return True if the vertex is visited.
	 */
	@Override
	public boolean isVisited() {
		return visited;
	}

	/** Connects this vertex and given vertex with an unweighted edge.
	 * The two vertices cannot be the same, and must not already have
	 * this edge between them. In a directed graph, the edge points towards the
	 * given vertex.
	 * @param endVertex A vertex in the graph that ends the edge.
	 * @return True if the edge is added, or false if not.
	 */
	@Override
	public boolean connect(VertexInterface<T> endVertex) {
		return connect(endVertex, 0);
	}

	/** Connects this vertex and a given vertex with a weighted edge.
	 * The two vertices cannot be the same, and must not already have 
	 * this edge between them. In a directed graph, the edge points
	 * towards the given vertex.
	 * @param endVertex A vertex in the graph that ends the edge.
	 * @param edgeWeight A real-value edge weight, if any.
	 * @return True if the edge is added, or false if not.
	 */
	@Override
	public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
		boolean result = false;

		if (!this.equals(endVertex)) {	// Vertices are distinct

			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean duplicateEdge = false;

			while (!duplicateEdge && neighbors.hasNext()) {	// looping while there the vertex isn't null and there isn't a duplicate
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor)) {
					duplicateEdge = true;
				}
			}	// end while

			if (!duplicateEdge) {
				edgeList.add(new Edge(endVertex, edgeWeight));
				result = true;
			}	// end if
		}	// end if

		return result;
	}	// end connect

	/** Creates an iterator of this vertex's neighbors by following 
	 * all edges that begin at this vertex.
	 * @return An iterator of the neighboring vertices of this vertex.
	 */
	@Override
	public Iterator<VertexInterface<T>> getNeighborIterator() {
		return new NeighborIterator();
	}	// end getNeighborIterator

	/** Creates an iterator of the weights of the edges to thisthis
	 * vertex's neighbors.
	 * @return An iterator of edge weights of the edges to this vertex's neighbors.
	 */
	@Override
	public Iterator<Double> getWeightIterator() {
		return new WeightIterator();
	}	// end getNeighborIterator

	/** Sees whether this vertex has at least one neighbor.
	 * @return True if the vertex has a neighbor.
	 */
	@Override
	public boolean hasNeighbor() {
		return !edgeList.isEmpty();
	}

	/** Gets an unvisited neighbor, if any, of this vertex.
	 * @return Either a vertex that is an unvisted neighbor or null if no such
	 * neighbor exists.
	 */
	@Override
	public VertexInterface<T> getUnvistedNeighbor() {
		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
		while (neighbors.hasNext() && (result == null)) {
			VertexInterface<T> nextNeighbor = neighbors.next();
			if (!nextNeighbor.isVisited()) {	// If vertex isn't visited we update the result and the 
								// case where result being null will no longer pass.
				result = nextNeighbor;
			}
		}	// end while

		return result;
	}	// end getUnvistedNeighbor

	/** Records the previous vertex on a path to this vertex.
	 * @param predecessor The vertex previous to this one along a path.
	 */
	@Override
	public void setPredecessor(VertexInterface<T> predecessor) {
		previousVertex = predecessor;
	}

	/** Gets the recorded predecessor of this vertex.
	 * @return Either this vertex's predecessor or null if no predecessor was recorded.
	 */
	@Override
	public VertexInterface<T> getPredecessor() {
		return previousVertex;
	}

	/** Sees whether a predecessor was recorded for this vertex.
	 * @return True if a predecessor was recorded.
	 */
	@Override
	public boolean hasPredecessor() {
		return previousVertex != null;
	}

	/** Records the cost of a path to this vertex.
	 * @param newCost The cost of the path.
	 */
	@Override
	public void setCost(double newCost) {
		cost = newCost;
	}

	/** Gets the recorded cost of the path to this vertex.
	 * @return The cost of the path.
	 */
	@Override
	public double getCost() {
		return cost;	
	}

	private class WeightIterator implements Iterator<Double> {
		private Iterator<Edge> edges;

		private WeightIterator() {
			edges = edgeList.iterator();
		}	// end default constructor

		public boolean hasNext() {
			return edges.hasNext();
		}	// end hasNext

		public Double next() {
			Double nextWeight = 0.0;

			if (edges.hasNext()) {
				Edge edgeToNextWeight = edges.next();
				nextWeight = edgeToNextWeight.getWeight();
			} else {
				throw new NoSuchElementException();
			}

			return nextWeight;
		}	// end next

		public void remove() {
			throw new UnsupportedOperationException("remove() is not implemented by this iterator.");
		}	// end remove
	}

	private class NeighborIterator implements Iterator<VertexInterface<T>> {
		private Iterator<Edge> edges;

		private NeighborIterator() {
			edges = edgeList.iterator();	// edgeList is a 'LinkedList' which has a private iterator class
		}	// end default constructor

		public boolean hasNext() {
			return edges.hasNext();
		}	// end hasNext

		public VertexInterface<T> next() {
			VertexInterface<T> nextNeighbor = null;

			if (edges.hasNext()) {
				Edge edgeToNextNeighbor = edges.next();
				nextNeighbor = edgeToNextNeighbor.getEndVertex();	// Updates the vertex to return with each call
			} else {
				throw new NoSuchElementException();
			}

			return nextNeighbor;
		}	// end next

		public void remove() {
			throw new UnsupportedOperationException("remove() is not implemented by this iterator.");
		}	// end remove
	}

	protected class Edge {
		private VertexInterface<T> vertex;	// Vertex at end of edge
		private double weight;
		
		protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
			vertex = endVertex;
			weight = edgeWeight;
		}	// end constructor

		protected Edge(VertexInterface<T> endVertex) {
			vertex = endVertex;
			weight = 0;
		}	// end constructor

		protected VertexInterface<T> getEndVertex() {
			return vertex;
		}	// end getEndVertex

		protected double getWeight() {
			return weight;
		}	// end getWeight
	}	// end Edge
}	// end Vertex
