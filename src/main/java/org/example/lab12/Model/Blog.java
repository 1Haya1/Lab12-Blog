package org.example.lab12.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Blog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "cant be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String title;




    @NotEmpty(message = "cant be empty")
//    @Size(min = 30 , message = "body should be more than 30 char")
    @Column(columnDefinition = "varchar(20) not null")
    private String body;




    @ManyToOne
    @JsonIgnore
    private User user;


}
