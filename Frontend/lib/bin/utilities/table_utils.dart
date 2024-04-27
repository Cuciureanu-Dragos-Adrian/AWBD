// ignore: file_names
import 'package:flutter/material.dart';
import '../constants.dart';
import '../models/table_model.dart';
import '../models/order_model.dart';
import '../services/order_service.dart';
import '../services/reservation_service.dart';
import '../models/reservation_model.dart';
import '../widgets/table_widget.dart';

/// Returns a list of MovableTable from a list of tables
///
/// @param tables: a list of tables
/// @param constraints: a MovableTable needs the BoxConstraints from where it is created
List<MovableTableWidget> getWidgetsFromTables(
    List<TableModel> tables, BoxConstraints constraints) {
  List<MovableTableWidget> result = [];

  for (TableModel table in tables) {
    UniqueKey key = UniqueKey();
    result.add(MovableTableWidget(
      key: key, //assigns new unique key to prevent states from jumping over
      constraints: constraints,
      tableSize: table.tableSize,
      position: Offset(table.xOffset, table.yOffset),
      id: table.id,
      floor: table.floor,
    ));
  }

  return result;
}

/// Returns a list of Table from a list of MovableTable widgets
///
///@param tableWidgets: list of widgets
List<TableModel> getTablesFromTableWidgets(
    List<MovableTableWidget> tableWidgets) {
  List<TableModel> tables = [];

  for (MovableTableWidget widget in tableWidgets) {
    tables.add(TableModel(
        id: widget.id,
        xOffset: widget.position.dx,
        yOffset: widget.position.dy,
        tableSize: widget.tableSize,
        floor: widget.floor));
  }

  return tables;
}

/// Generates unique ID for a table. Must receive either a list of tables or list of tableWidgets.
///
/// @param(optional) tables = a list of tables
/// @param(optional)
String generateTableId(
    {List<TableModel>? tables,
    List<MovableTableWidget>? tableWidgets,
    required int tableSize}) {
  /// Returns corresponding table letter, given a size
  ///
  String getTableLetterFromSize() {
    switch (tableSize) {
      case 2:
        return 'A';
      case 3:
        return 'B';
      case 4:
        return 'C';
      case 6:
        return 'D';
      case 8:
        return 'E';
    }
    throw Exception("Invalid table size!");
  }

  if (tables != null && tableWidgets != null) {
    throw Exception("Invalid parameters provided!");
  }

  var filteredTableIds = [];

  if (tables != null) {
    filteredTableIds = tables.where((x) => x.tableSize == tableSize).map((x) {
      return x.id;
    }).toList(); // get tables of same size and select only ids
  } else if (tableWidgets != null) {
    filteredTableIds =
        tableWidgets.where((x) => x.tableSize == tableSize).map((x) {
      return x.id;
    }).toList(); // get tables of same size and select only ids
  } else {
    throw Exception("Both list parameters were null!");
  }

  var frequency = [
    for (var i = 0; i < filteredTableIds.length; i++) false
  ]; // generate frequency vector

  for (var id in filteredTableIds) {
    var tableIndex = int.parse(id.substring(1));

    if (tableIndex - 1 < filteredTableIds.length) {
      frequency[tableIndex - 1] = true; // indexing starts from 0, subtract
    }
  }
  //search
  for (var i = 0; i < frequency.length; i++) {
    if (frequency[i] == false) {
      return "${getTableLetterFromSize()}${i + 1}";
    } //indexing starts from 0;
  }

  // no unused index was found, return length + 1
  return "${getTableLetterFromSize()}${frequency.length + 1}";
}

TableModel getTableModelFromWidget(MovableTableWidget widget) {
  return TableModel(
      id: widget.id,
      xOffset: widget.position.dx,
      yOffset: widget.position.dy,
      tableSize: widget.tableSize,
      floor: widget.floor);
}

Future<OrderModel?> getAssignedOrder(String tableId) async {
  OrderModel? result;

  var ordersList = await OrderService.getOrderList();
  var tableOrders = ordersList.where((element) => element.tableId == tableId);

  if (tableOrders.isEmpty) {
    result = null;
  } else {
    result = tableOrders.first;
  }

  return result;
}

Future<ReservationModel?> getUpcomingReservation(String tableId) async{
  ReservationModel? result;

  var reservationExpirationTerm =
      DateTime.now().add(const Duration(hours: reservationDurationHours));
  var tableReservations = (await ReservationService.getReservationList())
      .where((reservation) =>
          reservation.tableId == tableId &&
          reservation.dateTime.isBefore(reservationExpirationTerm));
  if (tableReservations.isEmpty) {
    result = null;
  } else {
    result = tableReservations.first;
  }

  return result;
}
