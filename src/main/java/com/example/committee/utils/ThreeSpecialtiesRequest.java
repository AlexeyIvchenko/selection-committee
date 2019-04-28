package com.example.committee.utils;

import com.example.committee.domain.request.Faculty;
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
    private Faculty firstPriorityFaculty;
    private Faculty secondPriorityFaculty;
    private Faculty thirdPriorityFaculty;

    private Specialty firstPriority;
    private Specialty secondPriority;
    private Specialty thirdPriority;
}
