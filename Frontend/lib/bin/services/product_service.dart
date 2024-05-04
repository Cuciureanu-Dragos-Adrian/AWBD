import 'package:restaurant_management_app/bin/models/product_model.dart';
import 'package:http/http.dart' as http;
import 'dart:convert'; // Import for jsonEncode
import '../constants.dart' as constants;

class ProductService {
  static const String _baseUrl = constants.backendUrl + '/products'; // Construct URL with base path

  static Future<List<ProductModel>> getProductList() async {
    final response = await http.get(Uri.parse(_baseUrl + "/getAll"));
    if (response.statusCode == 200) {
      final jsonData = jsonDecode(response.body) as List<dynamic>;
      return jsonData.map((data) => ProductModel.fromJson(data)).toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<List<ProductModel>> getProductListByCategory(String category) async {
    final response = await http.get(Uri.parse(_baseUrl + "/getAllByCategory/" + category));
    if (response.statusCode == 200) {
      final jsonData = jsonDecode(response.body) as List<dynamic>;
      return jsonData.map((data) => ProductModel.fromJson(data)).toList();
    } else {
      throw Exception(response.body);
    }
  }

  static Future<ProductModel> getProductByName(String productName) async {
    final url = Uri.parse('$_baseUrl/$productName');
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final jsonData = jsonDecode(response.body) as Map<String, dynamic>;
      return ProductModel.fromJson(jsonData);
    } else if (response.statusCode == 404) {
      throw Exception('Product not found: $productName'); // Handle 404 for specific product
    } else {
      throw Exception(response.body);
    }
  }

  static Future<void> addProduct(ProductModel product) async {
    final url = Uri.parse(_baseUrl);
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(product.toJson()), // Convert product to JSON for request body
    );
    if (response.statusCode != 201) {
      throw Exception(response.body);
    }
  }
  
  static Future<void> removeProductByName(String productName) async {
    final url = Uri.parse('$_baseUrl/$productName');
    final response = await http.delete(url);
    if (response.statusCode == 404) {
      throw Exception('Product not found: $productName'); // Handle 404 for specific product
    } else if (response.statusCode != 200){
      throw Exception(response.body);
    }
  }
}
