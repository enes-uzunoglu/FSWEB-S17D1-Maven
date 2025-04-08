package com.workintech.fswebs17d1.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Animal {
    @Value("${course.name}")
    private String courseName;

    @Value("${project.developer.fullname}")
    private String developerFullName;

    private int id;
    private String name;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDeveloperFullName() {
        return developerFullName;
    }

    public void setDeveloperFullName(String developerFullName) {
        this.developerFullName = developerFullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
