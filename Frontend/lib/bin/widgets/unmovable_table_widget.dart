// ignore: file_names
import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/constants.dart' as constants;
import 'package:restaurant_management_app/bin/services/order_service.dart'
    as orders;
import 'package:restaurant_management_app/bin/services/reservation_service.dart'
    as reservations;
import 'package:restaurant_management_app/bin/models/order_model.dart';
import 'package:restaurant_management_app/bin/models/reservation_model.dart';
import 'package:restaurant_management_app/bin/utilities/table_utils.dart';

import '../constants.dart';
import '../services/globals.dart';
import 'orders.dart';

/// Movable table object
///
class UnmovableTableWidget extends StatefulWidget {
  final String imagePath; //the corresponding table's image path
  final int _imageWidth; // width of the displayed image
  final int imageHeight; // height of the displayed image
  final int tableSize;
  final String id;
  final int floor;
  final Offset
      position; // position relative to the top left corner of the container
  final void Function() callback;
  UnmovableTableWidget({
    Key? key,
    required this.tableSize,
    required this.position,
    required this.id,
    required this.floor,
    required this.callback,
  })  : imagePath = getBaseImagePath(tableSize),
        _imageWidth = getImageSize(tableSize)[0],
        imageHeight = getImageSize(tableSize)[1],
        super(key: key);

  @override
  State<UnmovableTableWidget> createState() => _UnmovableTableWidgetState();
}

class _UnmovableTableWidgetState extends State<UnmovableTableWidget> {
  static String selectedId = "";
  late Offset _position;
  late double _scale;
  late bool _hasOrder = false;
  late bool _hasReservation = false;
  late OrderModel? _order;
  late ReservationModel? _reservation;

  @override
  void initState() {
    super.initState();
    _position = widget.position;

    _order = null;
    _reservation = null;

    fetchReservation();
    fetchOrder();
  }

  void fetchReservation() async {
    try {
      var upcomingReservation = await getUpcomingReservation(widget.id);
      //has a reservation in the next 3 hours
      setState(() {
        _reservation = upcomingReservation;
        _hasReservation = upcomingReservation != null;
      });
    } on Exception {
      return;
    }
  }

  void fetchOrder() async {
    try{
      var order = await getAssignedOrder(widget.id);
      setState(() {
        _order = order;
        _hasOrder = order != null;
      });
    } on Exception {
      return;
    }
  }

  @override
  Widget build(BuildContext context) {
    _scale = Globals.getGlobals().tableImagesScale;
    return Positioned(
      left: _position.dx,
      top: _position.dy,
      child: SizedBox(
        width: widget._imageWidth.toDouble() * _scale,
        height: widget.imageHeight.toDouble() * _scale,
        child: Stack(
          alignment: Alignment.center,
          children: <Widget>[
            Image(
              //set the image accordingly
              image: AssetImage(getImagePath()),
              width: widget._imageWidth.toDouble() * _scale,
              height: widget.imageHeight.toDouble() * _scale,
            ),
            Text(
              widget.id,
              style: TextStyle(
                  color: Colors.white,
                  fontSize: 18 * _scale,
                  fontWeight: FontWeight.bold),
            ),
            GestureDetector(onTap: () {
              setState(() {
                if (selectedId == "") {
                  selectedId = widget.id;

                  showDialog(
                      context: context,
                      barrierDismissible: false,
                      builder: (BuildContext context) {
                        return StatefulBuilder(builder: (context, setState) {
                          return AlertDialog(
                              title: Text('Table ${widget.id} info',
                                  style: const TextStyle(color: mainColor)),
                              content: SizedBox(
                                height: 300,
                                child: Column(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceAround,
                                    children: [
                                      Text(getReservationText()),
                                      Column(
                                        children: [
                                          Text(getOrderText()),
                                          SizedBox(
                                            height: 200,
                                            width: 200,
                                            child: ListView.builder(
                                              controller: ScrollController(),
                                              itemBuilder: (BuildContext context,
                                                  int index) {
                                                return DialogListItem(
                                                  name:
                                                      _order!.products[index].name,
                                                  category: _order!
                                                      .products[index].category,
                                                  quantity:
                                                      _order!.quantities[index],
                                                );
                                              },
                                              itemCount: _order != null
                                                  ? _order!.products.length
                                                  : 0,
                                            ),
                                          ),
                                        ],
                                      ),
                                    ]),
                              ),
                              actions: [
                                TextButton(
                                    child: const Text('Finish order'),
                                    onPressed: () {
                                      setState(() {
                                        removeOrder();
                                      });
                                    }),
                                TextButton(
                                    child: const Text('Clear reservation'),
                                    onPressed: () {
                                      setState(() {
                                        removeReservation();
                                      });
                                    }),
                                TextButton(
                                    child: const Text('Close'),
                                    onPressed: () {
                                      Navigator.of(context).pop();
                                      selectedId = "";
                                      widget.callback();
                                    })
                              ]);
                        });
                      });
                } else if (selectedId == widget.id) {
                  selectedId = "";
                }
                widget.callback();
              });
            }),
          ],
        ),
      ),
    );
  }

