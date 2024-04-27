import 'package:flutter_test/flutter_test.dart';
import 'package:restaurant_management_app/bin/entities/table_list.dart';
import 'package:restaurant_management_app/bin/models/table_model.dart';
import 'package:restaurant_management_app/bin/services/table_service.dart';

import 'factories/table_factory.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized(); // Initialize Flutter bindings

  group('Table Loading and Saving Integration Tests', () {
    test('loadTables should return a list of TableModel objects', () async {
      // Act: Call the loadTables function
      var tables = await loadTables();

      // Assert: Verify the returned data
      expect(tables, isNotNull); // Check for non-null value
      expect(tables, isA<List<TableModel>>()); // Check for correct data type
    });

    test('saveTables should correctly save the list of TableModel objects',
        () async {
      // Arrange: Create a list of TableModel objects
      var testTables = [
        TablesFactory.createTable()
      ];
      TableList.setTableList(
          testTables); // Assuming this is how you set the tables to be saved

      // Act: Call the saveTables function
      await saveTables();

      // Assert: Verify that the data was saved correctly
      var savedTables = await loadTables();
      expect(savedTables, isNotNull);
      expect(savedTables.length, equals(1));
      expect(savedTables[0].id, equals("A1"));
      expect(savedTables[0].xOffset, equals(0));
      expect(savedTables[0].yOffset, equals(0));
      expect(savedTables[0].tableSize, equals(2));
      expect(savedTables[0].floor, equals(0));
    });
  });
}
