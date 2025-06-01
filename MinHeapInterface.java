//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: MinHeapInterface specifies the methods that are needed to be implemented to use the minheap
// 
//

public interface MinHeapInterface<T extends Comparable<? super T>> {

	/** Adds a new entry to this heap.
	 *	@param newEntry	An object to be added. */
	public void add(T newEntry);

	/** Removes and returns the smallest item in this heap.
	 *	@return	Either the smallest object in the heap or,
	 *	if the heap is empty before the operation, null. */
	public T removeMin();

	/** Retrieves the smallest item in this heap.
	 *	@return	Either the smallest object in the heap or,
	 *	if the heap is empty, null. */
	public T getMin();

	/** Detects whether this heap is empty.
	 *	@return	True if the heap is empty, or false otherwise. */
	public boolean isEmpty();

	/** Gets the size of this heap.
	 *	@return	The number of entries currently in the heap. */
	public int getSize();

	/** Removes all entries from this heap. */
	public void clear();

} // end MinHeapInterface
