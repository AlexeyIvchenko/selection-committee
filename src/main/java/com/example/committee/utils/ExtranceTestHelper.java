package com.example.committee.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ExtranceTestHelper {
    @Id
    private byte id;
    @NotNull
    @Min(0)
    private byte hbResult;

    @NotNull
    @Min(0)
    private double run100mResult;

    @NotNull
    @Min(0)
    private double run3kmResult;

    @NotNull
    @Min(0)
    @Max(3)
    private byte prof_group;
}
