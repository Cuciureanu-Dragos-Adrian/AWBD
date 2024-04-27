import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/services/reservation_service.dart';

class TimePicker extends StatefulWidget {
  const TimePicker({Key? key}) : super(key: key);

  @override
  _TimePickerState createState() => _TimePickerState();
}

class _TimePickerState extends State<TimePicker> {
  late DateTime _selectedDate;
  @override
  Widget build(BuildContext context){
    _selectedDate = getSelectedDate();
    return DateButton(
        text: getDateTime(_selectedDate),
        onClicked: () => pickDateTime(context),
    );
  }

  Future<DateTime> pickDate(BuildContext context) async {
    final newDate = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(DateTime.now().year),
      lastDate: DateTime(DateTime.now().year + 1),
    );

    if (newDate != null) {
      return newDate;
    } else {
      return _selectedDate;
    }
  }

  Future<TimeOfDay> pickTime(BuildContext context) async {
    final newTime = await showTimePicker(
        context: context,
        initialTime: TimeOfDay(
            hour: _selectedDate.hour,
            minute: _selectedDate.minute));

    if (newTime != null) {
      return newTime;
    } else {
      return TimeOfDay(
          hour: _selectedDate.hour, minute: _selectedDate.minute);
    }
  }

  Future pickDateTime(BuildContext context) async {
    final date = await pickDate(context);

    final time = await pickTime(context);

    setState(() {
      DateTime newDate = DateTime(
        date.year,
        date.month,
        date.day,
        time.hour,
        time.minute,
      );
      setSelectedDate(newDate); //set the date inside the service
      _selectedDate = newDate;
    });
  }
}

class DateButton extends StatelessWidget {
  final String text;
  final VoidCallback onClicked;

  const DateButton({
    Key? key,
    required this.text,
    required this.onClicked,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) => ElevatedButton(
        style: ElevatedButton.styleFrom(
          minimumSize: const Size.fromHeight(40),
          backgroundColor: mainColor,
        ),
        child: FittedBox(
          child: Text(
            text,
            style: const TextStyle(
              fontSize: 20,
              color: accent2Color,
            ),
            textAlign: TextAlign.left,
          ),
        ),
        onPressed: onClicked,
      );
}

String getDateTime(DateTime dt) {
  DateFormat dateFormat = DateFormat("MM/dd HH:mm");
  return dateFormat.format(dt);
}