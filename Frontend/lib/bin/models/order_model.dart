import 'dart:convert';

import 'package:restaurant_management_app/bin/models/product_model.dart';

class OrderModel {
  late String orderId;
  late List<ProductModel> products; // Maintains ProductModel list
  late List<int> quantities;
  late double price;
  late String tableId;

  OrderModel({
    required this.orderId,
    required this.products,
    required this.quantities,
    required this.price,
    required this.tableId,
  });

  // No longer needed, as products are directly assigned
  // OrderModel.fromJson(Map<String, dynamic> json) { ... }

  @override
  String toString() {
    return jsonEncode(toJson());
  }

  Map<String, dynamic> toJson() {
    // Convert product list to list of product names (IDs)
    final productIds = products.map((product) => product.name).toList();
    return {
      'orderId': orderId,
      'tableId': tableId,
      'price': price,
      'productNames': productIds,
      'quantities': quantities,
    };
  }

  factory OrderModel.fromJson(Map<String, dynamic> json) {
    final orderId = json['orderId'] as String;
    final tableId = json['tableId'] as String;
    final price = json['price'] as double;

    final jsonProducts = json['products'] as List<dynamic>;
    final quantities = json['quantities'] as List<dynamic>;

    final products = <ProductModel>[];

    for (var json in jsonProducts) {
      products.add(ProductModel.fromJson(json));
    }

    return OrderModel(
      orderId: orderId,
      products: products,
      quantities: quantities.cast<int>(), // Cast to List<int>
      price: price,
      tableId: tableId,
    );
  }
}
