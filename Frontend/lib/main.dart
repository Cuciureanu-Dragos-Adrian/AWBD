import 'package:flutter/material.dart';

import 'package:restaurant_management_app/bin/auth.dart';
import 'package:restaurant_management_app/bin/main_app.dart';
import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/data_providers/json_provider.dart';
import 'package:restaurant_management_app/bin/data_providers/data_provider.dart';
import 'package:restaurant_management_app/bin/services/auth_service.dart';
import 'package:restaurant_management_app/bin/utilities/capacity_list.dart';
import 'package:restaurant_management_app/bin/utilities/globals.dart';

DataProvider data = JsonProvider();

Future<void> init() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Globals.loadGlobals();
  await CapacityList.loadCapacityList();
}

void main() async {
  await init();
  runApp(const MyApp());
}

class NavigationService {
  static GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _isLoggedIn = false; // Initial state

  @override
  void initState() {
    super.initState();
    AuthService.loggedInSetterCallback = setLogged;
  }

  void setLogged(bool value) {
    setState(() {
      _isLoggedIn = value;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: NavigationService.navigatorKey,
      theme: ThemeData(
        primaryColor: mainColor,
        primarySwatch: mainMaterialColor,
      ),
      home: _isLoggedIn ? _buildMainApp() : _buildAuth(),
    );
  }

  Widget _buildMainApp() {
    return const MainApp();
  }

  Widget _buildAuth() {
    return const Auth();
  }
}
