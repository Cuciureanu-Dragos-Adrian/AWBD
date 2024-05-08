import '../constants.dart' as constants;
import 'package:http/http.dart' as http;
import 'dart:convert';

class AuthService {
  // Variable to store login state
  static bool _isLoggedIn = false;
  static String loggedUser = "";

  static void Function(bool)? loggedInSetterCallback;

  // Getter for isLoggedIn
  static set isLoggedIn(bool loggedIn) {
    _isLoggedIn = loggedIn;

    if (loggedInSetterCallback != null) {
      loggedInSetterCallback!(loggedIn);
    }
  }

  // Login endpoint
  static Future<bool> login(String username, String password) async {
    final url = Uri.parse(constants.backendUrl + '/auth/login');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': username, 'password': password}),
    );

    if (response.statusCode == 200) {
      // Login successful, set flag
      isLoggedIn = true;
      loggedUser = username;
      return true;
    } else {
      // Login failed, handle error
      throw Exception(response.body);
    }
  }

  // Login endpoint
//   static Future<bool> login(String username, String password) async {
//   final url = Uri.parse(constants.backendUrl + '/perform_login'); // Use the URL for login processing
//   final response = await http.post(
//     url,
//     headers: {'Content-Type': 'application/json'},
//     body: jsonEncode({'username': username, 'password': password}),
//   );

//   if (response.statusCode == 200) {
//     // Login successful, set flag
//     isLoggedIn = true;
//     loggedUser = username;
//     return true;
//   } else {
//     // Login failed, handle error
//     throw Exception(response.body);
//   }
// }


  // Signup endpoint
  static Future<void> signup(String username, String password) async {
    final url = Uri.parse(constants.backendUrl + '/auth/signup');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );

    if (response.statusCode != 200) {
      // Signup failed, handle error
      throw Exception(response.body);
    }
  }

  // Method to clear login state
  static void logout() {
    // Assuming backend handles session invalidation
    isLoggedIn = false;
    loggedUser = "";
  }

  static Future<http.Response> authenticatedRequest(
      Future<http.Response> futureRequest) async {
    if (!_isLoggedIn) {
      throw Exception("User is not logged in!");
    }

    final response = await futureRequest;
    if (response.statusCode == 401) {
      logout();
      throw Exception(
          'User was logged out!'); // Throw to indicate need for re-authentication
    }
    return response;
  }
}
