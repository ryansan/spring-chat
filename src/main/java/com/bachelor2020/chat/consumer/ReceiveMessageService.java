package com.bachelor2020.chat.consumer;


import com.bachelor2020.chat.model.StudyGroup;
import com.bachelor2020.chat.service.StudyGroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveMessageService {

    @Autowired
    StudyGroupService studyGroupService;

    /**
     * Gets message from RabbitMQ, reads value and creates a StudyGroup object.
     * If object is valid, a new StudyGroup will be created and persisted to the database.
     * @param message
     */
    public void handleMessage(String message){
        System.out.println("HandleMessage: ");
        System.out.println(message);

        StudyGroup studyGroup = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            studyGroup = mapper.readValue(message, StudyGroup.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(studyGroup != null){
            System.out.println("studyGroup with ID " + studyGroup.getStudy_group_id() + " has been made");
            System.out.println("Now saving to DB");
            StudyGroup newStudentGroup = studyGroupService.createNewStudyGroup(studyGroup);
            System.out.println("SG in chatDB ID:" + newStudentGroup.getStudy_group_id());
            //System.out.println("SG in chatDB ID:" + newStudentGroup.getId());
        }else{
            System.out.println("Test == null..");
        }
    }
}