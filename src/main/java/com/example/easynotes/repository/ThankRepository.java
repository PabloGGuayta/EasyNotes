package com.example.easynotes.repository;

import com.example.easynotes.model.Thank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

public interface ThankRepository extends JpaRepository<Thank, Long> {

    @Query("SELECT COUNT(note) as cant_thanks " +
            "from Note note inner join note.thanks as thank " +
            "where note.id =:noteId")
     Integer findThanksCountByNoteId(Long noteId);

}
