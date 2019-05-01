package com.example.committee.utils;

import com.example.committee.domain.request.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestAndScore {
    Request request;
    int score;
}
