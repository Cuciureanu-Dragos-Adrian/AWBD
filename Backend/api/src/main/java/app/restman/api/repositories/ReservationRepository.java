package app.restman.api.repositories;

import app.restman.api.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String>
{

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