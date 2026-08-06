package basic.adapter;

// Adapter Pattern
// 기존의 코드가 존재하고 새로운 코드를 작성할 때, 기존 코드를 버리지 않고 재활용, 레거시코드 + Adapter 방식
public class Test {
	
	// #1. 패턴 미적용
//	public static void main(String[] args) {
//		LegacyFunc legacyFunc = new LegacyFuncImpl();
//		System.out.println(legacyFunc.calc(10));
//	}

	// #2. 패턴 적용
		public static void main(String[] args) {
			LegacyFunc legacyFunc = new LegacyFuncImpl();
			LegacyFuncAdapter adapter = new LegacyFuncAdapterImpl(legacyFunc);
			System.out.println(adapter.calc(10));
	}
}
