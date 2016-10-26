package arr.pirate.ship.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;


@Entity
@Table(name = "MutualChallenges")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class MutualChallenge
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long templateId; 
    
    private String name;

    private long challengerId;

    private long targetId;

    private long judge1Id;

    private long judge2Id;

    private long judge3Id;

    @Enumerated(EnumType.STRING)
    private MutualChallenegStattus status;

    private Date issuedOn;


    public long getId()
    {
        return id;
    }


    public void setId(long id)
    {
        this.id = id;
    }


    public long getTemplateId()
    {
        return templateId;
    }


    public void setTemplateId(long templateId)
    {
        this.templateId = templateId;
    }


    public long getChallengerId()
    {
        return challengerId;
    }


    public void setChallengerId(long challengerId)
    {
        this.challengerId = challengerId;
    }


    public long getTargetId()
    {
        return targetId;
    }


    public void setTargetId(long targetId)
    {
        this.targetId = targetId;
    }


    public long getJudge1Id()
    {
        return judge1Id;
    }


    public void setJudge1Id(long judge1Id)
    {
        this.judge1Id = judge1Id;
    }


    public long getJudge2Id()
    {
        return judge2Id;
    }


    public void setJudge2Id(long judge2Id)
    {
        this.judge2Id = judge2Id;
    }


    public long getJudge3Id()
    {
        return judge3Id;
    }


    public void setJudge3Id(long judge3Id)
    {
        this.judge3Id = judge3Id;
    }


    public MutualChallenegStattus getStatus()
    {
        return status;
    }


    public void setStatus(MutualChallenegStattus status)
    {
        this.status = status;
    }


    public Date getIssuedOn()
    {
        return issuedOn;
    }


    public void setIssuedOn(Date issuedOn)
    {
        this.issuedOn = issuedOn;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

}
