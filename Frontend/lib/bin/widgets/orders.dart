import 'dart:math';

import 'package:flutter/material.dart';
import 'package:restaurant_management_app/bin/models/product_model.dart';
import 'package:restaurant_management_app/bin/widgets/custom_button.dart';
import 'package:restaurant_management_app/bin/widgets/dialog.dart';

import '../constants.dart';
import '../services/product_service.dart';
import '../services/table_service.dart';
import '../models/order_model.dart';
import '../services/order_service.dart';
import '../models/table_model.dart';

const double expandedMaxHeight = 400;

class OrdersWidget extends StatefulWidget {
  const OrdersWidget({Key? key}) : super(key: key);

  @override
  State<OrdersWidget> createState() => _OrdersWidgetState();
}

class _OrdersWidgetState extends State<OrdersWidget> {
  List<ProductModel> _products = [];
  List<TableModel> _tables = [];
  List<OrderModel> _orders = [];

  String _dialogErrorMessage = "";
  String _currentSelectedProduct = '';
  String _selectedTable = '';

  List<ProductModel> _dialogProducts = [];
  List<int> _dialogProductQuantities = [];
  final TextEditingController _quantityController = TextEditingController();

  @override
  void initState() {
    super.initState();

    loadProductsAsync();
    loadTablesAsync();
    loadOrdersAsync();
  }

  void loadTablesAsync() async {
    try {
      var response = await TableService.getTables();
      setState(() {
        _tables = response;
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch tables!');
      return;
    }

    if (_tables.isNotEmpty) {
      _selectedTable = _tables[0].id;
    }
  }

  void loadOrdersAsync() async {
    try {
      var response = await OrderService.getOrderList();
      setState(() {
        _orders = response;
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch orders!');
      return;
    }
  }

  void loadProductsAsync() async {
    try {
      var response = await ProductService.getProductList();
      setState(() {
        _products = response;

        if (_products.isNotEmpty) {
          _currentSelectedProduct = _products[0].name;
        }
      });
    } on Exception {
      showMessageBox(context, 'Failed to fetch products!');
      return;
    }
  }

  @override
  void dispose() {
    _quantityController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        return Column(children: [
          SizedBox(
            width: constraints.maxWidth,
            height: constraints.maxHeight * 9 / 10,
            child: ListView.builder(
                controller: ScrollController(),
                itemBuilder: (BuildContext context, int index) {
                  return OrderSection(
                    title: _orders[index].tableId +
                        '     ' +
                        _orders[index].price.toString(),
                    orderModel: _orders[index],
                  );
                },
                itemCount: _orders.length),
          ),
          SizedBox(
              width: constraints.maxWidth,
              height: constraints.maxHeight * 1 / 10,
              child:
                  Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                Container(
                  margin: const EdgeInsets.only(bottom: 10),
                  child: CustomButton(
                    color: mainColor,
                    size: 50,
                    icon: const Icon(Icons.add),
                    onPressed: () async {
                      _dialogProducts = [];
                      _dialogProductQuantities = [];
                      await showDialog(
                          context: context,
                          barrierDismissible: false,
                          builder: (BuildContext context) {
                            return StatefulBuilder(
                                builder: (context, setState) {
                              return AlertDialog(
                                title: const Text(
                                  "Add new order",
                                  style: TextStyle(color: mainColor),
                                ),
                                content: SizedBox(
                                  height: 300,
                                  width: 300,
                                  child: Column(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceAround,
                                    children: [
                                      ////
                                      Column(
                                        children: [
                                          const Text(
                                            "Choose Products",
                                            style: TextStyle(
                                                color: Colors.black87),
                                          ),
                                          Row(
                                            children: [
                                              Container(
                                                margin:
                                                    const EdgeInsets.symmetric(
                                                        horizontal: 10),
                                                child: DropdownButton<String>(
                                                  value: _selectedTable,
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
                                                      _selectedTable =
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
                                              ),
                                              Container(
                                                margin:
                                                    const EdgeInsets.symmetric(
                                                        horizontal: 10),
                                                child: DropdownButton<String>(
                                                  value:
                                                      _currentSelectedProduct,
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
                                                      _currentSelectedProduct =
                                                          newValue!;
                                                    });
                                                  },
                                                  items: _products.map<
                                                          DropdownMenuItem<
                                                              String>>(
                                                      (ProductModel value) {
                                                    return DropdownMenuItem<
                                                        String>(
                                                      value: value.name,
                                                      child: Text(value.name),
                                                    );
                                                  }).toList(),
                                                ),
                                              ),
                                              TextButton(
                                                onPressed: () {
                                                  setState(() {
                                                    addProductToCurrentOrder();
                                                  });
                                                },
                                                child: const Text("Add product",
                                                    style: TextStyle(
                                                        fontWeight:
                                                            FontWeight.bold)),
                                                style: TextButton.styleFrom(
                                                    foregroundColor: mainColor),
                                              ),
                                            ],
                                          ),
                                        ],
                                      ),

                                      // LayoutBuilder(
                                      //   builder: (context, constraints) {
                                      Flexible(
                                          child: ListView.builder(
                                        controller: ScrollController(),
                                        itemBuilder:
                                            (BuildContext context, int index) {
                                          return DialogListItem(
                                            name: _dialogProducts[index].name,
                                            category:
                                                _dialogProducts[index].category,
                                            quantity:
                                                _dialogProductQuantities[index],
                                          );
                                        },
                                        itemCount: _dialogProducts.length,
                                      )),
                                      //   }
                                      // ),

                                      TextField(
                                        decoration: const InputDecoration(
                                            hintText: "Enter quantity"),
                                        controller: _quantityController,
                                      ),
                                      Text(
                                        _dialogErrorMessage,
                                        style: const TextStyle(
                                            color: Colors.redAccent),
                                      ),
                                    ],
                                  ),
                                ),
                                actions: [
                                  TextButton(
                                    child: const Text('Create order'),
                                    onPressed: () async {
                                      await tryAddOrder();
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
                )
              ]))
        ]);
      },
    );
  }

  void addProductToCurrentOrder() {
    int? quantity = int.tryParse(_quantityController.text);

    if (quantity == null || quantity <= 0) {
      _dialogErrorMessage =
          "Incorrect quantity! Must be a number higher than 0.";
      return;
    }

    ProductModel product =
        _products.firstWhere((prod) => prod.name == _currentSelectedProduct);

    //the product already exists if this != -1
    bool exists = false;

    for (int index = 0; index < _dialogProducts.length; index++) {
      if (_dialogProducts[index].name == _currentSelectedProduct) {
        _dialogProductQuantities[index] += quantity;
        exists = true;
        break;
      }
    }

    if (!exists) {
      _dialogProducts.add(product);
      _dialogProductQuantities.add(quantity);
    }

    _dialogErrorMessage = "";
  }

  Future<void> tryAddOrder() async {
    //check if table is available
    _selectedTable;
    if (_orders.any((element) => element.tableId == _selectedTable)) {
      showMessageBox(
          context, "The selected table already has an assigned order!");
      return;
    }

    //validate number of products
    if (_dialogProductQuantities.isEmpty) {
      showMessageBox(context, "Order must have at least one product!");
      return;
    }

    try {
      await createOrder();
      Navigator.of(context).pop();
    } on Exception catch (e) {
      showMessageBox(context, "Failed to add order! " + e.toString());
    }
  }

  Future<void> createOrder() async {
    OrderModel newOrder = OrderModel(
        orderId: "",
        products: (_dialogProducts),
        quantities: _dialogProductQuantities,
        tableId: _selectedTable,
        price: 0);

    await OrderService.addOrder(newOrder);

    var orders = await OrderService.getOrderList();
    setState(() {
      _orders = orders;
    });
  }
}

//Order section/category widget
class OrderSection extends StatefulWidget {
  final String title;
  final OrderModel orderModel;

  const OrderSection({Key? key, required this.title, required this.orderModel})
      : super(key: key);

  @override
  _OrderSectionState createState() => _OrderSectionState();
}

class _OrderSectionState extends State<OrderSection> {
  bool expandFlag = false;

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
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
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
                          expandFlag
                              ? Icons.keyboard_arrow_up
                              : Icons.keyboard_arrow_down,
                          color: Colors.white,
                          size: 24,
                        ),
                      ),
                    ),
                    onPressed: () {
                      setState(() {
                        expandFlag = !expandFlag;
                      });
                    }),
                Text(
                  // Section title
                  widget.title,
                  style: const TextStyle(
                      fontSize: 22,
                      fontWeight: FontWeight.bold,
                      color: mainColor),
                )
              ],
            ),
          ),
          OrderSectionContent(
              // expanded content
              expanded: expandFlag,
              itemCount: widget.orderModel.products.length,
              child: ListView.builder(
                controller: ScrollController(),
                itemBuilder: (BuildContext context, int index) {
                  List<ProductModel> items = widget.orderModel.products;

                  return OrderItem(
                    price: items[index].price,
                    name: items[index].name,
                    quantity: widget.orderModel.quantities[index],
                    category: items[index].category,
                  );
                },
                itemCount: widget.orderModel.products.length,
              ))
        ],
      ),
    );
  }
}

