package arr.pirate.ship.dao;

import org.springframework.data.repository.CrudRepository;

import arr.pirate.ship.model.Content;

public interface ContentRepository extends CrudRepository<Content, Long> {

	Content findByChallengeInstanceId(long id);

}

