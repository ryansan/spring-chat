package com.bachelor2020.chat.model;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long student_id;
    private String first_name;
    private String last_name;
    private String student_number;
    private String fieldOfStudy;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;


/*
    @JsonIgnore
    @OneToOne(mappedBy = "student")
    private StudentCalendar calendar;
*/
    //@JsonBackReference
    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private List<StudyGroup> studyGroups;
/*
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Request> requests;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<GroupInvite> groupInvites;

    //@JsonIgnore
    //@OneToMany(
    //         mappedBy = "student"
    // )
    // private List<Request> requests;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List <InviteNotification> inviteNotifications;*/
}
