package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.*;
import com.bachelor2020.chat.repository.ChatMessageRepository;
import com.bachelor2020.chat.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private ConversationKeyService conversationKeyService;

    /**
     * Creates a new ChatMessage object by using values from incomingMessage object of type Message.
     * Saves a ChatMessage with encrypted content but returns a ChatMessage with content in plaintext
     * @param incomingMessage
     * @param studyGroupID
     * @return
     */
    public ChatMessage createNewChatMessage(Message incomingMessage, long studyGroupID){
        ChatMessage message = new ChatMessage();

        //Set content of message
        message.setContent(incomingMessage.getContent());

        //If time and date received from client for any reason isn't set, get timestamp from LocalDate and LocalTime objects
        if(incomingMessage.getSentDate() == null && incomingMessage.getSentDate() == null){
            setDateAndTimeUsingLocalDateAndLocalTime(message);
        }else{
            message.setSentDate(incomingMessage.getSentDate());
            message.setSentTime(incomingMessage.getSentTime());
        }

        System.out.println("Trying to find student with ID " + incomingMessage.getStudentID());
        //Set student sending the message
        Student student = studentService.getStudentByStudentID(incomingMessage.getStudentID());
        if(student != null) {
            message.setStudent(student);
        }else{
            System.out.println("Couldn't find student");
        }

        //Get Conversation by the StudyGroupID
        StudyGroup studyGroup = studyGroupService.getStudyGroupByStudyGroupID(studyGroupID);
        Conversation conversation = null;

        if(studyGroup != null){
            conversation = studyGroup.getConversation();
        }else{
            System.out.println("Couldnt find studygroup");
        }

        if(conversation != null){
            message.setConversation(conversation);
        }else{
            System.out.println("Couldn't find conversation");
        }

        //copy of original message used for directly sending to message broker instead of decpryting again before sending
        ChatMessage copyOfChatMessage = createCopyOfChatMessage(message);

        //Encrypt the content of the message
        encryptMessageContent(message, conversation);

        //save original message with encrypted content
        chatMessageRepository.save(message);

        //Return the copy, which contains plaintext of the content in the chat message received
        return copyOfChatMessage;
    }

    /**
     * Encrypts content of Message object by getting signing key from ConversationKey object by Conversation object
     * @param message
     * @param conversation
     */
    private void encryptMessageContent(ChatMessage message, Conversation conversation) {
        //Get ConversationKey by Conversation
        ConversationKey conversationKey = conversationKeyService.getConversationKeyByConversation(conversation);

        if(conversationKey == null){
            throw new NoSuchElementException("Couldn't find ConversationKey associated with the Conversation");
        }

        //Utility class used for encrypting/decrypting
        AESUtil aesUtil = new AESUtil();

        //Encrypt message content and set the encrypted message content to the Chat Message object
        message.setContent(aesUtil.encryptWithKey(message.getContent(), conversationKey.getSigningKey()));
    }

    /**
     * Sets date and time using LocalDate and LocalTime
     * @param message
     */
    private void setDateAndTimeUsingLocalDateAndLocalTime(ChatMessage message) {
        message.setSentDate(java.sql.Date.valueOf(LocalDate.now()));
        message.setSentTime(java.sql.Time.valueOf(LocalTime.now()));
    }

    /**
     * Creates a copy of originalMessage
     * @param originalMessage
     * @return
     */
    public ChatMessage createCopyOfChatMessage(ChatMessage originalMessage){
        ChatMessage copyOfOriginalMessage = new ChatMessage();
        copyOfOriginalMessage.setContent(originalMessage.getContent());
        copyOfOriginalMessage.setStudent(originalMessage.getStudent());
        copyOfOriginalMessage.setConversation(originalMessage.getConversation());
        copyOfOriginalMessage.setSentTime(originalMessage.getSentTime());
        copyOfOriginalMessage.setSentDate(originalMessage.getSentDate());
        return copyOfOriginalMessage;
    }
}
