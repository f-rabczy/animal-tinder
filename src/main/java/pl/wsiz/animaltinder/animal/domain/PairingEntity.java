package pl.wsiz.animaltinder.animal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "PAIRINGS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PairingEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ANIMAL_ID")
    private AnimalEntity owner;

    @Column(name = "PAIRED_ANIMAL_ID")
    @NotNull
    private Long pairedAnimalId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PairingEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
