package com.example.clubsapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clubs")
public class Clubs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private LocalDate createdOn;

    @OneToMany(mappedBy = "clubs",cascade = CascadeType.REMOVE)
    private List<Event> events = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "club_user",
            joinColumns = @JoinColumn(name = "club_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<UserEntity> userEntitySet = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Set<UserEntity> getUserEntitySet() {
        return userEntitySet;
    }

    public void setUserEntitySet(Set<UserEntity> userEntitySet) {
        this.userEntitySet = userEntitySet;
    }
}
