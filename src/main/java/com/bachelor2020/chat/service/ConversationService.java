package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.ChatMessage;
import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.model.ConversationKey;
import com.bachelor2020.chat.model.StudyGroup;
import com.bachelor2020.chat.repository.ConversationRepository;
import com.bachelor2020.chat.repository.StudyGroupRepository;
import com.bachelor2020.chat.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ConversationService {
    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    ConversationKeyService conversationKeyService;

    public Conversation getConversationByStudyGroupID(long studyGroupID){
        System.out.println("Trying to find SG with ID " + studyGroupID);
        StudyGroup studyGroup = studyGroupRepository.getStudyGroupByStudyGroupID(studyGroupID);
        System.out.println("Got sg " + studyGroup.getId() + " sgID " + studyGroup.getStudy_group_id());
        Conversation conversationByStudyGroup = conversationRepository.findConversationByStudyGroup(studyGroup);

        decryptChatMessagesInConversation(conversationByStudyGroup);

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

    /**
     * Decrypts the chat messages in the conversation using the AESUtil class and sets the messages in plaintext.
     * @param conversation
     */
    public void decryptChatMessagesInConversation(Conversation conversation){
        AESUtil aesUtil = new AESUtil();

        //Find the key by conversation
        ConversationKey conversationKey = conversationKeyService.getConversationKeyByConversation(conversation);

        if(conversationKey == null){
            throw new NoSuchElementException("Couldn't find ConversationKey associated with the Conversation");
        }

        //Decrypts message and sets message in plaintext
        for (int i = 0; i < conversation.getChatMessages().size(); i++) {
            String decryptedString = aesUtil.decryptWithKey(conversation.getChatMessages().get(i).getContent(), conversationKey.getSigningKey());
            conversation.getChatMessages().get(i).setContent(decryptedString);
        }
    }
}
