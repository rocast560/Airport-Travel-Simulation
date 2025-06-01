//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: ArrayStack which implements a stack ADT with a array which is used to keep track of the routes in Flights.java
// 
//

import java.util.Arrays;

/** An array-based implementation of the ADT stack.
 * @param <T> The type of elements stored in this stack
 */
public final class ArrayStack<T> implements StackInterface<T> {

	private T[] stack;	// Array of stack entries
	private int topIndex;	// Index of top entry
	private boolean integrityOK;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;

	/**
	 * Creates an empty stack with different capacity.
	 */
	public ArrayStack() {
		this(DEFAULT_CAPACITY);
	}

	/** Creates an empty stack with a given capacity.
	 * @param initialCapacity The inital capacity of the stack
	 */
	public ArrayStack(int initialCapacity) {
		integrityOK = false;
		checkCapacity(initialCapacity);

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
		integrityOK = true;
	}

	/**
	 * Adds a new entry to the top of this stack.
	 * @param newEntry An object to be added to the stack
	 */
	@Override
	public void push(T newEntry) {
		checkIntegrity();
		ensureCapacity();
		stack[topIndex+1] = newEntry;
		++topIndex;
	}

	/**
	 * Removes and returns this stack's top entry.
	 * @return The object at the top of the stack
	 * @throws RuntimeException if the stack is empty
	 */
	@Override
	public T pop() {
		checkIntegrity();
		if (topIndex == -1) {
			throw new RuntimeException("Stack is empty!");
		}

		T topEntry = stack[topIndex];
		stack[topIndex] = null;
		--topIndex;
		return topEntry;
	}

	/**
	 * Retrieves this stack's top entry.
	 * @return The object at the top of the stack
	 * @throws RuntimeException if the stack is empty
	 */
	@Override
	public T peek() {
		checkIntegrity();
		if (topIndex == -1) {
			throw new RuntimeException("Stack is empty!");
		}

		return stack[topIndex];
	}

	/**
	 * Detects whether this stack is empty.
	 * @return True if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		return topIndex < 0;
	}

	/**
	 * Removes all entries from this stack.
	 */
	@Override
	public void clear() {
		checkIntegrity();

		// Remove references to the objects in the stack but not to deallocate the whole array
		for (int i = topIndex; i >= 0; --i) {
			stack[i] = null;
		}
		topIndex = -1;
	}

	/**
	 * Ensures that the stack has enough capacity.
	 */
	private void ensureCapacity() {
		if (topIndex == stack.length-1) {
			int newLength = 2 * stack.length;	// Multiplies the space available in the stack by a factor of two.
			checkCapacity(newLength);	// Check if the new capacity is greater than the MAX_CAPACITY given.
			stack = Arrays.copyOf(stack, newLength);
		}
	}

	/**
	 * Throws an exception if this object is not initalized.
	 */
	private void checkIntegrity() {
		if (!integrityOK) {
			throw new SecurityException("ArrayStack object is corrupt.");
		}
	}

	/**
	 * Throws an exception if the client request a capacity that is too large.
	 * @param capacity The requested capacity
	 */
	private void checkCapacity(int initialCapacity) {
		if (initialCapacity > MAX_CAPACITY) {
			throw new IllegalStateException("Attemp to create a stack whoses capacity exceeds allowed maximum capacity of " + MAX_CAPACITY);
		}
	}

}
