import java.util.Iterator;
public interface DictionaryInterface<K,V> {

	/**
	 * Adds a new entry to this dictionary. If the given key already exists, 
	 * replaces the corresponding value and returns the original value.
	 * 
	 * @param key   The key of the entry to be added.
	 * @param value The value associated with the key.
	 * @return The value that was associated with the key before the new value
	 *         was added, or null if the key was not in the dictionary.
	 */
	public V add(K key, V value);

	/**
	 * Removes a specific entry from this dictionary.
	 * 
	 * @param key The key of the entry to be removed.
	 * @return The value that was associated with the key, 
	 *         or null if the key was not in the dictionary.
	 */
	public V remove(K key);

	/**
	 * Retrieves the value associated with a given key.
	 * 
	 * @param key The key to search for.
	 * @return The value associated with the key, 
	 *         or null if the key is not in the dictionary.
	 */
	public V getValue(K key);

	/**
	 * Determines whether a given key is in this dictionary.
	 * 
	 * @param key The key to search for.
	 * @return True if the key is in the dictionary, false otherwise.
	 */
	public boolean contains(K key);
	
	/**
	 * Creates an iterator that traverses all keys in this dictionary.
	 * 
	 * @return An iterator that provides sequential access to the keys in the dictionary.
	 */
	public Iterator<K> getKeyIterator();

	/**
	 * Creates an iterator that traverses all values in this dictionary.
	 * 
	 * @return An iterator that provides sequential access to the values in the dictionary.
	 */
	public Iterator<V> getValueIterator();

	/**
	 * Determines whether this dictionary is empty.
	 * 
	 * @return True if the dictionary is empty, false otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Gets the number of entries in this dictionary.
	 * 
	 * @return The number of entries in the dictionary.
	 */
	public int getSize();

	/**
	 * Removes all entries from this dictionary.
	 */
	public void clear();
}	// end DictionaryInterface
