package arr.pirate.ship.dao;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import arr.pirate.ship.model.Instance;
import arr.pirate.ship.model.ChallengeStatus;


public interface InstanceRepository
    extends CrudRepository<Instance, Long>
{

    @Query("SELECT c FROM Instance c WHERE c.targetUserId = :id AND c.status IN ('ACTIVE','EVALUATION', 'PUBLIC_EVALUATION')")
    Collection<Instance> selectActiveForUser(@Param("id") long id);


    @Query("SELECT c FROM Instance c WHERE c.targetUserId = :id AND c.status = 'PENDING'")
    Collection<Instance> selectPendingForUser(@Param("id") long id);


    @Query("SELECT c FROM Instance c WHERE c.status = 'EVALUATION' AND"
           + " :id IN (SELECT b.userId from Backing b WHERE c.id = b.challengeInstanceId)"
           + "AND c.id NOT IN (SELECT v.challengeInstanceId FROM Vote v WHERE v.userId = :id)")
    Collection<Instance> selectToBeVotedForUser(@Param("id") long id);


    @Query("SELECT c FROM Instance c WHERE c.targetUserId = :id AND c.status IN ('SUCCESS','FAILED_NO_TRY','FAILED', 'PUBLIC_SUCCESS','PUBLIC_FAIL')")
    Collection<Instance> selectCompletedForUser(@Param("id") long id);


    @Query("SELECT c FROM Instance c WHERE c.status = 'PUBLIC_EVALUATION'")
    Collection<Instance> selectPublic();

    @Query("SELECT c FROM Instance c WHERE c.hanging = TRUE AND c.issuedByUserId IN (:fids)")
    Collection<Instance> selectHangingForUser(@Param("fids") long[] fids);
    
    Collection<Instance> findByTargetUserIdAndStatus(long targetUserId, ChallengeStatus status);
}
