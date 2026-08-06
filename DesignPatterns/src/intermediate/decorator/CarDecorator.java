package intermediate.decorator;

// 스스로는 객체를 생성 X
//
public abstract class CarDecorator implements Car{
	private final Car car;
	
	public CarDecorator(Car car) {
		this.car = car;
	}
	
	@Override
	public String getDesc() {
		return car.getDesc();
	}

	@Override
	public int getSpeed() {
		return car.getSpeed();
	}	
}
