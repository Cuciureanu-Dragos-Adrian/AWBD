import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/widgets/auth/login_widget.dart';
import 'package:restaurant_management_app/bin/widgets/auth/signup_widget.dart';

class Auth extends StatelessWidget {
  const Auth({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        backgroundColor: accent2Color,
        appBar: AppBar(
          bottom: const TabBar(
            tabs: <Widget>[
              Tab(icon: Icon(Icons.login), text: "Login"),
              Tab(icon: Icon(Icons.description), text: "Sign Up"),
            ],
            indicatorColor: accent2Color,
            indicatorWeight: 3,
            labelColor: accent2Color,
            unselectedLabelColor: accent1Color,
          ),
          centerTitle: true,
          title: const Text(
            'Authentication',
            style: TextStyle(
              fontSize: 24,
              color: accent2Color,
              fontWeight: FontWeight.w600,
            ),
          ),
          backgroundColor: mainColor,
        ),
        body: const TabBarView(
          physics: BouncingScrollPhysics(),
          dragStartBehavior: DragStartBehavior.down,
          children: [
            Center(child: LoginWidget()),
            Center(child: SignupWidget()),
          ],
        ),
      ),
    );
  }
}
