package app.restman.api.repositories;

import app.restman.api.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.Optional;

@Repository
public interface ReservationRepository extends PagingAndSortingRepository<Reservation, String>
{
    Reservation save (Reservation reservation);

}

//@Repository
//public interface ReservationRepository extends PagingAndSortingRepository<Reservation, String>
//{
//    Optional<Reservation> findByReservationId (String id);
//    Optional<Reservation> existsByReservationId (String id);
//    Optional<Reservation> find ();
//
//    void delete (String id);
//
//    Reservation save (Reservation reservation);
//}
    Optional<Reservation> findByReservationId (String id);
    boolean existsByReservationId (String id);

    void deleteByReservationId (String id);
}
