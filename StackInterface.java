//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: StackInterface is an interface with method specifications on methods required to implement a stack
// 
//

import java.util.EmptyStackException;

public interface StackInterface<T> {

	/** 
	 * Adds a new entry to the top of this stack.
	 * @param newEntry An object to be added to the stack.
	 */
	public void push(T newEntry);

	/** 
	 * Removes and returns this satck's top entry.
	 * @return The object at the top of the stack.
	 * @throws EmptyStackException if the stack is empty before the operation.
	 */
	public T pop();

	/** 
	 * Retrieves this stack's top entry.
	 * @return The object at the top of the stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public T peek();

	/** 
	 * Detects whether this stack is empty.
	 * @return True if the stack is empty.
	 */
	public boolean isEmpty();

	/** 
	 * Removes all entries from this stack. 
	 */
	public void clear();
}
