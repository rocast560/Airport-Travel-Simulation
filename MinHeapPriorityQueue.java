//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: MinHeapPriorityQueue implements the PriorityQueueInterface and has an inital capacity of 25
// 
//

public class MinHeapPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {

	private MinHeapInterface<T> minheap;

	public MinHeapPriorityQueue() {
		minheap = new MinHeap<>(25);
	}

	/** Adds a new entry to this priority queue.
	 * @param newEntry An object to be added.
	 */
	@Override
	public void add(T newEntry) {
		minheap.add(newEntry);
	}

	/** Removes and returns the entry having the highest priority.
	 * @return Either the object having the highest priority or, 
	 * if the priority queue is empty before the operation, null.
	 */
	@Override
	public T remove() {
		return minheap.removeMin();
	}

	/** Retrieves the entry having the highest priority.
	 * @return Either the object having the highest priority or, if the priority queue is empty, null.
	 */
	@Override
	public T peek() {
		return minheap.getMin();
	}

	/** Detects whether this priority queue is empty.
	 * @return True if the priority queue is emtpy, or false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return minheap.isEmpty();
	}

	/** Gets the size of this priority queue.
	 * @return The number of entries currently in the priority queue.
	 */
	@Override
	public int getSize() {
		return minheap.getSize();
	}

	/** Removes all entries from this priority queue. */
	@Override
	public void clear() {
		minheap.clear();
	}
	
}
