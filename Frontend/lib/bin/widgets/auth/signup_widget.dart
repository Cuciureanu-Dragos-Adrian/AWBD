import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/services/auth_service.dart';
import 'package:restaurant_management_app/bin/widgets/common/dialog.dart';
import 'package:restaurant_management_app/main.dart';

class SignupWidget extends StatefulWidget {
  const SignupWidget({Key? key}) : super(key: key);

  @override
  State<SignupWidget> createState() => _SignupWidgetState();
}

class _SignupWidgetState extends State<SignupWidget> {
  final _emailController = TextEditingController();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(20.0), // Add padding inside the box
      decoration: BoxDecoration(
        color: Colors.white, // Set background color
        borderRadius: BorderRadius.circular(10.0), // Set rounded corners
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.3), // Add a subtle shadow
            blurRadius: 5.0, // Adjust blur radius of the shadow
            spreadRadius: 1.0, // Adjust spread radius of the shadow
          ),
        ],
      ),
      child: Column(
        // Set mainAxisSize to minimize
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.center, // Center the column
        children: [
          const Text(
            'Sign up',
            style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold, color: mainColor),
          ),
          const SizedBox(height: 24), // Increased spacing between fields
          SizedBox(
            width: 300.0, // Set desired width for email field
            child: TextField(
              controller: _emailController,
              decoration: const InputDecoration(
                labelText: 'Email',
                labelStyle: TextStyle(color: mainColor),
                enabledBorder: UnderlineInputBorder(
                  borderSide: BorderSide(color: Colors.grey),
                ),
                focusedBorder: UnderlineInputBorder(
                  borderSide: BorderSide(color: mainColor),
                ),
              ),
              style:
                  const TextStyle(fontSize: 16.0), // Adjust font size if needed
              keyboardType:
                  TextInputType.text, // Set keyboard type for username
              textInputAction:
                  TextInputAction.next, // Trigger "next" on keyboard
              maxLines: 1, // Set the number of text lines (1 for single line)
            ),
          ),
          const SizedBox(height: 24), // Increased spacing between fields
          SizedBox(
            width: 300.0, // Set desired width for username field
            child: TextField(
              controller: _usernameController,
              decoration: const InputDecoration(
                labelText: 'Username',
                labelStyle: TextStyle(color: mainColor),
                enabledBorder: UnderlineInputBorder(
                  borderSide: BorderSide(color: Colors.grey),
                ),
                focusedBorder: UnderlineInputBorder(
                  borderSide: BorderSide(color: mainColor),
                ),
              ),
              style:
                  const TextStyle(fontSize: 16.0), // Adjust font size if needed
              keyboardType:
                  TextInputType.text, // Set keyboard type for username
              textInputAction:
                  TextInputAction.next, // Trigger "next" on keyboard
              maxLines: 1, // Set the number of text lines (1 for single line)
            ),
          ),
          const SizedBox(height: 24), // Increased spacing between fields
          SizedBox(
            width: 300.0, // Set desired width for password field
            child: TextField(
              controller: _passwordController,
              obscureText: true, // Hide password input
              decoration: const InputDecoration(
                labelText: 'Password',
                labelStyle: TextStyle(color: mainColor),
                enabledBorder: UnderlineInputBorder(
                  borderSide: BorderSide(color: Colors.grey),
                ),
                focusedBorder: UnderlineInputBorder(
                  borderSide: BorderSide(color: mainColor),
                ),
              ),
              style:
                  const TextStyle(fontSize: 16.0), // Adjust font size if needed
              keyboardType: TextInputType
                  .visiblePassword, // Set keyboard type for password
              textInputAction:
                  TextInputAction.done, // Trigger "done" on keyboard
              maxLines: 1, // Set the number of text lines (1 for single line)
            ),
          ),
          const SizedBox(height: 54), // Add spacing before button
          ElevatedButton(
            onPressed: () async {
              final email = _emailController.text;
              final username = _usernameController.text;
              final password = _passwordController.text;
              await signup(email, username, password); // Call login callback
            },
            style: ElevatedButton.styleFrom(
                backgroundColor: mainColor),
            child: const Text('Sign up', style: TextStyle(color: Colors.white),),
          ),
        ],
      ),
    );
  }

  Future<void> signup(String email, String username, String password) async 
  {
      try {
        await AuthService.signup(email, username, password);

        showMessageBox(NavigationService.navigatorKey.currentContext!,
            'User successfully created!', title: "User created!");
      } on Exception catch (e) {
        showMessageBox(NavigationService.navigatorKey.currentContext!,
            'Signup failed: $e');
        return;
      }
  }
}
