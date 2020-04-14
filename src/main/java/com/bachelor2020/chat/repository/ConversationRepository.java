package com.bachelor2020.chat.repository;

import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Conversation findConversationByStudyGroup(StudyGroup studyGroup);
}
