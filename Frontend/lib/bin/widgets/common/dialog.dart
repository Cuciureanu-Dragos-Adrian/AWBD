import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/constants.dart';

void showMessageBox(BuildContext context, String message, {String title = "Error"}) {
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return AlertDialog(
        title: Text(title),
        content: Text(message),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
            style: TextButton.styleFrom(
                foregroundColor: mainColor),
          ),
        ],
      );
    },
  );
}
