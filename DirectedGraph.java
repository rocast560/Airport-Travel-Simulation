//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: DirectedGraph implements GraphInterface and gets the cheapest path 
// 
//

import java.util.Iterator;

public class DirectedGraph<T> implements GraphInterface<T> {
	
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;

	public DirectedGraph() {
		vertices = new HashedDictionary<>();
		edgeCount = 0;
	}	// end default constructor

	/**
	 * Adds a given vertex to this graph.
	 * @param vertexLabel An object that labels the new vertex and is distinct from the labels of current vertices.
	 * @return True if the vertex is added, or false if not.
	 */
	@Override
	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null;	// Was addition to dictionary successful?
	}	// end addVertex

	/**
	 * Adds an unweighted edge between two given distinct vertices that are currently in this graph.
	 * The desired edge must not already be in the graph. In a directed graph, the edge points toward
	 * the second vertex given.
	 * @param begin An object that labels the origin vertex of the edge.
	 * @param end An object, distinct from begin, that labels the end vertex of the edge.
	 * @return True if the edge is added, or false if not.
	 */
	@Override
	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	}	// end addEdge

	/**
	 * Adds a weighted edge between two given distinct vertices that are currently in this graph.
	 * The desired edge must not already be in the graph. In a directed graph, the edge points toward
	 * the second vertex given.
	 * @param begin An object that labels the origin vertex of the edge.
	 * @param end An object, distinct from begin, that labels the end vertex of the edge.
	 * @param edgeWeight The real value of the edge's weight.
	 * @return True if the edge is added, or false if not.
	 */
	@Override
	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean result = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);	// getValue gets the individual vertex
		VertexInterface<T> endVertex = vertices.getValue(end);
		if ((beginVertex != null) && (endVertex != null)) {
			result = beginVertex.connect(endVertex, edgeWeight);
		}

		if (result) {
			edgeCount++;
		}

		return result;
	}	// end addEdge

	/**
	 * Sees whether an edge exists between two given vertices.
	 * @param begin An object that labels the origin vertex of the edge.
	 * @param end An object that labels the end vertex of the edge.
	 * @return True if an edge exists.
	 */
	@Override
	public boolean hasEdge(T begin, T end) {
		boolean found = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		if ((beginVertex != null) && (endVertex != null)) {
			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();

			while (!found && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor)) {
					found = true;
				}	
			}	// end while
		}	// end if

		return found;
	}	// end hasEdge

	/**
	 * Sees whether this graph is empty.
	 * @return True if the graph is empty.
	 */
	@Override
	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	/**
	 * Gets the number of vertices in this graph.
	 * @return The number of vertices in the graph.
	 */
	@Override
	public int getNumberOfVertices() {
		return vertices.getSize();
	}	// end getNumberOfVertices
	
	/**
	 * Gets the number of edges in this graph.
	 * @return The number of edges in the graph.
	 */
	@Override
	public int getNumberOfEdges() {
		return edgeCount;
	}	// end getNumberOfEdges

	/**
	 * Removes all vertices and edges from this graph resulting in an empty graph.
	 */
	@Override
	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}

	/** Preforms a breadth-first traversal of this graph.
	 * @param origin An object that labels the origin vertex of the traversal.
	 * @return A queue of labels of the vertices in the traversal, with the label
	 * of the origin vertex at the queue's front. */
	@Override
	public QueueInterface<T> getBreadthFirstTraversal(T origin) {
		throw new UnsupportedOperationException();
	}

	/** Preforms a depth-first traversal of this graph.
	 * @param origin An object that labels the origin vertex of the traversal.
	 * @return A queue of labels of the vertices in the traversal, with the label of the origin vertex at the queue's front. */
	@Override
	public QueueInterface<T> getDepthFirstTraversal(T origin) {
		throw new UnsupportedOperationException();
	}

	/** Preforms a topological sort of the vertices in this graph without cycles.
	 * @return A stack of vertex labels in topological order, beginning with the stack's top. */
	@Override
	public StackInterface<T> getTopologicalOrder() {
		throw new UnsupportedOperationException();
	}

	/** Finds the shortest-length path between two given vertices in this graph.
	 * @param begin An object that labels the path's origin vertex.
	 * @param end An object that labels the path's destination vertex.
	 * @param path A stack of labels that is empty initially; at the completion of the method, this stack contains the 
	 * 	labels of the vertices along the shortest path; the label of the origin vertex is at the top, and the 
	 * 	label of the desination vertex is at the bottom.
	 * @return The length of the shortest path. */
	@Override
	public int getShortestPath(T begin, T end, StackInterface<T> path) {
		throw new UnsupportedOperationException();
	}

	/** Finds the least-cost path between two given vertices in this graph.
	 * @param begin An object that labels the path's origin vertex.
	 * @param end An object that labels the path's destination vertex.
	 * @param pat A stack of labels that is empty initially; at the completion of the method, this stack contains the labels of the vertices along
	 * the cheapest path; the label of the origin vertex is at the top, and the label of the destination vertex is at the bottom.
	 * @return The cost of the cheapest path. */
	@Override
	public double getCheapestPath(T begin, T end, StackInterface<T> path) {
		resetVertices();
		boolean done = false;

		PriorityQueueInterface<EntryPQ> vertexQueue = new MinHeapPriorityQueue<>();
		VertexInterface<T> originVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		vertexQueue.add(new EntryPQ(originVertex, 0.0, null));
		while (!done && !vertexQueue.isEmpty()) {
			EntryPQ frontEntry = vertexQueue.remove();
			VertexInterface<T> frontVertex = frontEntry.getVertex();

			if (!frontVertex.isVisited()) {
				frontVertex.visit();
				frontVertex.setCost(frontEntry.getCost());
				frontVertex.setPredecessor(frontEntry.getPredecessor());

				if (frontVertex.equals(endVertex)) {
					done = true;
				} else {

					Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
					Iterator<Double> weights = frontVertex.getWeightIterator();

					while (neighbors.hasNext()) {
						VertexInterface<T> nextNeighbor = neighbors.next();
						double weightOfEdgeToNeighbor = weights.next();

						if (!nextNeighbor.isVisited()) {
							double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
							vertexQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
						}
					}	
				}
			}

		}

		if (!endVertex.isVisited()) {
			return -1;
		}

		double pathCost = endVertex.getCost();
		path.push(endVertex.getLabel());
		VertexInterface<T> vertex = endVertex;

		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}

		return pathCost;
	}

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

		while(vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		}	// end while
	}	// end resetVertices

	private class EntryPQ implements Comparable<EntryPQ> {

		private VertexInterface<T> predecessor;
		private VertexInterface<T> vertexLabel;
		private double cost;
		
		public EntryPQ(VertexInterface<T> vertexLabel, double cost, VertexInterface<T> predecessor) {
			this.vertexLabel = vertexLabel;
			this.predecessor = predecessor;
			this.cost = cost;
		}

		@Override
		public int compareTo(EntryPQ predecessor) {
			return (int) Math.signum(this.cost - predecessor.cost);	// Changes the compare to we get the minimium
		}

		public double getCost() {
			return cost;
		}

		public VertexInterface<T> getVertex() {
			return vertexLabel;
		}
		
		public VertexInterface<T> getPredecessor() {
			return predecessor;
		}
	}

}	// end DirectedGraph
