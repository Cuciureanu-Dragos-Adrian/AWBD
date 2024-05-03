package app.restman.api.repositories;

import app.restman.api.entities.Reservation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface ReservationRepository extends PagingAndSortingRepository<Reservation, String>
{
    Reservation save (Reservation reservation);

    boolean existsByReservationId (String id);
    Optional<Reservation> findByReservationId (String id);

    void deleteByReservationId (String id);
}
