package basic.observer;

// 구독 등록, 해지 처리
// 메세지 갱신
public interface Subject {
	void attach(Observer observer);
	void detach(Observer observer);
	void notifyUpdate(Message message);
}
