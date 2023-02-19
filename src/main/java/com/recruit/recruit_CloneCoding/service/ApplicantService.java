package com.recruit.recruit_CloneCoding.service;

import com.recruit.recruit_CloneCoding.domain.Applicant;
import com.recruit.recruit_CloneCoding.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ApplicantService {
    private ApplicantRepository applicantRepository;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Transactional
    public int apply(Applicant applicant){
        applicantRepository.save(applicant);
        return applicant.getId();
    }

    public List<Applicant> findApplicants(){
        return applicantRepository.findAll();
    }


}
