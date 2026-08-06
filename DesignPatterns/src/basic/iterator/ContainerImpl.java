package basic.iterator;

// 다양한 타입의 객체를 여러 개 관리하는 컬렉션 역할  
public class ContainerImpl<T> implements Container<T> {
	
	// 컬렉션
	T[] array;
	
	// 개별 항목 T를 넘거나, 꺼내는 메소는 제외, array setter만 추가
	public void setArray(T[] array) {
		this.array = array;
	}
	
	@Override
	public Iterator<T> getIterator(){
		return new IteratorImpl();
	}

	// Iterator를 구현한 클래스
	private class IteratorImpl implements Iterator<T>{
		
		int index; // 초기값 0
		
		@Override
		public boolean hasNext() {
			return index < array.length;
		}
		
		// 사용자 코드에서 next()만 호출하는 경우
		// 사용자 코드에서 Container<T>() 후 next()만 호출하는 경우
		@Override
		public T next() {
			if (this.hasNext()) {
				return array[index++];
			}
			return null;
		}
	}
}