class OrderSectionContent extends StatelessWidget {
  final bool expanded;
  final double collapsedHeight;
  late final double expandedHeight;
  final Widget child;
  final int itemCount;

  OrderSectionContent(
      {Key? key,
      required this.child,
      required this.itemCount,
      this.collapsedHeight = 0.0,
      this.expanded = true})
      : super(key: key) {
    expandedHeight = min(
        expandedMaxHeight, itemCount * 50); //50 is the height of a OrderItem
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
class OrderItem extends StatelessWidget {
  final String name;
  final double price;
  final int quantity;
  late final double totalPrice;
  final String category;

  OrderItem({
    Key? key,
    required this.name,
    required this.price,
    required this.quantity,
    required this.category,
  }) : super(key: key) {
    totalPrice = price * quantity;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          border: Border.all(width: 1, color: accent1Color),
          color: accent2Color),
      child: ListTile(
        title:
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Text(name + ' X' + quantity.toString()),
          Text(totalPrice.toString()),
        ]),
        leading: const Icon(
          //TODO - get icon from category
          Icons.food_bank,
          color: mainColor,
        ),
      ),
    );
  }
}

class DialogListItem extends StatelessWidget {
  final String name;
  final int quantity;
  final String category;

  const DialogListItem(
      {Key? key,
      required this.name,
      required this.quantity,
      required this.category})
      : super(key: key);

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
          Text('X' + quantity.toString()),
        ]),
        leading: const Icon(
          //TODO - get icon from category
          Icons.food_bank,
          color: mainColor,
        ),
      ),
    );
  }
}
