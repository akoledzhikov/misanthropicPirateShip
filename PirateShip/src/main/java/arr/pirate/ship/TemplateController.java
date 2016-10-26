package arr.pirate.ship;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.User;
import arr.pirate.ship.service.TemplateService;
import arr.pirate.ship.service.UserService;


@Controller
public class TemplateController
{
    @Autowired
    private TemplateService ts;

    @Autowired
    private UserService us;


    @RequestMapping(value = "/challengeTemplates/{id}", method = RequestMethod.GET)
    public String challengeTemplate(@CookieValue("pirateID") String pirateID,
                                    @PathVariable("id") long id,
                                    Model model)
    {
        Template template = ts.findById(id);
        long challengerID = Long.valueOf(pirateID);
        User challenger = us.findById(challengerID);
        if (challenger.getCredits() >= template.getCreditsCost())
        {
            model.addAttribute("allowChallenge", true);
        }

        Collection<User> friends = us.findFriends(challenger);
        model.addAttribute("template", template);
        model.addAttribute("friends", friends);
        return "newChallenge";
    }

}
