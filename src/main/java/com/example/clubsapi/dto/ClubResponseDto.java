package com.example.clubsapi.dto;

import com.example.clubsapi.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class ClubResponseDto {
    private Long id;
    private String Title;
    private String Contenet;
    private String Date;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContenet() {
        return Contenet;
    }

    public void setContenet(String contenet) {
        Contenet = contenet;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
