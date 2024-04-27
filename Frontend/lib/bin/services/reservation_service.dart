import '../../main.dart';
import '../entities/reservation_list.dart';
import '../models/reservation_model.dart';

Future<List<ReservationModel>> loadReservations() async {
  List<ReservationModel> reservations = await data.readReservations();
  return reservations;
}

void saveReservations() {
  List<ReservationModel> toSave = ReservationList.getReservationList();
  data.writeReservations(toSave);
}

DateTime? _selectedDate;

void setSelectedDate(DateTime toSet){
  _selectedDate = toSet;
}

DateTime getSelectedDate(){
  _selectedDate ??= DateTime.now();
  return _selectedDate!;
}


