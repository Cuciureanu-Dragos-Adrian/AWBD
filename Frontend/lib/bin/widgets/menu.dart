import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/constants.dart';
import 'package:restaurant_management_app/bin/models/category_model.dart';
import 'package:restaurant_management_app/bin/services/category_service.dart';
import 'package:restaurant_management_app/bin/widgets/custom_button.dart';
import 'package:restaurant_management_app/bin/widgets/dialog.dart';
import 'dart:math';

import '../services/product_service.dart';
import '../models/product_model.dart';

const double expandedMaxHeight = 400;

class Menu extends StatefulWidget {
  const Menu({Key? key}) : super(key: key);

  @override
  _MenuState createState() => _MenuState();
}

class _MenuState extends State<Menu> {
  // Declare sections as a state variable
  List<CategoryModel> _menuCategories = [];

  @override
  void initState() {
    super.initState();

    //load categories
    loadCategoriesAsync();
  }

  void loadCategoriesAsync() async {
    try {
      var response = await CategoryService.getCategoryList();
      setState(() {
        _menuCategories = response;
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch categories!');
      return;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(children: [
      Container(
        //wrap row with container to set color
        color: accent1Color,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            Container(
              margin: const EdgeInsets.only(bottom: 10, top: 10, right: 10),
              child: CustomButton(
                  // Add CustomButton to actions
                  icon: const Icon(Icons.add),
                  size: 50,
                  onPressed: () {
                    // Handle button press (e.g., navigate to add category page)
                    print('Add button pressed!');
                  },
                  color: mainColor),
            )
          ],
        ),
      ),
      Expanded(
        // Wrap ListView with Expanded
        child: ListView.builder(
          controller: ScrollController(),
          itemBuilder: (BuildContext context, int index) {
            return MenuSection(categoryName: _menuCategories[index].name);
          },
          itemCount: _menuCategories.length,
        ),
      ),
    ]);
  }
}

//menu section/category widget
class MenuSection extends StatefulWidget {
  final String categoryName;

  const MenuSection({Key? key, required this.categoryName}) : super(key: key);

  @override
  _MenuSectionState createState() => _MenuSectionState();
}

class _MenuSectionState extends State<MenuSection> {
  bool _expandFlag = false;
  String _errorMessage = "";
  List<ProductModel> _products = [];
  final TextEditingController nameController = TextEditingController();
  final TextEditingController priceController = TextEditingController();

  @override
  void initState() {
    super.initState();

    loadProductsAsync();
  }

