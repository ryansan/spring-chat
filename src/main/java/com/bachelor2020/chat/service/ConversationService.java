package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.model.StudyGroup;
import com.bachelor2020.chat.repository.ConversationRepository;
import com.bachelor2020.chat.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    public Conversation getConversationByStudyGroupID(long studyGroupID){
        System.out.println("Trying to find SG with ID " + studyGroupID);
        StudyGroup studyGroup = studyGroupRepository.getStudyGroupByStudyGroupID(studyGroupID);
        System.out.println("Got sg " + studyGroup.getId() + " sgID " + studyGroup.getStudy_group_id());
        Conversation conversationByStudyGroup = conversationRepository.findConversationByStudyGroup(studyGroup);

        //System.out.println(conversationByStudyGroup);
        return conversationByStudyGroup;
    }

    public Conversation createNewConversation(StudyGroup studyGroup){
        Conversation conversation = new Conversation();
        conversation.setStudyGroup(studyGroup);
        return conversationRepository.save(conversation);
    }

    public Conversation getConversationByID(long conversationID){
        return conversationRepository.findById(conversationID).get();
    }

    public Conversation saveConversation(Conversation conversation){
        return conversationRepository.save(conversation);
    }
}
