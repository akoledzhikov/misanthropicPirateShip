package arr.pirate.ship.dto;


import java.util.Date;

import arr.pirate.ship.model.ChallengeStatus;
import arr.pirate.ship.model.Instance;
import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.User;
import arr.pirate.ship.model.Vote;


/**
 * TODO short description for InstanceDTO.
 * <p>
 * Long description for InstanceDTO.
 * 
 * @author Alexander
 */
public class InstanceDTO
{
    private Instance inst;

    private Template template;

    private User targetUser;

    private User challenger;

    private Vote vote;

    private boolean allowBack;

    private boolean allowUploadContent;

    private boolean allowReject;

    private boolean allowVote;

    private boolean allowMakePublic;

    private boolean allowClaim;


    public InstanceDTO()
    {

    }


    public InstanceDTO(Instance inst,
                       Template template,
                       User targetUser,
                       User challenger,
                       Vote vote,
                       boolean allowBack,
                       boolean allowUploadContent,
                       boolean allowReject,
                       boolean allowVote,
                       boolean allowMakePublic,
                       boolean allowClaim)
    {
        super();
        this.inst = inst;
        this.template = template;
        this.targetUser = targetUser;
        this.challenger = challenger;
        this.vote = vote;
        this.allowBack = allowBack;
        this.allowUploadContent = allowUploadContent;
        this.allowReject = allowReject;
        this.allowVote = allowVote;
        this.allowMakePublic = allowMakePublic;
        this.allowClaim = allowClaim;
    }


    public String getName()
    {
        return inst.getName();
    }


    public long getId()
    {
        return inst.getId();
    }


    public long getTemplateId()
    {
        return inst.getTemplateId();
    }


    public long getTargetUserId()
    {
        return inst.getTargetUserId();
    }


    public Date getSubmittedOn()
    {
        return inst.getSubmittedOn();
    }


    public Date getStartedOn()
    {
        return inst.getStartedOn();
    }


    public Date getDeadline()
    {
        return inst.getDeadline();
    }


    public Date getCompletedOn()
    {
        return inst.getCompletedOn();
    }


    public ChallengeStatus getStatus()
    {
        return inst.getStatus();
    }


    public long getIssuedByUserId()
    {
        return inst.getIssuedByUserId();
    }


    public boolean isHanging()
    {
        return inst.isHanging();
    }


    public Instance getInst()
    {
        return inst;
    }


    public Template getTemplate()
    {
        return template;
    }


    public User getTargetUser()
    {
        return targetUser;
    }


    public User getChallenger()
    {
        return challenger;
    }


    public Vote getVote()
    {
        return vote;
    }


    public boolean getAllowBack()
    {
        return allowBack;
    }


    public boolean getAllowUploadContent()
    {
        return allowUploadContent;
    }


    public boolean getAllowReject()
    {
        return allowReject;
    }


    public boolean getAllowVote()
    {
        return allowVote;
    }


    public boolean getAllowMakePublic()
    {
        return allowMakePublic;
    }


    public boolean getAllowClaim()
    {
        return allowClaim;
    }

}
