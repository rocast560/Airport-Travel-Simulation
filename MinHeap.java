import java.util.Arrays;

public final class MinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T>{

	private T[] heap;	// Array of heap entries; ignore heap[0]
	private int lastIndex;	// Index of last entry and number of entries
	private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;

	public MinHeap(int initialCapacity) {
		if (initialCapacity < DEFAULT_CAPACITY) {
			initialCapacity = DEFAULT_CAPACITY;
		} else {
			checkCapacity(initialCapacity);
		}

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
		heap = tempHeap;
		lastIndex = 0;
		integrityOK = true;
	}
	
	/** Adds a new entry to this heap.
	 *	@param newEntry	An object to be added. */
	@Override
	public void add(T newEntry) {
		checkIntegrity();

		if (newEntry == null) {
			throw new IllegalArgumentException("Cannot add null entry to the heap.");
		}

		int newIndex = lastIndex + 1;
		int parentIndex = newIndex / 2;

		while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0) {
			heap[newIndex] = heap[parentIndex];
			newIndex = parentIndex;
			parentIndex = newIndex / 2;
		}

		heap[newIndex] = newEntry;
		lastIndex++;
		ensureCapacity();
	}

	/** Removes and returns the smallest item in this heap.
	 *	@return	Either the smallest object in the heap or,
	 *	if the heap is empty before the operation, null. */
	@Override
	public T removeMin() {
		checkIntegrity();
		T root = null;

		if (!isEmpty()) {
			root = heap[1];
			heap[1] = heap[lastIndex];
			heap[lastIndex] = null;
			lastIndex--;
			reheap(heap, 1, lastIndex);
		}

		return root;
	}
	
	/** Retrieves the smallest item in this heap.
	 *	@return	Either the smallest object in the heap or,
	 *	if the heap is empty, null. */
	@Override
	public T getMin() {
		T root = null;
		if (!isEmpty()) {
			root = heap[1];
		}

		return root;
	}	

	/** Detects whether this heap is empty.
	 *	@return	True if the heap is empty, or false otherwise. */
	@Override
	public boolean isEmpty() {
		return lastIndex < 1;	// set to 0 by default when its first initalized as empty, if it becomes that again or is that.. its empty
	}

	/** Gets the size of this heap.
	 *	@return	The number of entries currently in the heap. */
	@Override
	public int getSize() {
		return lastIndex;
	}

	/** Removes all entries from this heap. */
	@Override
	public void clear() {
		checkIntegrity();
		while (lastIndex > 0) {
			heap[lastIndex] = null;
			lastIndex--;
		}
		lastIndex = 0;
	}

	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY) {
			throw new IllegalStateException("Attempt to create a MinHeap whoses capacity exceeds allowed maximum capacity of " + MAX_CAPACITY);
		}
	}

	private void checkIntegrity(){
		if(!integrityOK){
			throw new SecurityException("Heap is corrupted.");
		}
	}

	private void ensureCapacity() {
		if (lastIndex >= heap.length - 1) {
			int newCapacity = 2 * (heap.length - 1);

			if (newCapacity + 1 > MAX_CAPACITY + 1) {
				newCapacity = MAX_CAPACITY;
			}
			checkCapacity(newCapacity);

			heap = Arrays.copyOf(heap, newCapacity + 1);
		}
	}

	private static <T extends Comparable<? super T>> void reheap(T[] heap, int rootIndex, int lastIndex) {
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = 2 * rootIndex;	// left child is 2i and not 2i + 1 which is the right child... this is the case for min heap

		while (!done && (leftChildIndex <= lastIndex)) {
			int smallerChildIndex = leftChildIndex;
			int rightChildIndex = leftChildIndex + 1;
			
			if ((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[smallerChildIndex]) < 0) {
				smallerChildIndex = rightChildIndex;	
			}

			if (orphan.compareTo(heap[smallerChildIndex]) > 0) {
				heap[rootIndex] = heap[smallerChildIndex];
				rootIndex = smallerChildIndex;
				leftChildIndex = 2 * rootIndex;
			} else {
				done = true;
			}
				
			heap[rootIndex] = orphan;
		}
	}

}
