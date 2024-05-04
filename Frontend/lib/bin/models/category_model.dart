import 'dart:convert';

import 'package:flutter/material.dart';

class CategoryModel {
  late String name;
  //TODO - for now, all categories use a default Icon
  late IconData icon;

  CategoryModel({required this.name, required this.icon});

  CategoryModel.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    icon = Icons.food_bank;
  }

  Map<String, dynamic> toJson() {
    return {'name': name, 'iconBase64': ''};
  }

  @override
  String toString() {
    return jsonEncode(toJson());
  }
}
