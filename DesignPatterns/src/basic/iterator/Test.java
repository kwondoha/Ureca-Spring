package basic.iterator;

// Iterator Pattern
// 여러 개 항목을 가진 컬렉션을 순회하면서 각각의 항목을 참조
// 직접 컬렉션의 index를 이용하는 방식보다는 안전하게 순회하는 Iterator를 함께 구현해서 순회, 참조
public class Test {

//	// #1. 패턴 미적용 
//	// Java api가 제공하는 collections를 사용하지 않을 때 
//	public static void main(String[] args) {
//		String[] strArray = {"Hello", "Patterns", "Iterator"};
//		for (int i = 0; i < strArray.length; i++) {
//			System.out.println(strArray[i]);
//		}
//	}

	// #2. 패턴 적용 
	public static void main(String[] args) {
		String[] strArray = {"Hello", "Patterns", "Iterator"};
		ContainerImpl<String> container = new ContainerImpl<>();
		container.setArray(strArray);
		Iterator<String> iter = container.getIterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
	
}

