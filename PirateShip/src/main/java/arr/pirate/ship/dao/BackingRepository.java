package arr.pirate.ship.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import arr.pirate.ship.model.Backing;

public interface BackingRepository extends CrudRepository<Backing, Long> {
		
	public Backing findByChallengeInstanceIdAndUserId(long id, long userId);

    public Collection<Backing> findByChallengeInstanceId(long challengeId);

}
