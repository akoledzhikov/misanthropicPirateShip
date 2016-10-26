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
@Table(name = "ChallengeInstances")
@GeneratePojoBuilder(withSetterNamePattern = "*")
public class Instance
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long templateId;

    private long targetUserId;

    private long issuedByUserId;

    private Date submittedOn;

    private Date startedOn;

    private Date votingEnd;

    private Date deadline;

    private Date completedOn;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    private String name;

    private boolean hanging;

    private boolean mutual;


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


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


    public long getTargetUserId()
    {
        return targetUserId;
    }


    public void setTargetUserId(long targetUserId)
    {
        this.targetUserId = targetUserId;
    }


    public Date getSubmittedOn()
    {
        return submittedOn;
    }


    public void setSubmittedOn(Date submittedOn)
    {
        this.submittedOn = submittedOn;
    }


    public Date getStartedOn()
    {
        return startedOn;
    }


    public void setStartedOn(Date startedOn)
    {
        this.startedOn = startedOn;
    }


    public Date getDeadline()
    {
        return deadline;
    }


    public void setDeadline(Date deadline)
    {
        this.deadline = deadline;
    }


    public Date getCompletedOn()
    {
        return completedOn;
    }


    public void setCompletedOn(Date completedOn)
    {
        this.completedOn = completedOn;
    }


    public ChallengeStatus getStatus()
    {
        return status;
    }


    public void setStatus(ChallengeStatus status)
    {
        this.status = status;
    }


    public long getIssuedByUserId()
    {
        return issuedByUserId;
    }


    public void setIssuedByUserId(long issuedByUserId)
    {
        this.issuedByUserId = issuedByUserId;
    }


    public boolean isHanging()
    {
        return hanging;
    }


    public void setHanging(boolean hanging)
    {
        this.hanging = hanging;
    }


    public Date getVotingEnd()
    {
        return votingEnd;
    }


    public void setVotingEnd(Date votingEnd)
    {
        this.votingEnd = votingEnd;
    }


    public boolean isMutual()
    {
        return mutual;
    }


    public void setMutual(boolean mutual)
    {
        this.mutual = mutual;
    }
}
