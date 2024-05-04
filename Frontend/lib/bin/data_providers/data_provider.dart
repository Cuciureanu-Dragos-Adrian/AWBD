import 'package:restaurant_management_app/bin/models/globals_model.dart';

// interface for data storage
// dart does not have interfaces so this is a workaround
abstract class DataProvider {
  Future<List<int>> readCapacities();
  Future<void> writeCapacities(List<int> capacities);
  Future<GlobalsModel> readGlobalObject();
  Future<void> writeGlobalObject(GlobalsModel globals);
}