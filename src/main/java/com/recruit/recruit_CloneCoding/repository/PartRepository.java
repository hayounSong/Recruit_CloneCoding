package com.recruit.recruit_CloneCoding.repository;

import com.recruit.recruit_CloneCoding.domain.Part;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartRepository {
    @PersistenceContext
    EntityManager em;

    public List<Part> findByName(String name){
        return em.createQuery("select p from Part p where p.partName=:name", Part.class).setParameter("name",name).getResultList();
    }
}
