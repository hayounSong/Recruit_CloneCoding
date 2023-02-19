package com.recruit.recruit_CloneCoding.service;

import com.recruit.recruit_CloneCoding.domain.Part;
import com.recruit.recruit_CloneCoding.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartService {

    private PartRepository partRepository;

@Autowired
    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Part findPart(String name){
        return partRepository.findByName(name);
    }
}
