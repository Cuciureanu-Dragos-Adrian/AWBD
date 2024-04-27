import 'package:flutter_test/flutter_test.dart';
import 'package:restaurant_management_app/bin/models/table_model.dart';
import 'package:restaurant_management_app/bin/utilities/table_utils.dart';

import 'factories/table_factory.dart';

void main() {
  group("generateTableId", () {
    var tableSizes = {2: "A", 3: "B", 4: "C", 6: "D", 8: "E"};

    group("no tables exist", () {
      List<TableModel> tables = [];

      tableSizes.forEach((key, value) {
        test("$key seats table", () {
          var result = generateTableId(tableSize: key, tables: tables);
          expect(result, "${value}1");
        });
      });
    });

    group("tables exist", () {
      var tables = TablesFactory.createDefaultTables();

      tableSizes.forEach((key, value) {
        test("$key seats table", () {
          var result = generateTableId(tableSize: key, tables: tables);
          expect(result, "${value}2");
        });
      });
    });
  });
}