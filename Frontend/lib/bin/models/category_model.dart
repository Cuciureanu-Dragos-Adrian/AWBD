import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/constants.dart';

class CategoryModel {
  late String name;
  late Icon icon;

  CategoryModel({required this.name, required this.icon});

  CategoryModel.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    icon = sectionIcons[json['icon']];
  }

  Map<String, dynamic> toJson() {
    return {'name': name, 'icon': name};
  }

  @override
  String toString() {
    return jsonEncode(toJson());
  }
}
