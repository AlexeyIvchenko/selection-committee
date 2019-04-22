package com.example.committee.domain.standarts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "run3km_standarts")
public class Run3Standart {
    @Id
    @Column(name = "standart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte standartId;

    @Column(name = "result")
    private double result;

    @Column(name = "score")
    private byte score;
}
