//variables that need to be loaded from disk and be visible in the whole project

import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/main.dart';

import '../models/globals_model.dart';

/// Loads a list of orders, saves it to TableList and returns it
///
Future<GlobalsModel> loadGlobalObject() async {
  return (await data.readGlobalObject());
}

///Saves orders to disk
///
void saveGlobalObject() {
  GlobalsModel toSave = Globals.getGlobals();
  data.writeGlobalObject(toSave); // use global data service to store tables
}

int getZoomIndex() {
  return zoomFactors.indexOf(Globals.getGlobals().tableImagesScale);
}

Globals? object;

class Globals {
  late GlobalsModel _globals;
  Globals._();

  static Future<void> loadGlobals() async {
    if (object == null) {
      object = Globals._();
      object!._globals = await loadGlobalObject();
    }
  }

  static GlobalsModel getGlobals() {
    object ??= Globals._();

    return object!._globals;
  }
}
