package ru.military.committee.domain.standarts;

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
@Table(name = "hb_standarts")
public class HBStandart {
    @Id
    @Column(name = "standart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte standartId;

    @Column(name = "result")
    private byte result;

    @Column(name = "score")
    private byte score;
}
