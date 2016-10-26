package arr.pirate.ship.service;


import java.util.Collection;
import java.util.Date;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arr.pirate.ship.dao.BackingRepository;
import arr.pirate.ship.dao.InstanceRepository;
import arr.pirate.ship.dao.VoteRepository;
import arr.pirate.ship.dto.InstanceDTO;
import arr.pirate.ship.model.Backing;
import arr.pirate.ship.model.BackingBuilder;
import arr.pirate.ship.model.ChallengeStatus;
import arr.pirate.ship.model.Instance;
import arr.pirate.ship.model.InstanceBuilder;
import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.User;
import arr.pirate.ship.model.Vote;
import arr.pirate.ship.model.VoteBuilder;


@Service
public class InstanceService
{
    @Autowired
    private InstanceRepository instanceRepo;

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private BackingRepository backingRepo;

    @Autowired
    private TemplateService ts;

    @Autowired
    private UserService us;


    public InstanceDTO getInstanceDTO(long id, long userId)
    {
        Instance ci = instanceRepo.findOne(id);
        Template template = ts.findById(ci.getTemplateId());
        User target = us.findById(ci.getTargetUserId());
        User challenger = us.findById(ci.getIssuedByUserId());
        Vote currentUserVote = voteRepo.findByChallengeInstanceIdAndUserId(id, userId);

        boolean allowBack = false;
        boolean allowVote = false;
        boolean allowUploadContent = false;
        boolean allowReject = false;
        boolean allowMakePublic = false;
        boolean allowClaim = false;
        if (ChallengeStatus.PENDING == ci.getStatus() && allowBack(ci, userId))
        {
            allowBack = true;
        }
        else if (ChallengeStatus.PENDING == ci.getStatus() && ci.isHanging()
                 && userId != ci.getIssuedByUserId())
        {
            allowClaim = true;
        }
        else if (ChallengeStatus.ACTIVE == ci.getStatus() && target.getId() == userId)
        {
            allowUploadContent = true;
            if (!ci.isHanging() && !ci.isMutual()
                && instanceRepo.findByTargetUserIdAndStatus(userId, ChallengeStatus.REJECTED).isEmpty()
                && target.getCredits() > template.getCreditsCost())
            {
                allowReject = true;
            }
        }
        else if (ChallengeStatus.EVALUATION == ci.getStatus() && allowVote(ci, userId))
        {
            allowVote = true;
        }
        else if (ChallengeStatus.FAILED == ci.getStatus() && target.getId() == userId)
        {
            allowMakePublic = true;
        }
        else if (ChallengeStatus.PUBLIC_EVALUATION == ci.getStatus() && allowPublicVote(ci, userId))
        {
            allowVote = true;
        }

        InstanceDTO result = new InstanceDTO(ci,
                                             template,
                                             target,
                                             challenger,
                                             currentUserVote,
                                             allowBack,
                                             allowUploadContent,
                                             allowReject,
                                             allowVote,
                                             allowMakePublic,
                                             allowClaim);

        return result;
    }


    private boolean allowBack(Instance ci, long userId)
    {
        if (us.areFriends(userId, ci.getTargetUserId()))
        {
            if (backingRepo.findByChallengeInstanceIdAndUserId(ci.getId(), userId) == null)
            {
                return true;
            }
        }

        return false;
    }


    private boolean allowPublicVote(Instance ci, long userId)
    {
        return voteRepo.findByChallengeInstanceIdAndUserId(ci.getId(), userId) == null;
    }


    private boolean allowVote(Instance ci, long userId)
    {
        Backing back = backingRepo.findByChallengeInstanceIdAndUserId(ci.getId(), userId);
        if (back != null)
        {
            return allowPublicVote(ci, userId);
        }

        return false;
    }


    public Collection<Instance> selectActiveForUser(User user)
    {
        return instanceRepo.selectActiveForUser(user.getId());
    }


    public Collection<Instance> selectToBeVotedForUser(User user)
    {
        return instanceRepo.selectToBeVotedForUser(user.getId());
    }


    public Collection<Instance> selectCompletedForUser(User user)
    {
        return instanceRepo.selectCompletedForUser(user.getId());
    }


    public Collection<Instance> selectPublic(User user)
    {
        return instanceRepo.selectPublic();
    }


    public Collection<Instance> selectHangingForUser(User user)
    {
        Collection<User> friends = us.findFriends(user);
        long[] fids = friends.stream().mapToLong(User::getId).toArray();
        return instanceRepo.selectHangingForUser(fids);
    }


    public void newChallenge(long templateId, long userId, long targetUserId)
    {
        User challenger = us.findById(userId);
        Template template = ts.findById(templateId);
        if (challenger.getCredits() < template.getCreditsCost())
        {
            throw new RuntimeException("Not enough credits!");
        }

        challenger.setCredits(challenger.getCredits() - template.getCreditsCost());
        us.save(challenger);

        Instance ci = new InstanceBuilder().name(template.getName())
                                           .submittedOn(new Date())
                                           .status(ChallengeStatus.PENDING)
                                           .templateId(templateId)
                                           .targetUserId(targetUserId)
                                           .issuedByUserId(userId)
                                           .hanging(targetUserId == -1)
                                           .build();

        ci = instanceRepo.save(ci);
        Backing backing = new BackingBuilder().challengeInstanceId(ci.getId())
                                              .dateCreated(new Date())
                                              .userId(userId)
                                              .build();
        backingRepo.save(backing);
    }


