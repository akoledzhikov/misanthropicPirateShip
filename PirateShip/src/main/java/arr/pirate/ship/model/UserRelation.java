package arr.pirate.ship.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserRelations")
public class UserRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long user;

	private long friend;

	public UserRelation() {

	}

	public UserRelation(long user, long friend) {
		super();
		this.user = user;
		this.friend = friend;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public long getFriend() {
		return friend;
	}

	public void setFriend(long friend) {
		this.friend = friend;
	}

}
