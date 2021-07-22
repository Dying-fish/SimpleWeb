package com.example.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Illustration {
    private static final long serialVersionUID = -8546972979375001850L;

    @Id
    private Integer pid;
    private String illustrator;
    private String title;
    private String description;
    @Column(unique = true)
    private String url;

    public Illustration() { }

    public Illustration(Integer pid, String illustrator, String title, String description, String url) {
        this.pid = pid;
        this.illustrator = illustrator;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