    public void backChallenge(long backerId, long challengeId)
    {
        if (backingRepo.findByChallengeInstanceIdAndUserId(challengeId, backerId) == null)
        {
            Instance ci = instanceRepo.findOne(challengeId);
            if (ChallengeStatus.PENDING == ci.getStatus())
            {
                Backing backing = new BackingBuilder().challengeInstanceId(challengeId)
                                                      .dateCreated(new Date())
                                                      .userId(backerId)
                                                      .build();
                backingRepo.save(backing);
                Collection<Backing> backs = backingRepo.findByChallengeInstanceId(challengeId);
                if (backs.size() == 2)
                {
                    Template ct = ts.findById(ci.getTemplateId());
                    ci.setStartedOn(new Date());
                    ci.setDeadline(new Date(System.currentTimeMillis() + ct.getDeadlineInDays() * 24 * 60
                                            * 60 * 1000));
                    ci.setStatus(ChallengeStatus.ACTIVE);
                    instanceRepo.save(ci);
                }
            }
            else
            {
                throw new RuntimeException("Challenge is not in state PENDING!");
            }
        }
        else
        {
            throw new RuntimeException("Challenge already backed by this user!");
        }
    }


    public void claimChallenge(long id, long claimerId)
    {
        Instance ci = instanceRepo.findOne(id);
        if (ci.isHanging() && ci.getTargetUserId() == -1 && ChallengeStatus.PENDING == ci.getStatus())
        {
            Template ct = ts.findById(ci.getTemplateId());
            ci.setTargetUserId(claimerId);
            ci.setStartedOn(new Date());
            ci.setDeadline(new Date(System.currentTimeMillis() + ct.getDeadlineInDays() * 24 * 60 * 60 * 1000));
            ci.setStatus(ChallengeStatus.ACTIVE);
            instanceRepo.save(ci);
        }
        else
        {
            throw new RuntimeException("Challenge is no longer hanging!");
        }
    }


    public void vote(long userId, long challengeId, String vote)
    {
        Instance ci = instanceRepo.findOne(challengeId);
        if (allowVote(ci, userId)
            || (ChallengeStatus.PUBLIC_EVALUATION == ci.getStatus() && allowPublicVote(ci, userId)))
        {
            User targetUser = us.findById(ci.getTargetUserId());
            Template ct = ts.findById(ci.getTemplateId());
            Vote v;
            if ("YES".equals(vote))
            {
                v = new VoteBuilder().challengeInstanceId(challengeId).userId(userId).pass(true).build();
            }
            else
            {
                v = new VoteBuilder().challengeInstanceId(challengeId).userId(userId).pass(false).build();
            }

            voteRepo.save(v);
            tallyVotes(targetUser, ci, ct);
        }
        else
        {
            throw new RuntimeException("You cannot vote on this challenge!");
        }
    }


    private void tallyVotes(User user, Instance ci, Template ct)
    {
        Collection<Vote> votes = voteRepo.findByChallengeInstanceId(ci.getId());
        Collection<Backing> backs = backingRepo.findByChallengeInstanceId(ci.getId());

        if (votes.size() == backs.size())
        {
            int positive = 0;
            int negative = 0;
            for (Vote v : votes)
            {
                if (v.isPass())
                {
                    positive++;
                }
                else
                {
                    negative++;
                }
            }

            if (positive >= negative)
            {
                ci.setStatus(ChallengeStatus.SUCCESS);
            }
            else
            {
                ci.setStatus(ChallengeStatus.FAILED);
            }

            ci.setCompletedOn(new Date());
            instanceRepo.save(ci);

            if (ChallengeStatus.SUCCESS == ci.getStatus())
            {
                user.setPoints(user.getPoints() + ct.getPoints());
                user.setCredits(user.getCredits() + 2 * ct.getCreditsCost());
            }
            else
            {
                // TODO get something, even on fail?
            }

            us.save(user);
        }
    }


    public void rejectChallenge(long userId, long challengeId)
    {
        Instance ci = instanceRepo.findOne(challengeId);
        User user = us.findById(userId);
        User owner = us.findById(ci.getIssuedByUserId());
        Template ct = ts.findById(ci.getTemplateId());
        if (user.getCredits() < ct.getCreditsCost())
        {
            throw new RuntimeException("Not enough credits to reject challenge!");
        }

        ci.setStatus(ChallengeStatus.REJECTED);
        ci.setCompletedOn(new Date());
        instanceRepo.save(ci);

        user.setCredits(user.getCredits() - ct.getCreditsCost());
        // refund
        owner.setCredits(owner.getCredits() + ct.getCreditsCost());
        us.save(user);
        us.save(owner);
    }


    public void makePublic(long userId, long challengeId)
    {
        Instance ci = instanceRepo.findOne(challengeId);
        ci.setDeadline(new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000));
        ci.setStatus(ChallengeStatus.PUBLIC_EVALUATION);
        instanceRepo.save(ci);
    }


    public void contentUploaded(long userId, long challengeId, int challengeRating)
    {
        Instance ci = instanceRepo.findOne(challengeId);
        ci.setStatus(ChallengeStatus.EVALUATION);
        ci.setVotingEnd(new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000));
        instanceRepo.save(ci);
    }


    public Collection<Instance> selectPendingForUser(User user)
    {
        return instanceRepo.selectPendingForUser(user.getId());
    }
}
