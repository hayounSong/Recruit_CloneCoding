package com.recruit.recruit_CloneCoding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;

import java.awt.*;

@Getter @Setter
@Entity
@Table(name="applicants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="part_id")
    private Part part;
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


    public static Applicant createApplicant(Part part, String name, String address,String subway, String email,String school, String major, String question1, String question2, String question3,AbsenseStatus absenseStatus){
        Applicant applicant=new Applicant();
        applicant.setPart(part);
        applicant.setAddress(address);
        applicant.setEmail(email);
        applicant.setName(name);
        applicant.setSubway(subway);
        applicant.setSchool(school);
        applicant.setMajor(major);

        applicant.setQuestion1(question1);
        applicant.setQuestion2(question2);
        applicant.setQuestion3(question3);
        applicant.setAbsenseStatus(absenseStatus);
        return applicant;
    }

}
