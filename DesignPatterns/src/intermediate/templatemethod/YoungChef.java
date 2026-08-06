package intermediate.templatemethod;

public class YoungChef extends Chef{

	// public -> default 로 호출 제한
	// 현재 우리코드가 같은 패키지에 있어서 호출 가능
	@Override
	void prepareMaterials() {
		System.out.println("YoungChef prepareMaterials");
	}
	
	@Override
	void followRecipes() {
		System.out.println("YoungChef followRecipes");
	}

	@Override
	void offerFood() {
		System.out.println("YoungChef offerFood");
	}

	@Override
	void cleanUp() {
		System.out.println("YoungChef cleanUp");
	}

}
