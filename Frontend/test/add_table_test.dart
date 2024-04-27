import 'package:flutter_test/flutter_test.dart';
import 'package:restaurant_management_app/bin/entities/table_list.dart';
//import 'package:restaurant_management_app/bin/models/table_model.dart';

import 'factories/table_factory.dart';


void main() {
  TableList.setTableList([]);

  test("add table", () async {
    expect((TableList.getTableList()).length, 0);

    var t = TablesFactory.createTable();
    TableList.addTable(t);

    var tables = TableList.getTableList();
    expect(tables.length, 1);
    expect(tables[0].id, t.id);
  });
}
