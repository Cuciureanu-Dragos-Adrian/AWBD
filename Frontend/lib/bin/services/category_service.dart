import 'package:http/http.dart' as http;
import 'package:restaurant_management_app/bin/models/category_model.dart';
import 'package:restaurant_management_app/bin/services/auth_service.dart';
import 'dart:convert'; // Import for jsonEncode
import '../constants.dart' as constants;

class CategoryService {
  static const String _baseUrl =
      constants.backendUrl + '/menu_categories'; // Construct URL with base path
  static Future<List<CategoryModel>> getCategoryList() async {
    final response = await AuthService.authenticatedRequest(http.get(
        Uri.parse(_baseUrl + "/getAll"),
        headers: {'Authorization': AuthService.authorizationHeader}));
    if (response.statusCode == 200) {
      final jsonData = jsonDecode(response.body) as List<dynamic>;
      return jsonData.map((data) => CategoryModel.fromJson(data)).toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<void> addCategory(CategoryModel category) async {
    final url = Uri.parse(_baseUrl);
    final response = await AuthService.authenticatedRequest(http.post(
      url,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': AuthService.authorizationHeader
      },
      body: jsonEncode(
          category.toJson()), // Convert category to JSON for request body
    ));
    if (response.statusCode != 201) {
      throw Exception(response.body);
    }
  }

  static Future<void> removeCategoryByName(String categoryName) async {
    final url = Uri.parse('$_baseUrl/$categoryName');
    final response = await AuthService.authenticatedRequest(http.delete(url,
        headers: {'Authorization': AuthService.authorizationHeader}));
    if (response.statusCode == 404) {
      throw Exception(
          'Category not found: $categoryName'); // Handle 404 for specific category
    } else if (response.statusCode != 200) {
      throw Exception(response.body);
    }
  }
}
