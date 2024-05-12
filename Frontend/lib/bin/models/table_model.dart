import 'dart:convert';

class TableModel {
  late String id;
  late double xOffset, yOffset;
  late int tableSize;
  late int floor;
  late bool hasReservation;
  late bool hasOrder;

  TableModel({
    required this.id,
    required this.xOffset,
    required this.yOffset,
    required this.tableSize,
    required this.floor,
    required this.hasReservation,
    required this.hasOrder
  });

  factory TableModel.fromJson(Map<String, dynamic> json) {
    return TableModel(
      id: json['tableId'],
      xOffset: json['xoffset'],
      yOffset: json['yoffset'],
      tableSize: json['tableSize'],
      floor: json['floor'],
      hasOrder: json['hasOrder'],
      hasReservation: json['hasReservation']
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'tableId': id,
      'xoffset': xOffset,
      'yoffset': yOffset,
      'tableSize': tableSize,
      'floor': floor,
    };
  }

  @override
  String toString() {
    return jsonEncode(toJson());
  }
}
