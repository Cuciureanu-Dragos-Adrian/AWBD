import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/services/reservation_service.dart';
import 'package:restaurant_management_app/bin/services/table_service.dart';
import 'package:restaurant_management_app/bin/models/reservation_model.dart';
import 'package:restaurant_management_app/bin/utilities/reservation_utils.dart';
import 'package:restaurant_management_app/bin/widgets/custom_button.dart';
import 'package:restaurant_management_app/bin/widgets/dialog.dart';
import 'package:restaurant_management_app/bin/widgets/time_picker.dart';

import '../models/table_model.dart';

const double expandedMaxHeight = 400;
List<ReservationModel> reservations = [];

class ReservationsWidget extends StatefulWidget {
  const ReservationsWidget({Key? key}) : super(key: key);

  @override
  State<ReservationsWidget> createState() => _ReservationsWidgetState();
}

class _ReservationsWidgetState extends State<ReservationsWidget> {
  List<TableModel> _tables = [];

  final TextEditingController _reservationNameController =
      TextEditingController();

  String _chosenTable = '';
  String _dialogErrorMessage = "";
  String _reservedBy = "";
  int _currentPage = 1;
  bool _hasMoreData = true;
  bool _asc = true;

  @override
  void initState() {
    super.initState();
    loadTablesAsync();
    loadReservationsPageAsync(_currentPage);
    //loadReservationsAscAsync();
  }

  void loadTablesAsync() async {
    try {
      _tables = await TableService.getTables();
    } on Exception {
      showMessageBox(context, 'Failed to fetch tables!');
      return;
    }

    if (_tables.isNotEmpty) {
      _chosenTable = _tables[0].id;
    }
  }