  void removeOrder() async {
    if (_order == null) {
      await showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text("Operation failed"),
            content: const Text("The selected table does not have an order!"),
            actions: [
              TextButton(
                child:
                    const Text("OK", style: TextStyle(color: Colors.redAccent)),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    } else {
      orders.OrderService.removeOrdersByTableId(_order!.tableId);
      _hasOrder = false;
      _order = null;
    }
  }

  void removeReservation() async {
    if (_reservation == null) {
      await showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text("Operation failed"),
            content: const Text(
                "The selected table does not have a current reservation!"),
            actions: [
              TextButton(
                child:
                    const Text("OK", style: TextStyle(color: Colors.redAccent)),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    } else {
      reservations.ReservationService.removeReservationById(
          _reservation!.reservationId);

      _hasReservation = false;
      _reservation = null;
    }
  }

  String getImagePath() {
    if (widget.id == selectedId) {
      return widget.imagePath + "_feedback.png";
    } else if (_hasOrder && _hasReservation) {
      return widget.imagePath + "_reserved_ordered.png";
    } else if (_hasOrder) {
      return widget.imagePath + "_ordered.png";
    } else if (_hasReservation) {
      return widget.imagePath + "_reserved.png";
    } else {
      return widget.imagePath + ".png";
    }
  }

  String getReservationText() {
    if (_reservation == null) {
      return 'The table is not reserved in the next $reservationDurationHours hours.';
    } else {
      return 'The table is reserved by ${_reservation!.name} from ${_reservation!.dateTime.hour}:${_reservation!.dateTime.minute} until ${_reservation!.dateTime.hour + 3}:${_reservation!.dateTime.minute}';
    }
  }

  String getOrderText() {
    if (_order == null) {
      return "There is no current order";
    } else {
      return "Order details:";
    }
  }
}

/// Receives a table size and returns the path to the corresponding image
///
/// @param tableSize: size of the table
/// @note image paths are without extension
String getBaseImagePath(int tableSize) {
  switch (tableSize) {
    case 2:
      return constants.AssetPaths.small2.value;
    case 3:
      return constants.AssetPaths.small3.value;
    case 4:
      return constants.AssetPaths.small4.value;
    case 6:
      return constants.AssetPaths.large6.value;
    case 8:
      return constants.AssetPaths.large8.value;
  }

  throw Exception("Invalid table size!");
}

/// Receives a table size and returns a list containing the required dimensions
///
///@param tableSize: the size of the table
///@returns List<int> containing [0] = width and [1] = height
List<int> getImageSize(int tableSize) {
  const List<int> smallTable = [constants.smallTblWidth, constants.tblHeight];
  const List<int> largeTable = [constants.largeTblWidth, constants.tblHeight];

  switch (tableSize) {
    case 2:
      return smallTable;
    case 3:
      return smallTable;
    case 4:
      return smallTable;
    case 6:
      return largeTable;
    case 8:
      return largeTable;
  }

  throw Exception("Invalid table size!");
}
