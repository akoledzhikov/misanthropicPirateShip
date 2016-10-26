package arr.pirate.ship.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@Entity
@Table(name = "ChallengeFeedbacks")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long userId;

	private long challengeTemplateId;

	private int interesting;

	private int difficult;

	private int fun;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChallengeTemplateId() {
		return challengeTemplateId;
	}

	public void setChallengeTemplateId(long challengeTemplateId) {
		this.challengeTemplateId = challengeTemplateId;
	}

	public int getInteresting() {
		return interesting;
	}

	public void setInteresting(int interesting) {
		this.interesting = interesting;
	}

	public int getDifficult() {
		return difficult;
	}

	public void setDifficult(int difficult) {
		this.difficult = difficult;
	}

	public int getFun() {
		return fun;
	}

	public void setFun(int fun) {
		this.fun = fun;
	}

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

}
