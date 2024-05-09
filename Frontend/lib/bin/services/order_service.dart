import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:restaurant_management_app/bin/models/order_model.dart';
import 'package:restaurant_management_app/bin/services/auth_service.dart';

import '../constants.dart' as constants;

class OrderService {
  static const String _baseUrl = constants.backendUrl + '/orders';

  static Future<List<OrderModel>> getOrderList() async {
    final response = await AuthService.authenticatedRequest(http.get(
        Uri.parse(_baseUrl + "/getAll"),
        headers: {'Authorization': AuthService.authorizationHeader}));
    if (response.statusCode == 200) {
      final jsonData = jsonDecode(response.body) as List<dynamic>;
      return jsonData.map((data) => OrderModel.fromJson(data)).toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<void> addOrder(OrderModel order) async {
    // Convert order to JSON string
    final orderJson = jsonEncode(order.toJson());

    final response = await AuthService.authenticatedRequest(http.post(
      Uri.parse(_baseUrl),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': AuthService.authorizationHeader
      },
      body: orderJson,
    ));

    if (response.statusCode != 201) {
      throw Exception(response.body);
    }
  }

  static Future<void> removeOrdersByTableId(String tableId) async {
    final url = Uri.parse('$_baseUrl/byTableId/$tableId');
    final response = await AuthService.authenticatedRequest(http.delete(url,
        headers: {'Authorization': AuthService.authorizationHeader}));

    if (response.statusCode != 200) {
      throw Exception(response.body);
    }
  }
}
