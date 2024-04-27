import 'package:restaurant_management_app/bin/models/globals_model.dart';
import 'package:restaurant_management_app/bin/models/product_model.dart';
import 'package:restaurant_management_app/bin/models/reservation_model.dart';
import 'package:restaurant_management_app/bin/models/order_model.dart';

// interface for data storage
// dart does not have interfaces so this is a workaround
abstract class DataProvider {
  Future<List<ProductModel>> readProducts();
  Future<void> writeProducts(List<ProductModel> productList);
  Future<List<OrderModel>> readOrders();
  Future<void> writeOrders(List<OrderModel> orderList);
  Future<List<ReservationModel>> readReservations();
  Future<void> writeReservations(List<ReservationModel> productList);
  Future<List<int>> readCapacities();
  Future<void> writeCapacities(List<int> capacities);
  Future<GlobalsModel> readGlobalObject();
  Future<void> writeGlobalObject(GlobalsModel globals);
}