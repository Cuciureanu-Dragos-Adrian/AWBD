// table images paths. WARNING they do not include extensions = .png
import 'package:flutter/material.dart';

const backendUrl = "http://localhost:8080";

const twoTablePath = "assets/2Table";
const threeTablePath = "assets/3Table";
const fourTablePath = "assets/4Table";
const sixTablePath = "assets/6Table";
const eightTablePath = "assets/8Table";
const feedbackPath = "Feedback";
// --------------------------------

// max number of floors for floorplan
const maxFloors = 10;

// possible zooms for tables
List<double> zoomFactors = [0.5, 0.65, 0.75, 1.0, 1.25, 1.5];

const smallTblWidth = 112;
const largeTblWidth = 168;
const tblHeight = 112;

enum AssetPaths {
  small2,
  small2reserved,
  small2reservedOrdered,
  small2ordered,
  small2feedback,

  small3,
  small3reserved,
  small3reservedOrdered,
  small3ordered,
  small3feedback,

  small4,
  small4reserved,
  small4reservedOrdered,
  small4ordered,
  small4feedback,

  large6,
  large6reserved,
  large6reservedOrdered,
  large6ordered,
  large6feedback,

  large8,
  large8reserved,
  large8reservedOrdered,
  large8ordered,
  large8feedback
}

extension AssetPathsExtension on AssetPaths {
  String get value {
    switch (this) {
      case AssetPaths.small2:
        return 'assets/tables/small/small_2';
      case AssetPaths.small2reserved:
        return 'assets/tables/small/small_2_highlighted';
      case AssetPaths.small2reservedOrdered:
        return 'assets/tables/small/small_2_selected';
      case AssetPaths.small2ordered:
        return 'assets/tables/small/small_2_selected_highlighted';
      case AssetPaths.small2feedback:
        return 'assets/tables/small/small_2_feedback';

      case AssetPaths.small3:
        return 'assets/tables/small/small_3';
      case AssetPaths.small3reserved:
        return 'assets/tables/small/small_3_highlighted';
      case AssetPaths.small3reservedOrdered:
        return 'assets/tables/small/small_3_selected';
      case AssetPaths.small3ordered:
        return 'assets/tables/small/small_3_selected_highlighted';
      case AssetPaths.small3feedback:
        return 'assets/tables/small/small_3_feedback';

      case AssetPaths.small4:
        return 'assets/tables/small/small_4';
      case AssetPaths.small4reserved:
        return 'assets/tables/small/small_4_highlighted';
      case AssetPaths.small4reservedOrdered:
        return 'assets/tables/small/small_4_selected';
      case AssetPaths.small4ordered:
        return 'assets/tables/small/small_4_selected_highlighted';
      case AssetPaths.small4feedback:
        return 'assets/tables/small/small_4_feedback';

      case AssetPaths.large6:
        return 'assets/tables/large/large_6';
      case AssetPaths.large6reserved:
        return 'assets/tables/large/large_6_highlighted';
      case AssetPaths.large6reservedOrdered:
        return 'assets/tables/large/large_6_selected';
      case AssetPaths.large6ordered:
        return 'assets/tables/large/large_6_selected_highlighted';
      case AssetPaths.large6feedback:
        return 'assets/tables/large/large_6_feedback';

      case AssetPaths.large8:
        return 'assets/tables/large/large_8';
      case AssetPaths.large8reserved:
        return 'assets/tables/large/large_8_highlighted';
      case AssetPaths.large8reservedOrdered:
        return 'assets/tables/large/large_8_selected';
      case AssetPaths.large8ordered:
        return 'assets/tables/large/large_8_selected_highlighted';
      case AssetPaths.large8feedback:
        return 'assets/tables/large/large_8_feedback';
    }
  }
}

const double floorMargin = 10;
//const smallModifier = 0.5;
//const largeModifier = 1.5;

//color scheme
const Color mainColor = Color.fromRGBO(222, 160, 87, 1);

const Map<int, Color> mainColorMap =
{
50:Color.fromRGBO(222, 160, 87, .1),
100:Color.fromRGBO(222, 160, 87, .2),
200:Color.fromRGBO(222, 160, 87, .3),
300:Color.fromRGBO(222, 160, 87, .4),
400:Color.fromRGBO(222, 160, 87, .5),
500:Color.fromRGBO(222, 160, 87, .6),
600:Color.fromRGBO(222, 160, 87, .7),
700:Color.fromRGBO(222, 160, 87, .8),
800: Color.fromRGBO(222, 160, 87, .9),
900:Color.fromRGBO(222, 160, 87, 1),
};

const int mainColorHex = 0xdea057;

MaterialColor mainMaterialColor = MaterialColor(mainColor.value, mainColorMap);

const Color accent1Color = Color.fromRGBO(224, 216, 176, 1);
const Color accent2Color = Color.fromRGBO(252, 255, 231, 1);
// --------------------------------

//menu sections
List<String> sections = [
  "Appetizers",
  "Main courses",
  "Sides",
  "Soft drinks",
  "Spirits"
];

Map sectionIcons = {
  'Appetizers': Icons.apple,
  'Main courses': Icons.food_bank,
  'Sides': Icons.food_bank_outlined,
  'Soft drinks': Icons.local_drink,
  'Spirits': Icons.wine_bar
};

//reservation Section

const int reservationDurationHours = 3;

//---------------------------------