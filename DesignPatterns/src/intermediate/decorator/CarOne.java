package intermediate.decorator;

public class CarOne implements Car{

	@Override
	public String getDesc() {
		return "Car One";
	}

	@Override
	public int getSpeed() {
		return 80;
	}

}
