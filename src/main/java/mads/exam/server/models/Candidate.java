package mads.exam.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Integer personalVotes;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

}
