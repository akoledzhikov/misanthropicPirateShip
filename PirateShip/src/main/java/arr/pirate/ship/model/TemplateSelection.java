package arr.pirate.ship.model;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;


@Entity
@Table(name = "ChallengeTemplateSelections")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class TemplateSelection
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private Calendar validFrom;

    private Calendar validTo;

    private long challenge1;

    private long challenge2;

    private long challenge3;


    public long getId()
    {
        return id;
    }


    public void setId(long id)
    {
        this.id = id;
    }


    public long getUserId()
    {
        return userId;
    }


    public void setUserId(long userId)
    {
        this.userId = userId;
    }


    public Calendar getValidFrom()
    {
        return validFrom;
    }


    public void setValidFrom(Calendar validFrom)
    {
        this.validFrom = validFrom;
    }


    public Calendar getValidTo()
    {
        return validTo;
    }


    public void setValidTo(Calendar validTo)
    {
        this.validTo = validTo;
    }


    public long getChallenge1()
    {
        return challenge1;
    }


    public void setChallenge1(long challenge1)
    {
        this.challenge1 = challenge1;
    }


    public long getChallenge2()
    {
        return challenge2;
    }


    public void setChallenge2(long challenge2)
    {
        this.challenge2 = challenge2;
    }


    public long getChallenge3()
    {
        return challenge3;
    }


    public void setChallenge3(long challenge3)
    {
        this.challenge3 = challenge3;
    }
}
