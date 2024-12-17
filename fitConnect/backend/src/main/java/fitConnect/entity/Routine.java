package fitConnect.entity;

import fitConnect.dto.RoutineRequestDto;
import fitConnect.entity.exerciseRoutine.ExerciseRoutine;
import fitConnect.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Routine extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="routine_id",nullable = false)
    private long routineNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="routine_user_id",referencedColumnName = "user_id",nullable = false,columnDefinition = "VARCHAR(255)")
    private User user;

    @Column(name="routine_title",nullable = false)
    private String routineTitle;

    @Column(name="routine_description")
    private String routineDescription;

    @Column(name="is_completed",nullable = false)
    private boolean completed;

    @OneToMany(mappedBy = "exerciseRoutinePK.routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseRoutine> exerciseRoutines;

    public Routine(RoutineRequestDto requestDto){
        this.routineTitle=requestDto.getRoutineTitle();
        this.routineDescription=requestDto.getRoutineDescription();
        this.user=requestDto.getUser();
    }
}
