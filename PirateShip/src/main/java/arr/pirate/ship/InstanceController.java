package arr.pirate.ship;


import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import arr.pirate.ship.dao.ContentRepository;
import arr.pirate.ship.dto.InstanceDTO;
import arr.pirate.ship.model.Content;
import arr.pirate.ship.model.ContentBuilder;
import arr.pirate.ship.model.MutualChallenge;
import arr.pirate.ship.model.Template;
import arr.pirate.ship.model.User;
import arr.pirate.ship.service.InstanceService;
import arr.pirate.ship.service.MutualChallengeService;
import arr.pirate.ship.service.TemplateService;
import arr.pirate.ship.service.UserService;


@Controller
public class InstanceController
{
    @Autowired
    private InstanceService is;

    @Autowired
    private ContentRepository contentRepo;

    @Autowired
    private MutualChallengeService mcs;
    
    @Autowired
    private TemplateService ts;
    
    @Autowired
    private UserService us;


    @RequestMapping(value = "/challengeInstances/{id}", method = RequestMethod.GET)
    public String challengeInstance(@CookieValue("pirateID") String pirateID,
                                    @PathVariable("id") long id,
                                    Model model)
    {
        long currentUserId = Long.valueOf(pirateID);
        InstanceDTO c = is.getInstanceDTO(id, currentUserId);
        model.addAttribute("c", c);
        model.addAttribute("target", c.getTargetUser());
        model.addAttribute("challenger", c.getChallenger());
        model.addAttribute("template", c.getTemplate());
        model.addAttribute("myVote", c.getVote());
        return "challengeInstance";
    }


    @RequestMapping(value = "/challengeInstances/challenge/{id}", method = RequestMethod.POST)
    public String createNewChallenge(@CookieValue("pirateID") String pirateID,
                                     @PathVariable("id") long id,
                                     @RequestParam(name = "ID", required = false) long targetUserId,
                                     Model model)
    {
        long currentUserId = Long.valueOf(pirateID);
        is.newChallenge(id, currentUserId, targetUserId);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + currentUserId;
    }


    @RequestMapping(value = "/challengeInstances/back/{id}", method = RequestMethod.POST)
    public String backChallenge(@CookieValue("pirateID") String pirateID,
                                @PathVariable("id") long id,
                                HttpServletRequest request,
                                Model model)
    {
        long challengerID = Long.valueOf(pirateID);
        is.backChallenge(challengerID, id);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + challengerID;
    }


    @RequestMapping(value = "/challengeInstances/claim/{id}", method = RequestMethod.POST)
    public String claimChallenge(@CookieValue("pirateID") String pirateID,
                                 @PathVariable("id") long id,
                                 HttpServletRequest request,
                                 Model model)
    {
        long myId = Long.valueOf(pirateID);
        is.claimChallenge(id, myId);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + myId;
    }


    @RequestMapping(value = "/challengeInstances/vote/{id}", method = RequestMethod.POST)
    public String vote(@CookieValue("pirateID") String pirateID,
                       @RequestParam("vote") String vote,
                       @PathVariable("id") long id,
                       Model model)
    {
        long currentUserID = Long.valueOf(pirateID);
        is.vote(currentUserID, id, vote);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + currentUserID;
    }


    @RequestMapping(value = "/challengeInstances/makePublic/{id}", method = RequestMethod.POST)
    public String makePublic(@CookieValue("pirateID") String pirateID,
                             @PathVariable("id") long id,
                             HttpServletResponse response)
    {
        long userId = Long.valueOf(pirateID);
        is.makePublic(userId, id);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + userId;
    }


    @RequestMapping(value = "/challengeInstances/reject/{id}", method = RequestMethod.POST)
    public String rejectChallenge(@CookieValue("pirateID") String pirateID,
                                  @PathVariable("id") long id,
                                  Model model)
        throws IOException
    {
        long currentUserID = Long.valueOf(pirateID);
        is.rejectChallenge(currentUserID, id);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + currentUserID;
    }


