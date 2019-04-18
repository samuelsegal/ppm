package com.sms.ppm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"project"})
@NamedEntityGraph(name="backlog.all", attributeNodes = {
        @NamedAttributeNode("id"),
        @NamedAttributeNode("PTSequence"),
        @NamedAttributeNode("projectIdentifier")
})
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0;
    private String projectIdentifier;

    //OneToOne with project
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id")
    @JsonIgnore
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private Project project;

    //OneToMany projectTasks
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "backlog")
    private List<ProjectTask> projectTasks = new ArrayList<>();


}
