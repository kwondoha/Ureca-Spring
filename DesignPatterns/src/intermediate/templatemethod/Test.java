package intermediate.templatemethod;

// Template Method Pattern
// 어떤 일의 전체 순서( 알고리즘 내용)는 상위 클래스에 모아 두고 ( 여러개의 메소드가 존재 )
// 메소드의 실행 순서는 바꾸지 못하고, 개별 메소드의 행위 (내용) 만 변경할 수 있도록 강제
// 요리 순서를 예시로 코드를 작성
public class Test {

	public static void main(String[] args) {
		Chef youngChef = new YoungChef();
		youngChef.cook();
		
		Chef oldChef = new OldChef();
		oldChef.cook();
		
//		youngChef.followRecipes();
//		youngChef.cleanUp();
		// 요리사가 기본적으로 수행할 메소드는 정해졌지만, 순서를 지키지 못하는 형태

	}

}
