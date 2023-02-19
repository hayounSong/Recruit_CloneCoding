package com.recruit.recruit_CloneCoding.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.w3c.dom.Text;

import java.awt.*;

@Getter
@Entity
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
    private TextArea question1;
    private TextArea question2;
    private TextArea question3;

    @Enumerated(EnumType.STRING)
    private AbsenseStatus absenseStatus;



}
