package com.example.ordertrackpro.ui.controller;

import com.example.ordertrackpro.utils.CartModel;

import java.util.ArrayList;

public interface ICartActivity {
    void getOrders(ArrayList<CartModel> models);
    void onPay(boolean verdict, String message, String reference, double total, double money);
    void onSuccess(boolean verdict, String message);
}
