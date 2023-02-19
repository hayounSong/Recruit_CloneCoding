package com.recruit.recruit_CloneCoding.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Part {

    @Id
    @GeneratedValue
    @Column(name="part_id")
    private int id;

    @OneToMany(mappedBy = "part")
    private List<Applicant> applicants=new ArrayList<Applicant>();
    private String partName;
}
