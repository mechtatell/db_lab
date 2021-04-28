package ru.mechtatell.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "project")
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private String name;
    private Date startDate;
    private Date endDate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "team_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teamList;

    public Project(Plan plan, String name, Date startDate, Date endDate, List<Team> teamList) {
        this.plan = plan;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamList = teamList;
    }
}
