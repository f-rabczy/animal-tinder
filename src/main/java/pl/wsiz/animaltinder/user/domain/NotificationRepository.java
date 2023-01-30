package pl.wsiz.animaltinder.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {

    @Query(value = "select n from NotificationEntity n where n.user.id = :userId")
    List<NotificationEntity> findAllByUserId(Long userId);

    @Query(value = "select n from NotificationEntity n where n.user.id = :userId and n.id = :notificationId")
    Optional<NotificationEntity> findNotificationByIdAndUserId(Long notificationId, Long userId);
}
