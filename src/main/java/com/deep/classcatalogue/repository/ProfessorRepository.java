package com.deep.classcatalogue.repository;

import java.util.List;

import com.deep.classcatalogue.classModel.Professors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ProfessorRepository extends CrudRepository<Professors, Long> {

// SELECT id, name, subject, location, availability FROM professors
// select m from MatchData m where(m.team1 = :teamName or m.team2 = :teamName) and m.date between :dateStart and :dateEnd order by date desc
    //@Query("SELECT id, name, subject, location, availability FROM professors")
    List<Professors> findAll(); 
    
}
