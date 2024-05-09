import '../constants.dart' as constants;
import 'package:http/http.dart' as http;
import 'dart:convert';

class AuthService {
  // Variable to store login state
  static bool _isLoggedIn = false;
  static String loggedUser = "";
  static String token = "";
  static String role = "";

  static bool get canSeeStaffFunctions {
    return role == "admin" || role == "staff";
  }

  static bool get canSeeAdminFunctions {
    return role == "admin";
  }

  static String get authorizationHeader {
    return "Bearer $token";
  }

  static void Function(bool)? loggedInSetterCallback;

  // Getter for isLoggedIn
  static set isLoggedIn(bool loggedIn) {
    _isLoggedIn = loggedIn;

    if (loggedInSetterCallback != null) {
      loggedInSetterCallback!(loggedIn);
    }
  }

  // Login endpoint
  static Future<bool> login(String email, String password) async {
    final url = Uri.parse(constants.backendUrl + '/auth/login');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': email, 'password': password}),
    );

    if (response.statusCode == 200) {
      var responseJson = jsonDecode(response.body) as Map<String, dynamic>;
      // Login successful, set flag
      isLoggedIn = true;
      loggedUser = responseJson["username"];
      token = responseJson["token"];
      role = responseJson["role"].toString().toLowerCase();
      return true;
    } else {
      // Login failed, handle error
      throw Exception(response.body);
    }
  }

  // Signup endpoint
  static Future<void> signup(
      String email, String username, String password) async {
    final url = Uri.parse(constants.backendUrl + '/auth/signup');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(
          {'email': email, 'username': username, 'password': password}),
    );

    if (response.statusCode == 200) {
      // Signup failed
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
