package arr.pirate.ship.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@Entity
@Table(name = "ChallengeVotes")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long userId;
	
	private long challengeInstanceId;
	
	private boolean pass;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getChallengeInstanceId() {
		return challengeInstanceId;
	}

	public void setChallengeInstanceId(long challengeInstanceId) {
		this.challengeInstanceId = challengeInstanceId;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}
}
