package arr.pirate.ship.demo;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import arr.pirate.ship.dao.TemplateRepository;
import arr.pirate.ship.dao.UserRelationRepository;
import arr.pirate.ship.dao.UserRepository;
import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.TemplateBuilder;
import arr.pirate.ship.model.User;
import arr.pirate.ship.model.UserBuilder;
import arr.pirate.ship.model.UserRelation;


@Component
public class DataImporterListener
{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TemplateRepository challengeRepo;

    @Autowired
    private UserRelationRepository userRelRepo;


    @PostConstruct
    public void importData()
    {

        if (!userRepo.findAll().iterator().hasNext())
        {
            User alex = new UserBuilder().firstName("Alexander")
                                         .lastName("Koledzhikov")
                                         .mail("al@arr.com").credits(30)
                                         .build();
            User yulia = new UserBuilder().firstName("Julia").lastName("Savova").mail("ju@arr.com").credits(30).build();
            User irina = new UserBuilder().firstName("Irina")
                                          .lastName("Stoyanova")
                                          .mail("ir@arr.com").credits(30)
                                          .build();

            User pinko = new UserBuilder().firstName("Pinko").lastName("Pinkov").mail("pi@arr.com").build();

            userRepo.save(alex);
            userRepo.save(yulia);
            userRepo.save(irina);
            userRepo.save(pinko);

            UserRelation ur1 = new UserRelation(1, 2);
            UserRelation ur2 = new UserRelation(1, 3);
            UserRelation ur3 = new UserRelation(2, 1);
            UserRelation ur4 = new UserRelation(2, 3);
            UserRelation ur5 = new UserRelation(3, 1);
            UserRelation ur6 = new UserRelation(3, 2);

            userRelRepo.save(ur1);
            userRelRepo.save(ur2);
            userRelRepo.save(ur3);
            userRelRepo.save(ur4);
            userRelRepo.save(ur5);
            userRelRepo.save(ur6);

            Template ct1 = new TemplateBuilder().category("Music")
                                                                  .name("Sing a pirate song! Arrr!")
                                                                  .description("Drink a bottle of rum, then sing a pirate song! Arr!")
                                                                  .pictureLocation("pirate.jpg")
                                                                  .points(15)
                                                                  .deadlineInDays(2)
                                                                  .creditsCost(5)
                                                                  .build();
            Template ct2 = new TemplateBuilder().category("Arts")
                                                                  .name("Draw a pirate ship! Arrr!")
                                                                  .description("Drink a bottle of rum, then draw a pirate ship with a charcoal! Arr!")
                                                                  .pictureLocation("pirate.jpg")
                                                                  .points(15)
                                                                  .deadlineInDays(2)
                                                                  .creditsCost(5)
                                                                  .build();
            Template ct3 = new TemplateBuilder().category("Cooking")
                                                                  .name("Bake a pirate pie! Arrr!")
                                                                  .description("Drink half a bottle of rum, then use the other half to bake a pirate pie with rum and chocolate! Arr!")
                                                                  .pictureLocation("pirate.jpg")
                                                                  .points(15)
                                                                  .deadlineInDays(2)
                                                                  .creditsCost(5)
                                                                  .build();
            Template ct4 = new TemplateBuilder().category("Dance")
                                                                  .name("Dance a pirate dance! Arrr!")
                                                                  .description("Drink a bottle of rum, then dance a violent and energetic pirate dance! Arr!")
                                                                  .pictureLocation("pirate.jpg")
                                                                  .points(15)
                                                                  .deadlineInDays(2)
                                                                  .creditsCost(5)
                                                                  .build();
            Template ct5 = new TemplateBuilder().category("Work")
                                                                  .name("Wear a pirate costume! Arrr!")
                                                                  .description("Drink a bottle of rum, then dress up as a pirate and go to work! Arr!")
                                                                  .pictureLocation("pirate.jpg")
                                                                  .points(15)
                                                                  .deadlineInDays(2)
                                                                  .creditsCost(5)
                                                                  .build();
            Template ct6 = new TemplateBuilder().category("Sports")
                                                                  .name("Pirate fight!Arrr!")
                                                                  .description("Get a friend, drink a bottle of rum together, then do a pirate fight with knives/swords! Arr!")
                                                                  .pictureLocation("pirate.jpg")
                                                                  .points(15)
                                                                  .deadlineInDays(2)
                                                                  .creditsCost(5)
                                                                  .build();

            challengeRepo.save(ct1);
            challengeRepo.save(ct2);
            challengeRepo.save(ct3);
            challengeRepo.save(ct4);
            challengeRepo.save(ct5);
            challengeRepo.save(ct6);
        }

    }

}
