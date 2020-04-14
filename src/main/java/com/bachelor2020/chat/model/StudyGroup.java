package com.bachelor2020.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "study_group_id")
@Entity
@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long study_group_id;

    private String groupName;
    private String startDate;
    private String description;
    private boolean acceptingNewStudents;

    //@JsonManagedReference@JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "students_in_studygroup",
            joinColumns = @JoinColumn(name = "study_group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"),
            uniqueConstraints = {@UniqueConstraint(
                    columnNames = {"study_group_id", "student_id"},
                    name = "Unique_Student_in_StudyGroup_Constraint"
            )
            }
    )
    private List<Student> students;


    public StudyGroup(String groupName, String startDate) {
        this.groupName = groupName;
        this.startDate = startDate;
    }


    //@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "studyGroup")
    private Conversation conversation;
/*

    @JsonIgnore
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<Request> requests;

    @JsonIgnore
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<GroupInvite> groupInvites;

    @JsonIgnore
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<InviteNotification> inviteNotifications;*/
}
