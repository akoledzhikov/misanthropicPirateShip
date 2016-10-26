package arr.pirate.ship;


import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import arr.pirate.ship.model.Instance;
import arr.pirate.ship.model.MutualChallenge;
import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.User;
import arr.pirate.ship.service.InstanceService;
import arr.pirate.ship.service.MutualChallengeService;
import arr.pirate.ship.service.TemplateService;
import arr.pirate.ship.service.UserService;


@Controller
public class UserController
{
    @Autowired
    private UserService us;

    @Autowired
    private TemplateService ts;

    @Autowired
    private InstanceService is;

    @Autowired
    private MutualChallengeService mcs;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model)
    {
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(name = "username") String userName,
                        HttpServletResponse response,
                        Model model)
    {
        User user = us.login(userName, "");
        response.addCookie(new Cookie("pirateID", String.valueOf(user.getId())));
        return "redirect:http://localhost:8080/pirate/myDashboard/" + user.getId();
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletResponse response, Model model)
    {
        Cookie cookie = new Cookie("pirateID", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:http://localhost:8080/pirate/login";
    }


    @RequestMapping(value = "/myDashboard/{id}", method = RequestMethod.GET)
    public String myDashboard(@PathVariable("id") long id, Model model)
    {
        User user = us.findById(id);
        model.addAttribute("user", user);
        Collection<User> friends = us.findFriends(user);
        model.addAttribute("friends", friends);

        Collection<Template> activeTemplates = ts.selectTemplatesForUser(user);
        model.addAttribute("activeTemplates", activeTemplates);

        Collection<Instance> myChallenges = is.selectActiveForUser(user);
        model.addAttribute("myChallenges", myChallenges);
        Collection<Instance> toBeVoted = is.selectToBeVotedForUser(user);
        model.addAttribute("toBeVoted", toBeVoted);
        Collection<Instance> completed = is.selectCompletedForUser(user);
        model.addAttribute("completed", completed);
        Collection<Instance> pub = is.selectPublic(user);
        model.addAttribute("publicc", pub);
        Collection<Instance> hangingChallenges = is.selectHangingForUser(user);
        model.addAttribute("hanging", hangingChallenges);
        Collection<MutualChallenge> mutualChallenges = mcs.getChallengesForUser(id);
        model.addAttribute("mutual", mutualChallenges);
        return "myDashboard";
    }


    @RequestMapping(value = "/userProfile/{id}", method = RequestMethod.GET)
    public String userProfile(@PathVariable("id") long id, Model model)
    {
        User user = us.findById(id);
        model.addAttribute("user", user);

        Collection<Instance> myChallenges = is.selectActiveForUser(user);
        model.addAttribute("active", myChallenges);
        Collection<Instance> completed = is.selectCompletedForUser(user);
        model.addAttribute("completed", completed);
        Collection<Instance> pending = is.selectPendingForUser(user);
        model.addAttribute("pending", pending);
        return "userProfile";
    }
}
