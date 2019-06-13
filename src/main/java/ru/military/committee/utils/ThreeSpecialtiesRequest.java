package ru.military.committee.utils;

import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.Specialty;
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
