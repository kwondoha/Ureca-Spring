package basic.iterator;

// Iterator의 대상이 되는 Collection
// generic T는 Iterator와 Collection이 공유함
public interface Container<T> {
	Iterator<T> getIterator();
}
