package edu.utn.frsr.csi.web;

import edu.utn.frsr.csi.model.Evidence;
import edu.utn.frsr.csi.services.ServiceEvidence;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Controller
public class EvidenceController {

    private ServiceEvidence serviceEvidence;

    public EvidenceController(ServiceEvidence serviceEvidence){
        this.serviceEvidence = serviceEvidence;
    }

    @RequestMapping("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("today", Date.from(Instant.now()));
        return modelAndView;
    }

    @RequestMapping("/evidencias")
    public ModelAndView evidences(){
        ModelAndView modelAndView = new ModelAndView("evidences");

        List<Evidence> evidencias = serviceEvidence.getAllEvidences();
        modelAndView.addObject("evidences", evidencias);
        return modelAndView;
    }

    @RequestMapping(value = "/add_evidence", method = RequestMethod.GET)
    public ModelAndView newEvidence(){
        ModelAndView modelAndView = new ModelAndView("form_evidence");

        Evidence evidence = new Evidence();
        modelAndView.addObject("action", "/add_evidence");
        modelAndView.addObject("method", "post");
        modelAndView.addObject("evidence", evidence);
        return modelAndView;
    }

    @RequestMapping(value = "/add_evidence", method = RequestMethod.POST)
    public String createEvidence(@ModelAttribute("evidence") Evidence evidence){
        serviceEvidence.addEvidence(evidence);
        return "redirect:/evidencias";
    }

    @RequestMapping(value = "/edit_evidence/{id}", method = RequestMethod.GET)
    public ModelAndView editEvidence(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView("form_evidence");

        Evidence evidence = serviceEvidence.getEvidence(id);
        modelAndView.addObject("action", "/edit_evidence");
        modelAndView.addObject("method", "post");
        modelAndView.addObject("evidence", evidence);
        return modelAndView;
    }

    @RequestMapping(value = "/edit_evidence", method = RequestMethod.POST)
    public String saveEvidence(@ModelAttribute("evidence") Evidence evidence){
        serviceEvidence.replaceEvidence(evidence);
        return "redirect:/evidencias";
    }
}
