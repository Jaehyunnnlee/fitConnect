package fitConnect.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id", nullable = false)
    private long exerciseNum;

    @Column(name="exercise_name",nullable = false)
    private String exerciseName;

    @Column(name="exercise_part",nullable=false)
    private String exercisePart;
}
