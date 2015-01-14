package gtd.util;

import java.util.Iterator;

@SuppressWarnings({"unchecked", "cast"})
public class HashSet<E> implements Iterable<E>{
	private Entry<E>[] elements;

	private int hashMask;
	private int bitSize;
	
	private int threshold;
	private int load;
	
	public HashSet(){
		super();
		
		int nrOfEntries = 1 << bitSize;
		
		hashMask = nrOfEntries - 1;
		
		elements = (Entry<E>[]) new Entry[nrOfEntries];
		
		threshold = nrOfEntries;
		load = 0;
	}
	
	private void rehash(){
		int nrOfElements = 1 << (++bitSize);
		int newHashMask = nrOfElements - 1;
		
		Entry<E>[] oldElements = elements;
		Entry<E>[] newElements = (Entry<E>[]) new Entry[nrOfElements];
		
		Entry<E> currentEntryRoot = new Entry<E>(null, 0, null);
		Entry<E> shiftedEntryRoot = new Entry<E>(null, 0, null);
		
		int oldSize = oldElements.length;
		for(int i = oldSize - 1; i >= 0; --i){
			Entry<E> e = oldElements[i];
			if(e != null){
				Entry<E> lastCurrentEntry = currentEntryRoot;
				Entry<E> lastShiftedEntry = shiftedEntryRoot;
				int lastPosition = -1;
				do{
					int position = e.hash & newHashMask;
					
					if(position == i){
						if(position != lastPosition) lastCurrentEntry.next = e;
						lastCurrentEntry = e;
					}else{
						if(position != lastPosition) lastShiftedEntry.next = e;
						lastShiftedEntry = e;
					}
					
					e = e.next;
				}while(e != null);
				
				lastCurrentEntry.next = null;
				lastShiftedEntry.next = null;
				
				newElements[i] = currentEntryRoot.next;
				newElements[i | oldSize] = shiftedEntryRoot.next;
			}
		}
		
		threshold <<= 1;
		elements = newElements;
		hashMask = newHashMask;
	}
	
	private void ensureCapacity(){
		if(load > threshold){
			rehash();
		}
	}
	
	public boolean add(E element){
		ensureCapacity();
		
		int hash = element.hashCode();
		int position = hash & hashMask;
		
		Entry<E> currentStartEntry = elements[position];
		if(currentStartEntry != null){
			Entry<E> entry = currentStartEntry;
			do{
				if(hash == entry.hash && entry.element.equals(element)){
					return false;
				}
			}while((entry = entry.next) != null);
		}
		
		elements[position] = new Entry<E>(element, hash, currentStartEntry);
		++load;

		return true;
	}
	
	public void addUnsafe(E element){
		ensureCapacity();
		
		int hash = element.hashCode();
		int position = hash & hashMask;
		
		elements[position] = new Entry<E>(element, hash, elements[position]);
		++load;
	}
	
	public boolean remove(E element){
		int hash = element.hashCode();
		int position = hash & hashMask;
		
		Entry<E> previous = null;
		Entry<E> currentStartEntry = elements[position];
		if(currentStartEntry != null){
			Entry<E> entry = currentStartEntry;
			do{
				if(hash == entry.hash && entry.element.equals(element)){
					if(previous == null){
						elements[position] = entry.next;
					}else{
						previous.next = entry.next;
					}
					--load;
					return true;
				}
				
				previous = entry;
			}while((entry = entry.next) != null);
		}
		return false;
	}
	
	public boolean contains(E element){
		int hash = element.hashCode();
		int position = hash & hashMask;
		
		Entry<E> entry = elements[position];
		while(entry != null){
			if(hash == entry.hash && element.equals(entry.element)) return true;
			
			entry = entry.next;
		}
		
		return false;
	}
	
	public void clear(){
		elements = (Entry<E>[]) new Entry[elements.length];
		
		load = 0;
	}
	
	public static class Entry<E>{
		public final int hash;
		public final E element;
		public Entry<E> next;
		
		public Entry(E element, int hash, Entry<E> next){
			super();
			
			this.element = element;
			this.hash = hash;
			this.next = next;
		}
	}
	
	public Iterator<E> iterator(){
		return new ElementIterator<E>(this);
	}
	
	private static class ElementIterator<E> implements Iterator<E>{
		private final Entry<E>[] data;
		
		private Entry<E> current;
		private int index;
		
		public ElementIterator(HashSet<E> hashSet){
			super();
			
			data = hashSet.elements;

			index = data.length - 1;
			current = new Entry<E>(null, -1, data[index]);
			locateNext();
		}
		
		private void locateNext(){
			Entry<E> next = current.next;
			if(next != null){
				current = next;
				return;
			}
			
			for(int i = index - 1; i >= 0 ; i--){
				Entry<E> entry = data[i];
				if(entry != null){
					current = entry;
					index = i;
					return;
				}
			}
			
			current = null;
			index = 0;
		}
		
		public boolean hasNext(){
			return (current != null);
		}
		
		public E next(){
			if(!hasNext()) throw new UnsupportedOperationException("There are no more elements in this iterator.");
			
			E element = current.element;
			locateNext();
			
			return element;
		}
		
		public void remove(){
			throw new UnsupportedOperationException("This iterator doesn't support removal.");
		}
	}
}
