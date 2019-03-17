package com.example.committee.domain.employee;

import com.example.committee.domain.employee.AppRole;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usert")
public class AppUser {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_surname")
    private String userSurname;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_secondname")
    private String userSecondName;
    @Column(name = "online_status")
    private boolean online;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<AppRole> roles = new HashSet<>();
}
