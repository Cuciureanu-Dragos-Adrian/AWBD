class GlobalsModel {
  late double tableImagesScale;
  late int floorGridStep;

  GlobalsModel({required this.tableImagesScale, required this.floorGridStep});

  GlobalsModel.fromJson(Map<String, dynamic> json) {
    tableImagesScale = json['tableImagesScale'];
    floorGridStep = json['floorGridStep'];
  }

  Map<String, dynamic> toJson() {
    return {
      'tableImagesScale': tableImagesScale,
      'floorGridStep': floorGridStep
    };
  }

  @override
  String toString() {
    String rep = "{\n";
    rep += '"tableImagesScale": "$tableImagesScale",\n';
    rep += '"floorGridStep": $floorGridStep,\n';
    rep += "}";
    return rep;
  }
}
