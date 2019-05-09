package com.sms.ppm.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

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
