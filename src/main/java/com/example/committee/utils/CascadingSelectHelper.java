package com.example.committee.utils;

import com.example.committee.domain.location.City;
import com.example.committee.domain.location.Region;
import com.example.committee.domain.request.Faculty;
import com.example.committee.domain.request.MilitaryEducation;
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
}
