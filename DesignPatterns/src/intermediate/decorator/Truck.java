package intermediate.decorator;

public class Truck extends CarDecorator{

	public Truck(Car car) {
		super(car); // 부모의 생성자를 호출하면서 전달
	}
	
	// 원래 Truck 클래스가 CarOne, CarTwo 를 통해 얻으려 했던 기능을 이용해서
	// 자신만의 기능을 완성
	@Override
	public String getDesc() {
//		return super.getDesc(); // 단순 호출은 자신만의 기능이 없는 상태
		return super.getDesc() + " Truck 기능 추가"; // 단순 호출은 자신만의 기능이 없는 상태
	}

	@Override
	public int getSpeed() {
		return super.getSpeed() - 20; // 속도를 더 줄인다.
	}
}