  void loadProductsAsync() async {
    try {
      var response =
          await ProductService.getProductListByCategory(widget.categoryName);
      setState(() {
        _products = response;
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch products!');
      return;
    }
  }

  @override
  void dispose() {
    nameController.dispose();
    priceController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 1.0),
      child: Column(
        children: <Widget>[
          Container(
            // top bar containing title and expand button
            color: accent1Color,
            padding: const EdgeInsets.symmetric(horizontal: 5.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Row(
                  children: [
                    IconButton(
                        // expand button
                        icon: Container(
                          height: 50.0,
                          width: 50.0,
                          decoration: const BoxDecoration(
                            color: mainColor,
                            shape: BoxShape.circle,
                          ),
                          child: Center(
                            child: Icon(
                              _expandFlag
                                  ? Icons.keyboard_arrow_up
                                  : Icons.keyboard_arrow_down,
                              color: Colors.white,
                              size: 24,
                            ),
                          ),
                        ),
                        onPressed: () {
                          setState(() {
                            _expandFlag = !_expandFlag;
                          });
                        }),
                    IconButton(
                        // expand button
                        icon: Container(
                          height: 50.0,
                          width: 50.0,
                          decoration: const BoxDecoration(
                            color: mainColor,
                            shape: BoxShape.circle,
                          ),
                          child: const Center(
                            child: Icon(
                              Icons.add,
                              color: Colors.white,
                              size: 24,
                            ),
                          ),
                        ),
                        onPressed: () async {
                          setState(() {
                            _expandFlag = true;
                          });

                          await showDialog(
                              context: context,
                              barrierDismissible: false,
                              builder: (BuildContext context) {
                                return StatefulBuilder(
                                    builder: (context, setState) {
                                  return AlertDialog(
                                    title: const Text(
                                      "Add new item",
                                      style: TextStyle(color: mainColor),
                                    ),
                                    content: SizedBox(
                                      height: 200,
                                      child: Column(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceAround,
                                        children: [
                                          TextField(
                                            decoration: const InputDecoration(
                                                hintText: "Enter product name"),
                                            controller: nameController,
                                          ),
                                          TextField(
                                            decoration: const InputDecoration(
                                                hintText: "Enter price"),
                                            controller: priceController,
                                          ),
                                          Text(
                                            _errorMessage,
                                            style: const TextStyle(
                                                color: Colors.redAccent),
                                          ),
                                        ],
                                      ),
                                    ),
                                    actions: [
                                      TextButton(
                                        child: const Text('Add'),
                                        onPressed: () async {
                                          setState(() {
                                            _errorMessage = "";
                                          });

                                          String name =
                                              nameController.text.trim();

                                          if (name.length < 3) {
                                            setState(() {
                                              _errorMessage =
                                                  "Product name must have at least 3 characters!";
                                            });
                                            return;
                                          }

                                          if (name.length > 20) {
                                            setState(() {
                                              _errorMessage =
                                                  "Product name must have at most 20 characters!";
                                            });
                                            return;
                                          }

                                          double? price = double.tryParse(
                                              priceController.text);

                                          if (price == null || price <= 0) {
                                            setState(() {
                                              _errorMessage =
                                                  "Incorrect price! Must be a number higher than 0.";
                                            });
                                            return;
                                          }

                                          await createProduct(
                                              name, price, widget.categoryName);
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
                        }),
                  ],
                ),
                Text(
                  // Section title
                  widget.categoryName.toUpperCase(),
                  style: const TextStyle(
                      fontWeight: FontWeight.bold, color: mainColor),
                )
              ],
            ),
          ),
          MenuSectionContent(
            expanded: _expandFlag,
            itemCount: _products.length,
            child: _products.isEmpty
                ? const Center(
                    child: Text(
                      'No products found in this category.',
                      style: TextStyle(color: Colors.grey),
                    ),
                  )
                : ListView.builder(
                    controller: ScrollController(),
                    itemBuilder: (BuildContext context, int index) {
                      List<ProductModel> items = _products;
                      items.sort();
                      return MenuItem(
                        price: items[index].price,
                        name: items[index].name,
                        category: widget.categoryName,
                        function: deleteProductByName,
                      );
                    },
                    itemCount: _products.length,
                  ),
          )
        ],
      ),
    );
  }

  Future<void> createProduct(String name, double price, String category) async {
    ProductModel newProduct =
        ProductModel(name: name, price: price, category: category);
    try {
      await ProductService.addProduct(newProduct);
      var products = await ProductService.getProductList();

      setState(() {
        _products = products;
      });
    } on Exception catch (e) {
      showMessageBox(context, "Failed to add product! " + e.toString());
    }
  }

  Future<void> deleteProductByName(String name) async {
    try {
      await ProductService.removeProductByName(name);
      var products = await ProductService.getProductList();

      setState(() {
        _products = products;
      });
    } on Exception catch (e) {
      showMessageBox(context, "Failed to remove product! " + e.toString());
    }
  }
}

//Expanded content of a section
class MenuSectionContent extends StatelessWidget {
  final bool expanded;
  final double collapsedHeight;
  late final double expandedHeight;
  final Widget child;
  final int itemCount;

  MenuSectionContent(
      {Key? key,
      required this.child,
      required this.itemCount,
      this.collapsedHeight = 0.0,
      this.expanded = true})
      : super(key: key) {
    if (itemCount == 0) {
      expandedHeight = 50;
    } else {
      expandedHeight = min(
          expandedMaxHeight, itemCount * 50); //50 is the height of a MenuItem
    }
  }

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    return AnimatedContainer(
      //expand animation
      duration: const Duration(milliseconds: 500),
      curve: Curves.easeInOut,
      width: screenWidth,
      height: expanded ? expandedHeight : collapsedHeight,
      child: Container(
        child: child,
      ),
    );
  }
}

//Single item in a section's expanded tab
class MenuItem extends StatelessWidget {
  final String name;
  final double price;
  final String category;
  final Future<void> Function(String) function;

  const MenuItem({
    Key? key,
    required this.name,
    required this.price,
    required this.category,
    required this.function,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          border: Border.all(width: 1, color: accent1Color),
          color: accent2Color),
      child: ListTile(
        title:
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Text(name),
          Row(
            children: [
              Container(
                  margin: const EdgeInsets.fromLTRB(0, 0, 8, 0),
                  child: Text(price.toString())),
              CustomButton(
                  color: Colors.red,
                  onPressed: () async {
                    await function(name);
                  },
                  size: 25,
                  icon: const Icon(Icons.delete)),
            ],
          ),
        ]),
        leading: const Icon(
          //TODO - get icon from category
          Icons.dining_sharp,
          color: mainColor,
        ),
      ),
    );
  }
}
