package com.recruit.recruit_CloneCoding.controller;

import com.recruit.recruit_CloneCoding.domain.Applicant;
import com.recruit.recruit_CloneCoding.domain.Part;
import com.recruit.recruit_CloneCoding.service.ApplicantService;
import com.recruit.recruit_CloneCoding.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicantController {

    private ApplicantService applicantService;
    private PartService partService;

@Autowired
    public ApplicantController(ApplicantService applicantService,PartService partService) {
        this.applicantService = applicantService;
        this.partService=partService;
    }
    @GetMapping(value = "/apply")
    public String createForm(Model model){
    model.addAttribute("applyForm",new ApplicantForm());
    return "apply/applyForm";
    }
    @PostMapping(value = "/apply")
    public String apply(ApplicantForm applicantForm){
    String partName=applicantForm.getPartName();
    Part part=partService.findPart(partName);
    Applicant applicant=Applicant.createApplicant(part,applicantForm.getName(),applicantForm.getAddress(),applicantForm.getSubway(),applicantForm.getEmail(),applicantForm.getSchool(),applicantForm.getMajor(),applicantForm.getQuestion1(),applicantForm.getQuestion2(),applicantForm.getQuestion3(),applicantForm.getAbsenseStatus());

    applicantService.apply(applicant);
    return "redirect:/";
    }
}
