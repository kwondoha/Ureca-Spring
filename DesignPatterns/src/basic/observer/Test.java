package basic.observer;

// Observer Pattern
// 객체의 변화에 대해 이를 구독하려는 구독자에게 알려주는 패턴
// 발행자(Publisher) 는 누가 구독중인지 목록만 관리, 구독자(Subscriber) 들은 알림을 받는 (받으면 처리하는 행위 ) 처리
public class Test {

	public static void main(String[] args) {
		// 서로 완전히 태생이 다른 구독자 3개 객체, 공통점은 Observer 구현
		SubscriberOne one = new SubscriberOne();
		SubscriberTwo two = new SubscriberTwo();
		SubscriberThree three = new SubscriberThree();
		
		Publisher publisher = new Publisher();
		
		publisher.attach(one);
		publisher.attach(two);
		
		publisher.notifyUpdate(new Message("first"));
		
		publisher.detach(one);
		publisher.attach(three);
		
		publisher.notifyUpdate(new Message("two"));		

	}

}