  void loadReservationsAscAsync() async {
    try {
      var fetch = await ReservationService.getReservationListAsc();

      setState(() {
        reservations = fetch;
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch tables!');
      return;
    }
  }

  void loadReservationsDescAsync() async {
    try {
      var fetch = await ReservationService.getReservationListDesc();

      setState(() {
        reservations = fetch;
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch tables!');
      return;
    }
  }

  void loadReservationsPageAsync(int page) async {
    try {
      List<ReservationModel> fetch;

      if (_asc) {
        fetch = await ReservationService.getReservationListPageAsc(page: page);
      } else {
        fetch = await ReservationService.getReservationListPageDesc(page: page);
      }

      setState(() {
        reservations.addAll(fetch);
        _currentPage++;
        _hasMoreData = fetch.length == 10;
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch tables!');
      return;
    }
  }

  @override
  void dispose() {
    _reservationNameController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    DateFormat dateFormat = DateFormat("MM/dd HH:mm");

    return LayoutBuilder(builder: (context, constraints) {
      return Column(children: [
        SizedBox(
            width: constraints.maxWidth,
            height: constraints.maxHeight * 9 / 10,
            child: NotificationListener<ScrollNotification>(
              onNotification: (notification) {
                if (notification is ScrollEndNotification) {
                  if (_hasMoreData) {
                    loadReservationsPageAsync(_currentPage);
                  }
                }
                return true;
              },
              child: ListView.builder(
                controller: ScrollController(),
                itemBuilder: (BuildContext context, int index) {
                  return ReservationSection(
                      title: '    ' +
                          reservations[index].tableId +
                          ' : ' +
                          dateFormat
                              .format(reservations[index].dateTime)
                              .toString() +
                          ', ' +
                          reservations[index].name,
                      reservationModel: reservations[index]);
                },
                itemCount: reservations.length,
              ),
            )),
        SizedBox(
            width: constraints.maxWidth,
            height: constraints.maxHeight * 1 / 10,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Expanded(
                  child: Center(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                    Container(
                        margin: const EdgeInsets.only(bottom: 10, top: 10, right: 10),
                        child: CustomButton(
                          color: mainColor,
                          size: 40,
                          icon: const Icon(Icons.arrow_upward),
                          onPressed: () {
                            _asc = true;
                            _currentPage = 1;
                            reservations.clear();
                            loadReservationsPageAsync(_currentPage);
                            //loadReservationsAscAsync();
                          },
                        )),
                    CustomButton(
                      color: mainColor,
                      size: 40,
                      icon: const Icon(Icons.add),
                      onPressed: () async {
                        await showDialog(
                            context: context,
                            barrierDismissible: false,
                            builder: (BuildContext context) {
                              return StatefulBuilder(
                                  builder: (context, setState) {
                                return AlertDialog(
                                  title: const Text(
                                    'Add Reservation',
                                    style: TextStyle(color: mainColor),
                                  ),
                                  content: SizedBox(
                                      height: 300,
                                      width: 300,
                                      child: Column(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceEvenly,
                                        children: [
                                          Column(
                                            children: [
                                              const Text(
                                                "Set table",
                                                style: TextStyle(
                                                    color: Colors.black87),
                                              ),
                                              Row(
                                                children: [
                                                  Container(
                                                    margin: const EdgeInsets
                                                        .symmetric(
                                                        horizontal: 10),
                                                    child:
                                                        DropdownButton<String>(
                                                      value: _chosenTable,
                                                      icon: const Icon(
                                                          Icons.arrow_downward),
                                                      elevation: 16,
                                                      style: const TextStyle(
                                                          color: Colors.black),
                                                      underline: Container(
                                                        height: 2,
                                                        color: mainColor,
                                                      ),
                                                      onChanged:
                                                          (String? newValue) {
                                                        setState(() {
                                                          _chosenTable =
                                                              newValue!;
                                                        });
                                                      },
                                                      items: _tables.map<
                                                              DropdownMenuItem<
                                                                  String>>(
                                                          (TableModel value) {
                                                        return DropdownMenuItem<
                                                            String>(
                                                          value: value.id,
                                                          child: Text(value.id),
                                                        );
                                                      }).toList(),
                                                    ),
                                                  )
                                                ],
                                              ),
                                              const Text(
                                                "Set Reservation Time",
                                                style: TextStyle(
                                                    color: Colors.black87),
                                              ),
                                              Row(
                                                children: [
                                                  Container(
                                                      height: 30,
                                                      width: 300,
                                                      margin: const EdgeInsets
                                                          .symmetric(
                                                          vertical: 10),
                                                      child: const TimePicker())
                                                ],
                                              ),
                                              TextField(
                                                decoration:
                                                    const InputDecoration(
                                                        hintText: "Enter Name"),
                                                controller:
                                                    _reservationNameController,
                                              ),
                                              Text(
                                                _dialogErrorMessage,
                                                style: const TextStyle(
                                                    color: Colors.redAccent),
                                              )
                                            ],
                                          )
                                        ],
                                      )),
                                  actions: [
                                    TextButton(
                                      child: const Text("Add"),
                                      onPressed: () async {
                                        setState(() {
                                          _dialogErrorMessage = "";
                                        });

                                        setState(() {
                                          _reservedBy =
                                              _reservationNameController.text;
                                        });

                                        if (await createReservation()) {
                                          _reservationNameController.text = "";
                                          _reservedBy = "";
                                          Navigator.of(context).pop();
                                        }
                                      },
                                      style: TextButton.styleFrom(
                                          foregroundColor: mainColor),
                                    ),
                                    TextButton(
                                      child: const Text('Cancel'),
                                      onPressed: () {
                                        Navigator.of(context).pop();
                                      },
                                      style: TextButton.styleFrom(
                                          foregroundColor: mainColor),
                                    ),
                                  ],
                                );
                              });
                            });
                      },
                    ),
                    Container(
                        margin: const EdgeInsets.only(left: 10, bottom: 10, top: 10),
                        child: CustomButton(
                          color: mainColor,
                          size: 40,
                          icon: const Icon(Icons.arrow_downward),
                          onPressed: () {
                            _asc = false;
                            _currentPage = 1;
                            reservations.clear();
                            loadReservationsPageAsync(_currentPage);
                            //loadReservationsDescAsync();
                          },
                        )),
                  ])),
                ),
              ],
            ))
      ]);
    });
  }

  Future<bool> createReservation() async {
    int numberOfPeople = 0;
    if (_chosenTable.startsWith('A')) {
      numberOfPeople = 2;
    } else if (_chosenTable.startsWith('B')) {
      numberOfPeople = 3;
    } else if (_chosenTable.startsWith('C')) {
      numberOfPeople = 4;
    } else if (_chosenTable.startsWith('D')) {
      numberOfPeople = 6;
    } else {
      numberOfPeople = 8;
    }

    //check if the name is valid
    if (_reservedBy == "") {
      setState(() {
        _dialogErrorMessage = "The name field cannot be empty";
      });
      return false;
    }

    ReservationModel newReservation = ReservationModel(
        reservationId: "-",
        numberOfPeople: numberOfPeople,
        name: _reservedBy,
        dateTime: getSelectedDate(),
        tableId: _chosenTable);

    try {
      String generatedId =
          await ReservationService.addReservation(newReservation);
      newReservation.reservationId = generatedId;
      var fetch = await ReservationService.getReservationListAsc();

      setState(() {
        reservations = fetch;
      });

      return true;
    } on Exception catch (e) {
      setState(() {
        _dialogErrorMessage = e.toString();
      });
      return false;
    }
  }
}

class ReservationSection extends StatefulWidget {
  final String title;
  final ReservationModel reservationModel;

  const ReservationSection(
      {Key? key, required this.title, required this.reservationModel})
      : super(key: key);

  @override
  _ReservationSectionState createState() => _ReservationSectionState();
}

class _ReservationSectionState extends State<ReservationSection> {
  bool expandFlag = false;

  @override
  Widget build(BuildContext context) {
    return Container(
        margin: const EdgeInsets.symmetric(vertical: 1.0),
        child: Column(
          children: <Widget>[
            Container(
              color: accent1Color,
              padding: const EdgeInsets.symmetric(vertical: 10.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
                  Text(
                    widget.title,
                    style: const TextStyle(
                        fontSize: 22,
                        fontWeight: FontWeight.bold,
                        color: mainColor),
                  )
                ],
              ),
            ),
          ],
        ));
  }
}
