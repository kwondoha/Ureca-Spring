package intermediate.templatemethod;

//public interface Chef {
//	void prepareMaterials();
//	void followRecipes();
//	void offerFood();
//	void cleanUp();
//}

public abstract class Chef {
	// final 로 재정의 불가
	// cook 대표 메소드에 순서를 고정
	public final void cook() {
		prepareMaterials();
		followRecipes();
		offerFood();
		cleanUp();
	}
	
	abstract void prepareMaterials();
	abstract void followRecipes();
	abstract void offerFood();
	abstract void cleanUp();
}
