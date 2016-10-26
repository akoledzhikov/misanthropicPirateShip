package arr.pirate.ship.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@Entity
@Table(name = "ChallengeBackings")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class Backing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long challengeInstanceId;

	private long userId;

	private Date dateCreated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChallengeInstanceId() {
		return challengeInstanceId;
	}

	public void setChallengeInstanceId(long challengeInstanceId) {
		this.challengeInstanceId = challengeInstanceId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
