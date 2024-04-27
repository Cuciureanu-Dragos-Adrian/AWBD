import 'package:restaurant_management_app/bin/constants.dart';

import '../models/reservation_model.dart';
import '../services/reservation_service.dart';

ReservationList? object;

class ReservationList {
  late List<ReservationModel> _reservations;
  ReservationList._();

  static Future<void> loadReservationList() async {
    if (object == null) {
      object = ReservationList._();
      object!._reservations = await loadReservations();
    }

    removeExpiredReservations();
  }

  static List<ReservationModel> getReservationList() {
    object ??= ReservationList._();

    return object!._reservations;
  }

  static void removeExpiredReservations() {
    List<ReservationModel> reservations = getReservationList();
    //delete all expired reservations
    List<ReservationModel> toRemove = [];
    for (var reservation in reservations) {
      if (reservation.dateTime.isBefore(DateTime.now().subtract(const Duration(hours: reservationDuration)))) {
        toRemove.add(reservation);
      }
    }

    for (var removed in toRemove) {
      reservations.remove(removed);
    }

    setReservationList(reservations);
  }

  static Future<bool> checkValidReservation(
      ReservationModel reservation) async {
    DateTime myDt = reservation.dateTime;

    for (ReservationModel res in object!._reservations) {
      if (res.tableId == reservation.tableId) {
        DateTime startDateTime = res.dateTime;
        DateTime endDateTime =
            startDateTime.add(const Duration(hours: reservationDuration));

        if (myDt.isAfter(startDateTime) && myDt.isBefore(endDateTime)) {
          return false;
        }

        if (myDt.compareTo(startDateTime) == 0) {
          return false;
        }
      }
    }
    return true;
  }

  static void setReservationList(List<ReservationModel> reservations) {
    object ??= ReservationList._();
    object!._reservations = reservations;
  }

  static Future<bool> addReservation(ReservationModel reservation) async {
    bool validReservation = true;
    validReservation = await checkValidReservation(reservation);

    //remove the expired reservations
    ReservationList.removeExpiredReservations();

    if (validReservation) {
      object ??= ReservationList._();
      object!._reservations.add(reservation);
      return true;
    }
    return false;
  }

  ///Removes a reservation with a given name and date
  static void removeReservationByNameAndId(
      String reservationName, DateTime dt) {
    object ??= ReservationList._();
    object!._reservations.removeWhere((element) =>
        element.name.toLowerCase() == reservationName.toLowerCase() &&
        element.dateTime == dt);
  }

  //Removes all reservations with a given id
  static void removeReservationsById(
      String id) {
    object ??= ReservationList._();
    object!._reservations.removeWhere((element) =>
        element.tableId == id);
  }
}
