// reads and writes to a JSON file
import 'dart:convert';
import 'dart:io';
import 'package:flutter/services.dart' as services;
import 'package:restaurant_management_app/bin/models/globals_model.dart';
import 'package:restaurant_management_app/bin/data_providers/data_provider.dart';

class JsonProvider implements DataProvider {
  static const jsonPath = "assets/json/";
  static const tableFile = "tables.json";
  static const productFile = "products.json";
  static const orderFile = "orders.json";
  static const reservationFile = "reservations.json";
  static const capacitiesFile = "capacities.json";
  static const globalsFile = "globals.json";

  static const tablePath = jsonPath + tableFile;
  static const productPath = jsonPath + productFile;
  static const orderPath = jsonPath + orderFile;
  static const reservationPath = jsonPath + reservationFile;
  static const capacitiesPath = jsonPath + capacitiesFile;
  static const globalsPath = jsonPath + globalsFile;

  JsonProvider();

  @override
  Future<List<int>> readCapacities() async {
    final jsondata = await services.rootBundle.loadString(capacitiesPath);
    final data = json.decode(jsondata) as List<dynamic>;
    return data.cast<int>();
  }

  @override
  Future<void> writeCapacities(List<int> capacities) async {
    await File(capacitiesPath).writeAsString(json.encode(capacities));
  }

  @override
  Future<GlobalsModel> readGlobalObject() async {
    final jsondata = await services.rootBundle.loadString(globalsPath);
    final data = json.decode(jsondata) as dynamic;
    return GlobalsModel.fromJson(data);
  }

  @override
  Future<void> writeGlobalObject(GlobalsModel globals) async {
    String globalsString = globals.toString();
    await File(globalsPath).writeAsString(globalsString);
  }
}
