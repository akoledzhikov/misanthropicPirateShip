package arr.pirate.ship.dao;

import org.springframework.data.repository.CrudRepository;

import arr.pirate.ship.model.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

}
