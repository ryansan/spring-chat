package com.bachelor2020.chat.repository;

import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.model.ConversationKey;
import com.bachelor2020.chat.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationKeyRepository extends JpaRepository<ConversationKey, Long> {
    //@Query("select s from StudyGroup as s where s.study_group_id = :studyGroupID")
    ConversationKey findConversationKeyByConversation(Conversation conversation);
}
