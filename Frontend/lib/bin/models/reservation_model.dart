import 'dart:convert';

class ReservationModel {
  late String reservationId;
  late int numberOfPeople;
  late String name;
  late DateTime dateTime;
  late String tableId;

  ReservationModel(
      { required this.reservationId,
      required this.numberOfPeople,
      required this.name,
      required this.dateTime,
      required this.tableId});

  ReservationModel.fromJson(Map<String, dynamic> json) {
    reservationId = json['reservationId'];
    name = json['name'];
    numberOfPeople = json['numberOfPeople'];
    dateTime = DateTime.parse(json['dateTime']);
    tableId = json['tableId'];
  }

  @override
  String toString() {
    return jsonEncode(toJson());
  }

  Map<String, dynamic> toJson() {
    return {
      'reservationId': reservationId,
      'name': name,
      'numberOfPeople': numberOfPeople,
      'dateTime': dateTime.toIso8601String() + "Z",
      'tableId': tableId
    };
  }
}