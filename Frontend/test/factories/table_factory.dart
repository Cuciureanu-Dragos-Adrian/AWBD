import 'package:restaurant_management_app/bin/models/table_model.dart';

class TablesFactory {
  static TableModel createTable({String id = "A1", int tableSize = 2, int floor = 0}) {
    return TableModel(id: id, xOffset: 0, yOffset: 0, tableSize: tableSize, floor: floor, hasOrder: false, hasReservation: false);
  }

  static List<TableModel> createDefaultTables({int floor = 0}){
    return [
      TableModel(id: "A1", xOffset: 0, yOffset: 0, tableSize: 2, floor: floor, hasOrder: false, hasReservation: false),
      TableModel(id: "B1", xOffset: 0, yOffset: 0, tableSize: 3, floor: floor, hasOrder: false, hasReservation: false),
      TableModel(id: "C1", xOffset: 0, yOffset: 0, tableSize: 4, floor: floor, hasOrder: false, hasReservation: false),
      TableModel(id: "D1", xOffset: 0, yOffset: 0, tableSize: 6, floor: floor, hasOrder: false, hasReservation: false),
      TableModel(id: "E1", xOffset: 0, yOffset: 0, tableSize: 8, floor: floor, hasOrder: false, hasReservation: false)
    ];
  }
}
