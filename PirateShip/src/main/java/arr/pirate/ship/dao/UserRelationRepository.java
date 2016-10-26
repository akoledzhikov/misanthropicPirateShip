package arr.pirate.ship.dao;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import arr.pirate.ship.model.UserRelation;


public interface UserRelationRepository
    extends CrudRepository<UserRelation, Long>
{

    @Query("SELECT r.friend FROM UserRelation r WHERE r.user = :userId AND :userId IN (SELECT r1.friend FROM UserRelation r1 WHERE r1.user = r.friend)")
    public Collection<Long> selectIdsOfFriends(@Param("userId") long userId);


    public Collection<UserRelation> findByUserAndFriend(long user, long friend);
}
