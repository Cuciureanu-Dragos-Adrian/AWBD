import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/services/auth_service.dart';
import 'package:restaurant_management_app/bin/widgets/main/floorplan_widget.dart';
import 'package:restaurant_management_app/bin/widgets/main/menu_widget.dart';
import 'package:restaurant_management_app/bin/widgets/main/orders_widget.dart';
import 'package:restaurant_management_app/bin/widgets/main/reservations_widget.dart';
import 'package:restaurant_management_app/bin/widgets/main/table_manager_widget.dart';

class MainApp extends StatelessWidget {
  const MainApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: AuthService.canSeeStaffFunctions ? 5 : 2,
      child: Scaffold(
        backgroundColor: accent2Color,
        appBar: AppBar(
          bottom: TabBar(
            tabs: AuthService.canSeeStaffFunctions
                ? const <Widget>[
                    Tab(
                        icon: Icon(Icons.bookmark_added_outlined),
                        text: "Manage tables"),
                    Tab(
                        icon: Icon(Icons.view_list_outlined),
                        text: "View order list"),
                    Tab(
                        icon: Icon(Icons.view_list_outlined),
                        text: "Manage reservations"),
                    Tab(
                        icon: Icon(Icons.menu_book_outlined),
                        text: "Edit menu"),
                    Tab(
                        icon: Icon(Icons.create_outlined),
                        text: "Edit floor plan"),
                  ]
                : const <Widget>[
                    Tab(
                        icon: Icon(Icons.bookmark_added_outlined),
                        text: "See tables"),
                    Tab(
                        icon: Icon(Icons.menu_book_outlined),
                        text: "View menu"),
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
          actions: [
            // Add text on top right
            Text(
              "Welcome, " +
                  AuthService.loggedUser +
                  "!", // Replace "John" with dynamic username
              style: const TextStyle(color: Colors.white),
            ),
            const SizedBox(width: 10),
            // Add logout button
            IconButton(
              icon: const Icon(Icons.logout),
              color: Colors.white,
              onPressed: () {
                AuthService.logout();
              },
            ),
          ],
          backgroundColor: mainColor,
        ),
        body: TabBarView(
          physics: const BouncingScrollPhysics(),
          dragStartBehavior: DragStartBehavior.down,
          children: AuthService.canSeeStaffFunctions
              ? [
                  const Center(child: TableManager()),
                  const Center(child: OrdersWidget()),
                  const Center(child: ReservationsWidget()),
                  const Center(child: Menu()),
                  Center(child: FloorPlan(UniqueKey())),
                ]
              : [
                  const Center(child: TableManager()),
                  const Center(child: Menu()),
                ],
        ),
      ),
    );
  }
}
