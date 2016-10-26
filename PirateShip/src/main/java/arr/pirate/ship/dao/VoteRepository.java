package arr.pirate.ship.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import arr.pirate.ship.model.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {
	
	public Collection<Vote> findByChallengeInstanceId(long id);
	
	public Vote findByChallengeInstanceIdAndUserId(long cid, long uid);


}

