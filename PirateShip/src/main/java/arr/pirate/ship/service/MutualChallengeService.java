package arr.pirate.ship.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arr.pirate.ship.dao.BackingRepository;
import arr.pirate.ship.dao.InstanceRepository;
import arr.pirate.ship.dao.MutualChallengeRepository;
import arr.pirate.ship.model.Backing;
import arr.pirate.ship.model.BackingBuilder;
import arr.pirate.ship.model.ChallengeStatus;
import arr.pirate.ship.model.Instance;
import arr.pirate.ship.model.InstanceBuilder;
import arr.pirate.ship.model.MutualChallenegStattus;
import arr.pirate.ship.model.MutualChallenge;
import arr.pirate.ship.model.MutualChallengeBuilder;
import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.User;


@Service
public class MutualChallengeService
{
    @Autowired
    private MutualChallengeRepository mRepo;

    @Autowired
    private InstanceRepository instanceRepo;

    @Autowired
    private BackingRepository backingRepo;

    @Autowired
    private UserService us;

    @Autowired
    private TemplateService ts;


    public Collection<MutualChallenge> getChallengesForUser(long userId)
    {
        return mRepo.findPendingForUser(userId);
    }


    public MutualChallenge findById(long id)
    {
        return mRepo.findOne(id);
    }


    public void createNewMutualChallenge(long templateId,
                                         long challengerId,
                                         long targetId,
                                         long judge1Id,
                                         long judge2Id,
                                         long judge3Id)
    {
        Template ct = ts.findById(templateId);
        MutualChallenge mc = new MutualChallengeBuilder().templateId(templateId)
                                                         .challengerId(challengerId)
                                                         .targetId(targetId)
                                                         .judge1Id(judge1Id)
                                                         .judge2Id(judge2Id)
                                                         .judge3Id(judge3Id)
                                                         .status(MutualChallenegStattus.PENDING)
                                                         .issuedOn(new Date())
                                                         .name(ct.getName())
                                                         .build();

        mRepo.save(mc);

        User user = us.findById(challengerId);
        user.setCredits(user.getCredits() - ct.getCreditsCost());
        us.save(user);
    }


    public void rejectMutualChallenge(long id, long userId)
    {
        MutualChallenge mc = mRepo.findOne(id);
        if (mc.getTargetId() == userId)
        {
            mc.setStatus(MutualChallenegStattus.REJECTED);
            mRepo.save(mc);

            Template ct = ts.findById(mc.getTemplateId());
            User user = us.findById(userId);
            user.setCredits(user.getCredits() - ct.getCreditsCost());
            // refund
            User owner = us.findById(mc.getChallengerId());
            owner.setCredits(owner.getCredits() + ct.getCreditsCost());
            us.save(user);
            us.save(owner);
        }
    }


    public void acceptMutualChallenge(long id, long userId)
    {
        MutualChallenge mc = mRepo.findOne(id);
        if (mc.getTargetId() == userId)
        {
            // create 2 challenges from the same template, status ACTIVE
            // if no judges - create 1 backing for each challenge for the respective user.
            // if at least 1 judge - for each judge, create 1 backing for each challenge

            Template ct = ts.findById(mc.getTemplateId());
            Date now = new Date();
            Date deadline = new Date(System.currentTimeMillis() + ct.getDeadlineInDays() * 24 * 60 * 60
                                     * 1000);
            Instance ci = new InstanceBuilder().name(ct.getName())
                                               .submittedOn(now)
                                               .deadline(deadline)
                                               .status(ChallengeStatus.ACTIVE)
                                               .templateId(mc.getTemplateId())
                                               .targetUserId(mc.getTargetId())
                                               .issuedByUserId(mc.getChallengerId())
                                               .mutual(true)
                                               .build();
            ci = instanceRepo.save(ci);
            Instance reverseCi = new InstanceBuilder().name(ct.getName())
                                                      .submittedOn(now)
                                                      .deadline(deadline)
                                                      .status(ChallengeStatus.ACTIVE)
                                                      .templateId(mc.getTemplateId())
                                                      .targetUserId(mc.getChallengerId())
                                                      .issuedByUserId(mc.getTargetId())
                                                      .mutual(true)
                                                      .build();
            reverseCi = instanceRepo.save(reverseCi);

            List<Long> judgeIds = new ArrayList<>();
            if (mc.getJudge1Id() != 0)
            {
                judgeIds.add(mc.getJudge1Id());
            }

            if (mc.getJudge2Id() != 0)
            {
                judgeIds.add(mc.getJudge2Id());
            }

            if (mc.getJudge3Id() != 0)
            {
                judgeIds.add(mc.getJudge3Id());
            }

            if (judgeIds.isEmpty())
            {
                Backing backing = new BackingBuilder().challengeInstanceId(ci.getId())
                                                      .dateCreated(new Date())
                                                      .userId(ci.getIssuedByUserId())
                                                      .build();

                Backing reverseBacking = new BackingBuilder().challengeInstanceId(reverseCi.getId())
                                                             .dateCreated(new Date())
                                                             .userId(reverseCi.getIssuedByUserId())
                                                             .build();
                backingRepo.save(backing);
                backingRepo.save(reverseBacking);
            }
            else
            {
                for (long judgeId : judgeIds)
                {
                    Backing backing = new BackingBuilder().challengeInstanceId(ci.getId())
                                                          .dateCreated(new Date())
                                                          .userId(judgeId)
                                                          .build();

                    Backing reverseBacking = new BackingBuilder().challengeInstanceId(reverseCi.getId())
                                                                 .dateCreated(new Date())
                                                                 .userId(judgeId)
                                                                 .build();
                    backingRepo.save(backing);
                    backingRepo.save(reverseBacking);
                }
            }
        }
    }
}
