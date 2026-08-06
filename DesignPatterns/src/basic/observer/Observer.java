package basic.observer;

// 메시지 알림을 받는 쪽
public interface Observer {
	void update(Message message);
}
