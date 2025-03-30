package com.study.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter @Builder @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = "id")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false)
    private String title;
}
