package basic.observer;

import java.util.ArrayList;
import java.util.List;

// 메세지를 보내는 쪽
public class Publisher implements Subject{
	// 메세지를 받는 Observer 들
	private List<Observer> observers = new ArrayList<>();

	@Override
	public void attach(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void detach(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyUpdate(Message message) {
		for (Observer observer : observers) {
			observer.update(message);
		}
	}
}
