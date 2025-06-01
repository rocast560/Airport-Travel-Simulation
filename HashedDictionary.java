//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: HashedDictionary class which implements DictionaryInterface and uses linear probing to resolve collisions
// 
//

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K,V> implements DictionaryInterface<K,V> {

	private int numberOfEntries;
	private int collisionCount;
	private static final int DEFAULT_CAPACITY = 444;
	private static final int MAX_CAPACITY = 10000;
	private int tableSize;
	private Entry<K,V>[] hashTable;

	private boolean integrityOK = false;

	protected final Entry<K,V> AVAILABLE = new Entry<K,V>(null, null);

	public HashedDictionary() {
		this(DEFAULT_CAPACITY);
	}
	
	public HashedDictionary(int initialCapacity) {
		checkCapacity(initialCapacity);
		numberOfEntries = 0;
		collisionCount = 0;
		tableSize = initialCapacity;
		@SuppressWarnings("unchecked")
		Entry<K,V>[] temp =(Entry<K,V>[]) new Entry[tableSize];
		hashTable = temp;
		integrityOK = true;
	}
	
	public int getCollisionCount() {
		return collisionCount;	
	}

	/**
	 * Retrieves the value associated with a given key.
	 * 
	 * @param key The key to search for.
	 * @return The value associated with the key, 
	 *         or null if the key is not in the dictionary.
	 */
	@Override
	public V getValue(K key) {
		checkIntegrity();
		
		if (key == null) {
			throw new IllegalArgumentException("Cannot get value for null key");
		}
		
		int index = getHashIndex(key);
		
		for (int counter = 0; counter < hashTable.length; counter++) {
			if (hashTable[index] == null) {
				return null;
			}
			
			if (hashTable[index] != AVAILABLE && key.equals(hashTable[index].getKey())) {
				return hashTable[index].getValue();
			}
		
			index = (index + 1) % hashTable.length;
		}
		
		return null;
	}

	/**
	 * Adds a new entry to this dictionary. If the given key already exists, 
	 * replaces the corresponding value and returns the original value.
	 * 
	 * @param key   The key of the entry to be added.
	 * @param value The value associated with the key.
	 * @return The value that was associated with the key before the new value
	 *         was added, or null if the key was not in the dictionary.
	 */
	@Override
	public V add(K key, V value) {
		if ((key == null) || (value == null)) {
			throw new IllegalArgumentException("'key' or 'value' is null. Not allowed!'");
		}

		int hashIndex = getHashIndex(key);
		V oldValue = null;

		boolean duplicateFound = false;

		for (int index = getHashIndex(key); index < hashTable.length && !duplicateFound; index++) {
			if (hashTable[index] != null && key.equals(hashTable[index].getKey())) {
				duplicateFound = true;
			}
		}

		// Check for collision and handle with linear probing if needed
		if (hashTable[hashIndex] != null && ((!key.equals(hashTable[hashIndex].getKey()))) && !duplicateFound) {
			hashIndex = linearProbe(hashIndex, key);
			collisionCount++;
		}

		// Add new entry or update existing one
		if ((hashTable[hashIndex] == null) && !duplicateFound) {
			hashTable[hashIndex] = new Entry<K,V>(key, value);
			numberOfEntries++;
		} else {
			oldValue = hashTable[hashIndex].getValue();
			hashTable[hashIndex].setValue(value);
		}

		if (numberOfEntries >= hashTable.length * 0.75) {
			enlargeHashTable();
		}

		return oldValue;
	}
	
	private int linearProbe(int index, K key) {
		boolean found = false;
		int availableStateIndex = -1;	// Index of first element in the available state

		while (!found && (hashTable[index] != null)) {	// Search while we haven't found something or the hashTable hasn't come across an open spot
			if (!hashTable[index].equals(AVAILABLE)) {
				if (key.equals(hashTable[index].getKey())) {
					found = true;
				} else {
					index = (index + 1) % hashTable.length;
				}
			} else {
				if (availableStateIndex == -1) {
					availableStateIndex = index;
				}
				index = (index + 1) % hashTable.length;		
			}
		}

		if (found || (availableStateIndex == -1)) {
			return index;			// Index of either key or null
		} else {
			return availableStateIndex;	// Index of an available index
		}
	}

	/**
	 * Creates an iterator that traverses all keys in this dictionary.
	 * 
	 * @return An iterator that provides sequential access to the keys in the dictionary.
	 */
	@Override
	public Iterator<K> getKeyIterator() {	
		return new KeyIterator();
	}

	/**
	 * Creates an iterator that traverses all values in this dictionary.
	 * 
	 * @return An iterator that provides sequential access to the values in the dictionary.
	 */
	@Override
	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	/**
	 * Removes a specific entry from this dictionary.
	 * 
	 * @param key The key of the entry to be removed.
	 * @return The value that was associated with the key, 
	 *         or null if the key was not in the dictionary.
	 */
	@Override
	public V remove(K key) {
		throw new UnsupportedOperationException("remove() method is not a supported operation.");
	}

	/**
	 * Determines whether a given key is in this dictionary.
	 * 
	 * @param key The key to search for.
	 * @return True if the key is in the dictionary, false otherwise.
	 */
	@Override
	public boolean contains(K key) {
		throw new UnsupportedOperationException("contains() method is not a supported operation.");
	}

	/**
	 * Determines whether this dictionary is empty.
	 * 
	 * @return True if the dictionary is empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException("isEmpty() method is not a supported operation.");
	}

	/**
	 * Gets the number of entries in this dictionary.
	 * 
	 * @return The number of entries in the dictionary.
	 */
	@Override
	public int getSize() {
		throw new UnsupportedOperationException("getSize() method is not a supported operation.");
	}

	/**
	 * Removes all entries from this dictionary.
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear() method is not a supported operation.");
	}

	private int getHashIndex(K key) {
		int hashIndex = key.hashCode() % hashTable.length;
		if (hashIndex < 0) {
			hashIndex = hashIndex + hashTable.length;
		}
		return hashIndex;
	}

	private boolean isPrime(int num) {
		if (num <= 1) return false;
		if (num <= 3) return true;

		if (num % 2 == 0 || num % 3 == 0) return false;
		
		for (int i = 5; i * i <= num; i = i + 6) {
			if (num % i == 0 || num % (i + 2) == 0)
			return false;
		}

		return true;
	}

	private int getNextPrime(int num) {
		if (num <= 1) {
			return 2;
		}

		int prime = num;
		boolean found = false;

		while (!found) {
			prime++;

			if (isPrime(prime)) {
				found = true;
			}
		}

		return prime;
	}

	private void enlargeHashTable() {
		Entry<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(oldSize + oldSize);
		checkCapacity(newSize);

		// The cast is safe because the new array contains null entries.
		@SuppressWarnings("unchecked")
		Entry<K, V>[] temp = (Entry<K, V>[])new Entry[newSize];
		hashTable = temp;
		numberOfEntries = 0;	// Reset number of dictionary entries, siunce it will be incremented by add during rehash

		// Rehash dictionary entries from old array to the new and bigger array;
		// skip elements that contain null or AVAILABLE
		for (int index = 0; index < oldSize; index++) {
			if ((oldTable[index] != null) && oldTable[index] != AVAILABLE) {
				add(oldTable[index].getKey(), oldTable[index].getValue());
			}
		}	// end for
	}	// end enlargeHashTable

	private void checkCapacity(int initialCapacity) {
		if (initialCapacity > MAX_CAPACITY) {
			throw new IllegalStateException("Attemp to create a hash dictionary whoses capacity exceeds allowed maximum capacity of " + MAX_CAPACITY);
		}
	}

	private void checkIntegrity(){
		if(!integrityOK){
			throw new SecurityException("bag is corrupted");
		}
	}
	
	private class ValueIterator implements Iterator<V> {
		private int currentIndex;
		private int numberLeft;

		private ValueIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		}

		public boolean hasNext() {
			return numberLeft > 0;
		}

		public V next() {
			V result = null;

			if (hasNext()) {
				// Loop until we find occupied hash table entry
				while (hashTable[currentIndex] == null || hashTable[currentIndex] == AVAILABLE) {
					currentIndex++;
				}

				result = hashTable[currentIndex].getValue();
				numberLeft--;
				currentIndex++;
			} else {
				throw new NoSuchElementException();
			}

			return result;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove is not supported by this iterator");
		}
	}

	private class KeyIterator implements Iterator<K> {
		private int currentIndex;
		private int numberLeft;

		private KeyIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		}

		public boolean hasNext() {
			return numberLeft > 0;
		}

		public K next() {
			K result = null;
			if (hasNext()) {
				// Loop until we find occupied hash table entry  
				while (hashTable[currentIndex] == null || hashTable[currentIndex] == AVAILABLE) {	
					currentIndex++;
				}

				result = hashTable[currentIndex].getKey();
				numberLeft--;
				currentIndex++;
			} else {
				throw new NoSuchElementException();
			}

			return result;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove is not supported by this iterator");
		}
		
	}

	protected final class Entry<KE,VE> {
		private KE key;
		private VE value;

		public Entry(KE key, VE value) {
			this.key = key;
			this.value = value;
		}

		public KE getKey() {
			return key;
		}

		public VE getValue() {
			return value;
		}

		public void setValue(VE value) {
			this.value = value;
		}
	}

}
