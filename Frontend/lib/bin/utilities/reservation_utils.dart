DateTime? _selectedDate;

//TODO - refactor and remove service

void setSelectedDate(DateTime toSet){
  _selectedDate = toSet;
}

DateTime getSelectedDate(){
  _selectedDate ??= DateTime.now();
  return _selectedDate!;
}


