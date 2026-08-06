package intermediate.decorator;

public class CarTwo implements Car{

	@Override
	public String getDesc() {
		return "Car Two";
	}

	@Override
	public int getSpeed() {
		return 50;
	}
}
