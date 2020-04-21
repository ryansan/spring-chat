package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.Conversation;
import com.bachelor2020.chat.model.ConversationKey;
import com.bachelor2020.chat.model.Student;
import com.bachelor2020.chat.model.StudyGroup;
import com.bachelor2020.chat.repository.StudentRepository;
import com.bachelor2020.chat.repository.StudyGroupRepository;
import com.bachelor2020.chat.util.AESUtil;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class StudyGroupService {

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ConversationService conversationService;

    @Autowired
    StudentService studentService;

    /**
     * Persists the new study group from the RabbitMQ message.
     * Also persists the students in the group who are not saved in the database.
     * @param studyGroup
     * @return
     */
    public StudyGroup createNewStudyGroup(StudyGroup studyGroup){
        //List of students persisted (Objects that can be saved because they are from DB)
        List<Student> studentsToBeAdded = new ArrayList<>();

        //If the studygroup doesnt exist already, add it
        if(getStudyGroupByStudyGroupID(studyGroup.getStudy_group_id()) == null){
            for (int i = 0; i < studyGroup.getStudents().size(); i++) {
                Student student = studyGroup.getStudents().get(i);
                Student studentByStudentID = studentService.getStudentByStudentID(student.getStudent_id());

                if(studentByStudentID == null){
                    System.out.println("Student with id " + student.getStudent_id() + " doesnt exist, adding now");

                    Student temp = new Student();
                    temp.setStudent_id(student.getStudent_id());
                    temp.setFieldOfStudy(student.getFieldOfStudy());
                    temp.setFirst_name(student.getFirst_name());
                    temp.setLast_name(student.getLast_name());
                    temp.setStudent_number(student.getStudent_number());


                    List<StudyGroup> studyGroups = new ArrayList<>();
                    studyGroups.add(studyGroup);

                    temp.setStudyGroups(studyGroups);
                    Student studentBeingAdded = studentRepository.save(temp);

                    studentsToBeAdded.add(studentBeingAdded);
                    System.out.println("StudentBeingAdded's id: " + studentBeingAdded.getStudent_id());
                }else{
                   studentsToBeAdded.add(studentByStudentID);
                }
            }

            studyGroup.getStudents().clear();
            studyGroup.setStudents(studentsToBeAdded);
            Conversation conversation = new Conversation();
            conversation.setStudyGroup(studyGroup);

            AESUtil aesUtil = new AESUtil();
            //Generate a random secrety key
            byte[] secretKey = aesUtil.generateSecretKey();

            System.out.println("Created key with value " + secretKey);

            //Create new ConversationKey to be used for encrypting/decrypting messages
            ConversationKey conversationKey = new ConversationKey();
            conversationKey.setSigningKey(secretKey);

            System.out.println("Added key to convoKey " + conversationKey.getSigningKey());

            //Set key to conversation
            conversation.setConversationKey(conversationKey);
            //Set conversation to key as well
            conversationKey.setConversation(conversation);

            studyGroup.setConversation(conversation);
            return studyGroupRepository.save(studyGroup);
        }else{
            return studyGroup;
        }
    }

    public boolean createNewConversation(StudyGroup studyGroup){
        Conversation conversation = new Conversation();
        conversation.setStudyGroup(studyGroup);
        return true;
    }

    public StudyGroup getStudyGroupByStudyGroupID(long studyGroupID){
        return studyGroupRepository.getStudyGroupByStudyGroupID(studyGroupID);
    }


}
