import 'package:restaurant_management_app/bin/models/table_model.dart';
import 'package:http/http.dart' as http;
import 'package:restaurant_management_app/bin/services/auth_service.dart';
import 'dart:convert';

import '../constants.dart' as constants;

class TableService {
  static const String _baseUrl = constants.backendUrl + '/tables';

  static Future<List<TableModel>> getTables() async {
    final url = Uri.parse(_baseUrl + '/getAll');
    final response = await AuthService.authenticatedRequest(http.get(url,
        headers: {'Authorization': AuthService.authorizationHeader}));
    if (response.statusCode == 200) {
      final jsonData = json.decode(response.body) as List;
      return jsonData.map((data) => TableModel.fromJson(data)).toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<TableModel?> getTableById(String tableId) async {
    final url = Uri.parse('$_baseUrl/$tableId');
    final response = await AuthService.authenticatedRequest(http.get(url,
        headers: {'Authorization': AuthService.authorizationHeader}));
    if (response.statusCode == 200) {
      final jsonData = json.decode(response.body) as Map<String, dynamic>;
      return TableModel.fromJson(jsonData);
    } else {
      throw Exception(response.body);
    }
  }

  static Future<void> addTable(TableModel table) async {
    // Send POST request to backend to create table
    final tableMap = table.toJson();
    final url = Uri.parse(_baseUrl);
    final response = await AuthService.authenticatedRequest(http.post(url,
        headers: {
          "Content-Type": "application/json",
          'Authorization': AuthService.authorizationHeader
        },
        body: json.encode(tableMap)));
    if (response.statusCode != 201) {
      throw Exception(response.body);
    }
  }

  static Future<void> editTablePosition(
      String id, double xOffset, double yOffset) async {
    var table = await getTableById(id);
    if (table != null) {
      table.xOffset = xOffset;
      table.yOffset = yOffset;

      final url = Uri.parse(_baseUrl + '/$id');
      final response = await AuthService.authenticatedRequest(http.put(url,
          headers: {
            "Content-Type": "application/json",
            'Authorization': AuthService.authorizationHeader
          },
          body: json.encode(table.toJson())));
      if (response.statusCode != 200) {
        throw Exception(response.body);
      }
    } else {
      throw Exception('Table with ID $id not found');
    }
  }

  static Future<void> removeTableById(String tableId) async {
    var table = await getTableById(tableId);
    if (table != null) {
      // Send DELETE request to backend to remove table
      final url = Uri.parse(_baseUrl + '/$tableId');
      final response = await AuthService.authenticatedRequest(http.delete(url,
          headers: {'Authorization': AuthService.authorizationHeader}));
      if (response.statusCode != 200) {
        throw Exception(response.body);
      }
    } else {
      throw Exception('Table with ID $tableId not found');
    }
  }
}
