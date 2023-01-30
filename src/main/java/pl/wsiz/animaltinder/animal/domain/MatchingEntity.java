package pl.wsiz.animaltinder.animal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "MATCHINGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ANIMAL_ID")
    private AnimalEntity owner;

    @Column(name = "MATCHED_ANIMAL_ID")
    @NotNull
    private Long matchedAnimalId;

    @Column(name = "MATCHING_DATE")
    private LocalDate matchingDate;

    @OneToOne(cascade = CascadeType.ALL)
    private ChatEntity chatEntity;
}
