package pl.wsiz.animaltinder.animal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ANIMALS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AnimalEntity invoker;

    @OneToOne
    private AnimalEntity receiver;

    @Column(name = "LIKING_STATUS")
    @Enumerated(EnumType.STRING)
    private LikingStatus likingStatus;

    @Column(name = "MATCHING_STATUS")
    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;
}
