package arr.pirate.ship.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import arr.pirate.ship.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.mail = :mail")
	public User findByUsername(@Param("mail") String mail);

}
