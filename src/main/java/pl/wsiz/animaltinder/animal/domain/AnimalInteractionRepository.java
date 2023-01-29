package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalInteractionRepository extends JpaRepository<AnimalInteractionEntity,Long> {

    AnimalInteractionEntity findByInvokerAndReceiver(AnimalEntity invoker, AnimalEntity receiver);
}
