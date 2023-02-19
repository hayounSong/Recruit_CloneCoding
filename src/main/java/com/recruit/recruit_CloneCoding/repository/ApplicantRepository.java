package com.recruit.recruit_CloneCoding.repository;

import com.recruit.recruit_CloneCoding.domain.Applicant;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ApplicantRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Applicant applicant){
        em.persist(applicant);
    }

    public Applicant findOne(int id){
        return em.find(Applicant.class,id);
    }

    public List<Applicant> findAll(){
        return em.createQuery("select a from Applicant a",Applicant.class).getResultList();
    }

    public List<Applicant> findByName(String name){
        return em.createQuery("select a from Applicant a where a.name=:name",Applicant.class).setParameter("name",name).getResultList();
    }

    public List<Applicant> findByPart(String part_id){
        return em.createQuery("select a from Applicant a where a.part.id=:part_id",Applicant.class).setParameter("part_id",part_id).getResultList();
    }


}