    @RequestMapping(value = "/challengeInstances/postContent/{id}", method = RequestMethod.POST)
    public String postContent(@CookieValue("pirateID") String pirateID,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam(name = "fun", defaultValue = "3") int fun,
                              @RequestParam(name = "interesting", defaultValue = "3") int interesting,
                              @RequestParam(name = "difficult", defaultValue = "3") int difficult,
                              @PathVariable("id") long id,
                              Model model)
        throws IOException
    {
        long currentUserID = Long.valueOf(pirateID);
        Content content = new ContentBuilder().content(file.getBytes())
                                              .createdOn(new Date())
                                              .docType("whatever")
                                              .originalName(file.getOriginalFilename())
                                              .challengeInstanceId(id)
                                              .build();
        contentRepo.save(content);
        is.contentUploaded(currentUserID, id, fun);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + currentUserID;
    }


    @RequestMapping(value = "/challengeInstances/getContent/{id}", method = RequestMethod.GET)
    public void getContent(@PathVariable("id") long id, HttpServletResponse response)
        throws Exception
    {
        Content content = contentRepo.findByChallengeInstanceId(id);
        byte[] docAsBytes = content.getContent();
        try
        {
            response.setContentType(content.getContentType());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + content.getOriginalName()
                                                      + "\"");
            response.setContentLength(docAsBytes.length);
            FileCopyUtils.copy(docAsBytes, response.getOutputStream());
            response.flushBuffer();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value = "/challengeInstances/mutualChallenge/{id}", method = RequestMethod.POST)
    public String createNewMutualChallenge(@CookieValue("pirateID") String pirateID,
                                           @PathVariable("id") long id,
                                           @RequestParam(name = "targetId") long targetUserId,
                                           @RequestParam(name = "judge1Id", defaultValue="0") long judge1Id,
                                           @RequestParam(name = "judge2Id", defaultValue="0") long judge2Id,
                                           @RequestParam(name = "judge3Id", defaultValue="0") long judge3Id,
                                           Model model)
    {
        long currentUserId = Long.valueOf(pirateID);
        mcs.createNewMutualChallenge(id, currentUserId, targetUserId, judge1Id, judge2Id, judge3Id);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + currentUserId;
    }
    
    @RequestMapping(value = "/challengeInstances/mutualChallenge/{id}", method = RequestMethod.GET)
    public String mutualChallengeInstance(@CookieValue("pirateID") String pirateID,
                                           @PathVariable("id") long id,
                                           Model model)
    {
        long currentUserId = Long.valueOf(pirateID);
        MutualChallenge mc = mcs.findById(id);
        model.addAttribute("mc",mc);
        Template template = ts.findById(mc.getTemplateId());
        model.addAttribute("template",template);
        User challenger = us.findById(mc.getChallengerId());
        User target = us.findById(mc.getTargetId());
        model.addAttribute("target",target);
        model.addAttribute("challenger",challenger);
        return "mutualChallengeInstance";
    }


    @RequestMapping(value = "/challengeInstances/rejectMutualChallenge/{id}", method = RequestMethod.POST)
    public String rejectMutualChallenge(@CookieValue("pirateID") String pirateID,
                                        @PathVariable("id") long id,
                                        Model model)
        throws IOException
    {
        long currentUserID = Long.valueOf(pirateID);
        mcs.rejectMutualChallenge(id, currentUserID);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + currentUserID;
    }


    @RequestMapping(value = "/challengeInstances/acceptMutualChallenge/{id}", method = RequestMethod.POST)
    public String acceptMutualChallenge(@CookieValue("pirateID") String pirateID,
                                        @PathVariable("id") long id,
                                        Model model)
        throws IOException
    {
        long currentUserID = Long.valueOf(pirateID);
        mcs.acceptMutualChallenge(id, currentUserID);
        return "redirect:http://localhost:8080/pirate/myDashboard/" + currentUserID;
    }
}
