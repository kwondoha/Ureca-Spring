package intermediate.decorator;

// Decorator Pattern
// 이미 있는 객체의 클래스를 수정 X, 기능을 추가하는 패턴
// 어떤 클래스이 기능이 필요하면 상속 고려할 수 있지만, 본질적으로 같은 IS A 관계가 생기고 coupling 이 심해 진다.

// Car 를 구현한 CarOne, CarTwo 가 있을 때 이 두 클래스에 Truck 기능과 SportsCar 기능을 추가 하려고 한다.
// 상속을 사용하면 CarOneTruck, CarOneSportsCar, CarTwoTruck, CarTwoSportsCar.....기본 차 종류 X 추가 기능 => 하의 클래스가 증가

// CarDecorator 는 Car 인터페이스를 구현, 동시에 Car 필드로 가진다.
public class Test {

	public static void main(String[] args) {
		Car carOne = new CarOne();
		System.out.println(carOne.getDesc() + " " + carOne.getSpeed());
		
		Car carTwo = new CarTwo();
		System.out.println(carTwo.getDesc() + " " + carTwo.getSpeed());
		
		// 난 CarOne + Truck 하지만 상속 X
		Car truckOne = new Truck(carOne);
		System.out.println(truckOne.getDesc() + " " + truckOne.getSpeed());
		
		// 난 CarTwo + Truck 하지만 상속 X
		Car truckTwo = new Truck(carTwo);
		System.out.println(truckTwo.getDesc() + " " + truckTwo.getSpeed());		
	}

}
