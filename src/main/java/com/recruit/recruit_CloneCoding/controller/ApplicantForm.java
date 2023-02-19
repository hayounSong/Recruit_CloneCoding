package com.recruit.recruit_CloneCoding.controller;

import com.recruit.recruit_CloneCoding.domain.AbsenseStatus;
import com.recruit.recruit_CloneCoding.domain.Part;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter@Setter
public class ApplicantForm {
    private String partName;
    private String name;
    private String address;
    private String subway;
    private String email;
    private String school;
    private String major;
    private boolean isOb;
    private String question1;
    private String question2;
    private String question3;

    @Enumerated(EnumType.STRING)
    private AbsenseStatus absenseStatus;
}
