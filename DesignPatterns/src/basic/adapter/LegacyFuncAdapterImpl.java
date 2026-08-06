package basic.adapter;

// Adapter 역할을 위해 LegacyFunc 구현체와 LegacyFunc 사용 코드 중간자 역할 
public class LegacyFuncAdapterImpl implements LegacyFuncAdapter {
	
	private final LegacyFunc legacyFunc;
	
	public LegacyFuncAdapterImpl(LegacyFunc legacyFunc) {
		this.legacyFunc = legacyFunc;
	}
	
	@Override
	public int calc(int num) {
		// legacyFunc 객체의 수행 결과를 얻는다
		int legacyData = legacyFunc.calc(num);
		
		// Adapter 역할 수행. 레거시데이터를 2차 가공
		return convertLegacyData(legacyData);
	}
	
	public int convertLegacyData(int legacyData) {
		return legacyData * 2;
	}
}
