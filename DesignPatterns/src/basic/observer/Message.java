package basic.observer;

// 구독자에게 전달되는 메시지
public class Message {
	private final String messageContent;
	
	public Message(String messageContent) {
		this.messageContent = messageContent;
	}
	
	public String getMessageContent() {
		return this.messageContent;
	}
}
