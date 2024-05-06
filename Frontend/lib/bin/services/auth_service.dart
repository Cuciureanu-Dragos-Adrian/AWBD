import '../constants.dart' as constants;
import 'package:http/http.dart' as http;
import 'dart:convert';

class AuthService {
  // Variable to store login state
  static bool _isLoggedIn = false;

  // Getter for isLoggedIn
  static bool get isLoggedIn => _isLoggedIn;

  // Login endpoint
  static Future<bool> login(String username, String password) async {
    final url = Uri.parse(constants.backendUrl + '/auth/login');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );

    if (response.statusCode == 200) {
      // Login successful, set flag
      _isLoggedIn = true;
      return true;
    } else {
      // Login failed, handle error
      throw Exception(response.body);
    }
  }

  // Signup endpoint
  static Future<void> signup(String username, String password) async {
    final url = Uri.parse(constants.backendUrl + '/auth/signup');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );

    if (response.statusCode != 201) {
      // Signup failed, handle error
      throw Exception(response.body);
    }
  }

  // Method to clear login state (optional)
  static void logout() {
    // Assuming backend handles session invalidation
    _isLoggedIn = false;
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
          'User was logged out!'); // Re-throw to indicate need for re-authentication
    }
    return response;
  }
}
