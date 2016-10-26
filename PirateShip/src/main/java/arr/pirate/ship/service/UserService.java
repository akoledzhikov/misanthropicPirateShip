package arr.pirate.ship.service;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arr.pirate.ship.dao.InstanceRepository;
import arr.pirate.ship.dao.UserRelationRepository;
import arr.pirate.ship.dao.UserRepository;
import arr.pirate.ship.model.User;


@Service
public class UserService
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private InstanceRepository instanceRepo;

    @Autowired
    private UserRelationRepository userRelRepo;


    public User login(String username, String password)
    {
        User user = userRepo.findByUsername(username);
        return user;
    }


    public User findById(long id)
    {
        return userRepo.findOne(id);
    }


    public Collection<User> findFriends(User user)
    {
        Collection<Long> friendIds = userRelRepo.selectIdsOfFriends(user.getId());
        return (Collection<User>)userRepo.findAll(friendIds);
    }


    public boolean areFriends(long userId, long targetUserId)
    {
        return !userRelRepo.findByUserAndFriend(userId, targetUserId).isEmpty();
    }


    public void save(User user)
    {
        userRepo.save(user);
    }
}
