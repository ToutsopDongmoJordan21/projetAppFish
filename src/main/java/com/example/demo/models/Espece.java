package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table( name = "espèces")
public class Espece {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspeceName() {
        return especeName;
    }

    public void setEspeceName(String especeName) {
        this.especeName = especeName;
    }

    public String getEspeceFamily() {
        return especeFamily;
    }

    public void setEspeceFamily(String especeFamily) {
        this.especeFamily = especeFamily;
    }

    public String getEspeceDescription() {
        return especeDescription;
    }

    public void setEspeceDescription(String especeDescription) {
        this.especeDescription = especeDescription;
    }


    public Espece(Long id,
                  String especeName,
                  String especeFamily,
                  String especeDescription) {
        this.id = id;
        this.especeName = especeName;
        this.especeFamily = especeFamily;
        this.especeDescription = especeDescription;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Size(max = 30)
    private String especeName;

    @Size(max = 40)
    private String especeFamily;

    private String especeDescription;


    public Espece() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
    private User user;


}
