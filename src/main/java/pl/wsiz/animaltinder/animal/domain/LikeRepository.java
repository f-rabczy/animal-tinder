package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity,Long> {

    LikeEntity findByInvokerAndReceiver(AnimalEntity invoker, AnimalEntity receiver);
}
