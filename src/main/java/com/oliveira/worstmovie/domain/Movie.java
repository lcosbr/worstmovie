package com.oliveira.worstmovie.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MOVIES")
@Getter
@Setter
@EqualsAndHashCode
public class Movie {

    @Id
    @Column(name="M_ID")
    private Long id;

    @Column(name="M_YEAR")
    private int year;

    @Column(name="TITLE")
    private String title;

    @Column(name="STUDIOS")
    private String studios;

    @Column(name="PRODUCERS")
    private String producers;

    @Column(name="WINNER")
    private String winner;
}
