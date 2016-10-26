package arr.pirate.ship.dao;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import arr.pirate.ship.model.MutualChallenge;


public interface MutualChallengeRepository
    extends CrudRepository<MutualChallenge, Long>
{
    @Query("SELECT m FROM MutualChallenge m WHERE m.targetId = :userId AND m.status = 'PENDING'")
    public Collection<MutualChallenge> findPendingForUser(@Param("userId") long userId);
}
