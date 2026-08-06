package basic.observer;

public class SubscriberOne implements Observer{

	// 다양한 필드와 메소드들이 존재
	// 구독과 관련된 처리를 위해서 Observer 구현
	@Override
	public void update(Message message) {
		System.out.println("SubscriberOne 받은 메세지 : " + message.getMessageContent());
	}

}
