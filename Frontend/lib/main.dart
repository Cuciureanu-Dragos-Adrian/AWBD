import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/data_providers/json_provider.dart';
import 'package:restaurant_management_app/bin/providers/capacity_list.dart';
import 'package:restaurant_management_app/bin/providers/order_list.dart';
import 'package:restaurant_management_app/bin/providers/product_list.dart';
import 'package:restaurant_management_app/bin/widgets/floorplan.dart';
import 'package:restaurant_management_app/bin/widgets/orders.dart';
import 'package:restaurant_management_app/bin/widgets/reservations_widget.dart';
import 'package:restaurant_management_app/bin/widgets/table_manager.dart';

import 'bin/data_providers/data_provider.dart';
import 'bin/providers/globals.dart';
import 'bin/widgets/menu.dart';

DataProvider data = JsonProvider();

Future<void> init() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Globals.loadGlobals();
  await CapacityList.loadCapacityList();
  await ProductList.loadProductList();
  await OrderList.loadOrderList();
}

void main() async {
  await init();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primaryColor: mainColor,
        primarySwatch: mainMaterialColor,
      ),
      home: DefaultTabController(
        length: 5,
        child: Scaffold(
          backgroundColor: accent2Color,
          appBar: AppBar(
              bottom: const TabBar(
                tabs: <Widget>[
                  Tab(icon: Icon(Icons.bookmark_added_outlined),
                      text: "Manage tables"),
                  Tab(icon: Icon(Icons.view_list_outlined),
                      text: "View order list"),
                  Tab(icon: Icon(Icons.view_list_outlined),
                      text: "Manage reservations"),
                  Tab(icon: Icon(Icons.menu_book_outlined), 
                      text: "Edit menu"),
                  Tab(icon: Icon(Icons.create_outlined),
                      text: "Edit floor plan"),
                ],
                indicatorColor: accent2Color,
                indicatorWeight: 3,
                labelColor: accent2Color,
                unselectedLabelColor: accent1Color,
              ),
              centerTitle: true,
              title: const Text(
                'RESTAURANT MANAGER',
                style: TextStyle(
                  fontSize: 24,
                  color: accent2Color,
                  fontWeight: FontWeight.w600,
                ),
              ),
              backgroundColor: mainColor),
          body: TabBarView(
            physics: const BouncingScrollPhysics(),
            dragStartBehavior: DragStartBehavior.down,
            children: [
              const Center(child: TableManager()),
              const Center(child: OrdersWidget()),
              const Center(child: ReservationsWidget()),
              const Center(child: Menu()),
              Center(child: FloorPlan(UniqueKey())),
            ],
          ),
        ),
      ),
    );
  }
}
