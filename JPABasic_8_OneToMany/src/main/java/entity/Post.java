package entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

// OneToMany 연관 관계를 가진다 Owing Entity 
// Post 1개에 대해 여러 개의 Comment가 연결 -> Post 객체 하나가 여러 개의 Comment를 필드로 가다 List<Comment>
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String content;
	
	@OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER) 
	private List<Comment> comments;
	//-------------------------------------------------------------------------------------
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
//	@Override
//	public String toString() {
//		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", comments=" + comments + "]";
//	}	
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + "]";
	}
	
}
