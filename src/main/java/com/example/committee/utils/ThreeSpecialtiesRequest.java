package com.example.committee.utils;

import com.example.committee.domain.request.Request;
import com.example.committee.domain.request.Specialty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeSpecialtiesRequest {
    private Specialty firstPriority;
    private Specialty secondPriority;
    private Specialty thirdPriority;
}
