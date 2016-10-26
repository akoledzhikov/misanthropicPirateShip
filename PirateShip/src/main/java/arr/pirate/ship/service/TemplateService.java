package arr.pirate.ship.service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arr.pirate.ship.dao.TemplateRepository;
import arr.pirate.ship.dao.TemplateSelectionRepository;
import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.TemplateSelection;
import arr.pirate.ship.model.TemplateSelectionBuilder;
import arr.pirate.ship.model.User;


@Service
public class TemplateService
{
    @Autowired
    private TemplateRepository templateRepo;

    @Autowired
    private TemplateSelectionRepository selectionRepo;


    public Template findById(long id)
    {
        return templateRepo.findOne(id);
    }


    public Collection<Template> selectTemplatesForUser(User user)
    {
        Calendar now = Calendar.getInstance();
        TemplateSelection selection = selectionRepo.findValidSelection(user.getId(), now);
        if (selection == null)
        {
            selection = generateSelection(user, now);
            selectionRepo.save(selection);
        }

        List<Long> challengeTemplateIds = new ArrayList<>();
        challengeTemplateIds.add(selection.getChallenge1());
        challengeTemplateIds.add(selection.getChallenge2());
        challengeTemplateIds.add(selection.getChallenge3());
        return (Collection<Template>)templateRepo.findAll(challengeTemplateIds);
    }


    private TemplateSelection generateSelection(User user, Calendar creation)
    {
        int size = ((Collection)templateRepo.findAll()).size();
        List<Long> indices = new ArrayList<Long>(size);
        for (long i = 0; i < size; i++)
        {
            indices.add(i + 1);
        }

        Collections.shuffle(indices);
        Calendar end = Calendar.getInstance();
        end.clear();
        end.set(creation.get(Calendar.YEAR),
                creation.get(Calendar.MONTH),
                creation.get(Calendar.DAY_OF_MONTH));
        end.add(Calendar.DAY_OF_MONTH, 1);
        TemplateSelection cts = new TemplateSelectionBuilder().validFrom(creation)
                                                              .validTo(end)
                                                              .userId(user.getId())
                                                              .challenge1(indices.get(0))
                                                              .challenge2(indices.get(1))
                                                              .challenge3(indices.get(2))
                                                              .build();

        return cts;
    }
}
