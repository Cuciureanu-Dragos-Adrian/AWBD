import 'package:restaurant_management_app/main.dart';

/// Loads a list of orders, saves it to TableList and returns it
///
Future<List<int>> loadCapacities() async {
  return (await data.readCapacities());
}

///Saves orders to disk
///
void saveCapacities() {
  List<int> toSave = CapacityList.getCapacityList();
  data.writeCapacities(toSave); // use global data service to store tables
}

CapacityList? object;

class CapacityList {
  late List<int> _capacities;
  CapacityList._();

  static Future<void> loadCapacityList() async {
    if (object == null) {
      object = CapacityList._();
      object!._capacities = await loadCapacities();
    }
  }

  static List<int> getCapacityList() {
    object ??= CapacityList._();

    return object!._capacities;
  }

  static void setCapacities(List<int> newTables) {
    object ??= CapacityList._();
    object!._capacities = newTables;
  }
}
