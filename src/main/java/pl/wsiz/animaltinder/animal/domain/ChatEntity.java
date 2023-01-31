package pl.wsiz.animaltinder.animal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CHATS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AnimalEntity animalOne;

    @ManyToOne
    private AnimalEntity animalTwo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MessageEntity> messages = new ArrayList<>();

}
