import 'package:http/http.dart' as http;
import 'dart:convert';

import '../models/reservation_model.dart';
import '../constants.dart' as constants;

class ReservationService {
  static Future<List<ReservationModel>> getReservationListAsc() async {
    final url = Uri.parse('${constants.backendUrl}/reservations/getAllNotExpiredAsc');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final reservationsJson = json.decode(response.body) as List;
      return reservationsJson
          .map((reservation) => ReservationModel.fromJson(reservation))
          .toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<List<ReservationModel>> getReservationListDesc() async {
    final url = Uri.parse('${constants.backendUrl}/reservations/getAllNotExpiredDesc');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final reservationsJson = json.decode(response.body) as List;
      return reservationsJson
          .map((reservation) => ReservationModel.fromJson(reservation))
          .toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<String> addReservation(ReservationModel reservation) async {
    final url = Uri.parse('${constants.backendUrl}/reservations');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: json.encode(reservation.toJson()),
    );

    if (response.statusCode == 201) {
      return response.body;
    } else {
      throw Exception(response.body);
    }
  }

  static Future<void> removeReservationById(String id) async {
    // Implement logic to remove reservation by ID on the backend
    // Send a DELETE request to a suitable endpoint (e.g., /reservations/$id)
    // with the ID parameter and handle the response.

    final url = Uri.parse('${constants.backendUrl}/reservations/$id');
    final response = await http.delete(url);

    if (response.statusCode != 200) {
      throw Exception(response.body);
    }
  }
}
