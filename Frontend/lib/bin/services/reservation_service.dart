import 'package:http/http.dart' as http;
import 'package:restaurant_management_app/bin/services/auth_service.dart';
import 'dart:convert';

import '../models/reservation_model.dart';
import '../constants.dart' as constants;

class ReservationService {
  static Future<List<ReservationModel>> getReservationListAsc() async {
    final url =
        Uri.parse('${constants.backendUrl}/reservations/getAllNotExpiredAsc');
    final response = await AuthService.authenticatedRequest(http.get(url,
        headers: {'Authorization': AuthService.authorizationHeader}));

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
    final url =
        Uri.parse('${constants.backendUrl}/reservations/getAllNotExpiredDesc');
    final response = await AuthService.authenticatedRequest(http.get(url,
        headers: {'Authorization': AuthService.authorizationHeader}));

    if (response.statusCode == 200) {
      final reservationsJson = json.decode(response.body) as List;
      return reservationsJson
          .map((reservation) => ReservationModel.fromJson(reservation))
          .toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<List<ReservationModel>> getReservationListPageAsc(
      {int page = 1}) async {
    final url = Uri.parse(
        '${constants.backendUrl}/reservations/getAllNotExpiredPageAsc?page=$page');
    final response = await AuthService.authenticatedRequest(http.get(url,
        headers: {'Authorization': AuthService.authorizationHeader}));

    if (response.statusCode == 200) {
      //final reservationsJson = json.decode(response.body) as List;
      final Map<String, dynamic> responseBody = json.decode(response.body);
      final reservationsJson = responseBody['content'] as List;
      return reservationsJson
          .map((reservation) => ReservationModel.fromJson(reservation))
          .toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<List<ReservationModel>> getReservationListPageDesc(
      {int page = 1}) async {
    final url = Uri.parse(
        '${constants.backendUrl}/reservations/getAllNotExpiredPageDesc?page=$page');
    final response = await AuthService.authenticatedRequest(http.get(url,
        headers: {'Authorization': AuthService.authorizationHeader}));

    if (response.statusCode == 200) {
      //final reservationsJson = json.decode(response.body) as List;
      final Map<String, dynamic> responseBody = json.decode(response.body);
      final reservationsJson = responseBody['content'] as List;
      return reservationsJson
          .map((reservation) => ReservationModel.fromJson(reservation))
          .toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<ReservationModel?> getCurrentReservationByTableId(String tableId) async {
    final response = await AuthService.authenticatedRequest(http.get(
        Uri.parse(constants.backendUrl + "/reservations/getCurrentByTableId/" + tableId),
        headers: {'Authorization': AuthService.authorizationHeader}));

    if (response.statusCode == 200) {
      final jsonData = jsonDecode(response.body) as dynamic;
      return ReservationModel.fromJson(jsonData);
    } else {
      return null;
    }
  }

  static Future<String> addReservation(ReservationModel reservation) async {
    final url = Uri.parse('${constants.backendUrl}/reservations');
    final response = await AuthService.authenticatedRequest(http.post(
      url,
      headers: {'Content-Type': 'application/json',
        'Authorization': AuthService.authorizationHeader
      },
      body: json.encode(reservation.toJson()),
    ));

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
    final response = await AuthService.authenticatedRequest(http.delete(url,
        headers: {'Authorization': AuthService.authorizationHeader}));

    if (response.statusCode != 200) {
      throw Exception(response.body);
    }
  }
}
