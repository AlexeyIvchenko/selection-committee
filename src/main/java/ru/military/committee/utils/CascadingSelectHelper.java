package ru.military.committee.utils;

import ru.military.committee.domain.location.City;
import ru.military.committee.domain.location.Region;
import ru.military.committee.domain.personal.Company;
import ru.military.committee.domain.personal.Platoon;
import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.MilitaryEducation;
import ru.military.committee.domain.request.Specialty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CascadingSelectHelper {
    Region region;
    City city;

    MilitaryEducation militaryEducation;
    Faculty faculty;
    Specialty specialty;

    Company company;
    Platoon recruitPlatoon;
}
