package com.example.clubsapi.entity;

/*
* This entiy will manage data ownership by keeping one to one realtioship with
* club and moderator
*
* That means one club will have one moderator
*
* */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "club_moderator")
public class ClubModerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @ManyToOne
    @JoinColumn(name = "club_id",referencedColumnName = "id")
    private Clubs club;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private  UserEntity user;
}
