package arr.pirate.ship.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@Entity
@Table(name = "ChallengeTemplates")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	@Column(length = 1024)
	private String description;

	private String category;

	private String pictureLocation;

	private int points;
	
	private int deadlineInDays;
	
	private int creditsCost;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPictureLocation() {
		return pictureLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

    public int getDeadlineInDays()
    {
        return deadlineInDays;
    }

    public void setDeadlineInDays(int deadlineInDays)
    {
        this.deadlineInDays = deadlineInDays;
    }

    public int getCreditsCost()
    {
        return creditsCost;
    }

    public void setCreditsCost(int creditsCost)
    {
        this.creditsCost = creditsCost;
    }
}
