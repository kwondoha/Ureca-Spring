package basic.iterator;

public interface Iterator<T> {
	boolean hasNext(); // 컬렉션에서 현재 항목 다음 항목이 있는지 확인
	T next(); // 다음 항목 참조

}
