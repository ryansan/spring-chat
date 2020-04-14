package com.bachelor2020.chat.repository;

import com.bachelor2020.chat.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

//    @Query(value = "SELECT * FROM study_group s WHERE s.study_group_id = :studyGroupID",nativeQuery = true)
    @Query("select s from StudyGroup as s where s.study_group_id = :studyGroupID")
    StudyGroup getStudyGroupByStudyGroupID(@Param("studyGroupID") Long studyGroupID);
}