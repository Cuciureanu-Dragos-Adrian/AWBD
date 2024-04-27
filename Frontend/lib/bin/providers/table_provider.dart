import 'package:restaurant_management_app/bin/models/table_model.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class TableProvider {

  static const String _baseUrl = 'http://localhost:8080/tables';

  static Future<List<TableModel>> getTables() async {
    final url = Uri.parse(_baseUrl + '/getAll');
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final jsonData = json.decode(response.body) as List;
      return jsonData.map((data) => TableModel.fromJson(data)).toList();
    } else {
      throw Exception('Failed to fetch tables: ${response.statusCode}');
    }
  }

  static Future<TableModel?> getTableById(String tableId) async {
    final url = Uri.parse('$_baseUrl/$tableId');
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final jsonData = json.decode(response.body) as Map<String, dynamic>;
      return TableModel.fromJson(jsonData);
    } else {
      throw Exception('Failed to get table by ID: ${response.statusCode}');
    }
  }

  static Future<void> addTable(TableModel table) async {
    // Send POST request to backend to create table
    final tableMap = table.toJson();
    final url = Uri.parse(_baseUrl);
    final response = await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: json.encode(tableMap));
    if (response.statusCode != 201) {
      throw Exception('Method returned ${response.statusCode}, ${response.body}');
    }
  }

  static Future<void> editTablePosition(String id, double xOffset, double yOffset) async {
    var table = await getTableById(id);
    if (table != null) {
      table.xOffset = xOffset;
      table.yOffset = yOffset;

      final url = Uri.parse(_baseUrl + '/$id');
      final response = await http.put(
          url,
          headers: {"Content-Type": "application/json"},
          body: json.encode(table.toJson()));
      if (response.statusCode != 200) {
        throw Exception('Failed to update table position: ${response.statusCode}');
      }
    } else {
      throw Exception('Table with ID $id not found');
    }
  }

  static Future<void> removeTable(String tableId) async {
    var table = await getTableById(tableId);
    if (table != null) {
      // Send DELETE request to backend to remove table
      final url = Uri.parse(_baseUrl + '/$tableId');
      final response = await http.delete(url);
      if (response.statusCode != 200) {
        throw Exception('Failed to remove table: ${response.statusCode}');
      } 
    } else {
      throw Exception('Table with ID $tableId not found');
    }
  }
}
