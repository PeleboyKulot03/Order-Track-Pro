package com.example.ordertrackpro.ui.controller;

import com.example.ordertrackpro.utils.CartModel;
import com.example.ordertrackpro.utils.MenuModel;

import java.util.ArrayList;

public interface IMenuFragment {
    void getProducts(ArrayList<ArrayList<MenuModel>> models);
    void addToCart(CartModel cartModel);

    void onAddToCart(boolean verdict, String cause);

    void onGetItemCount(int count);
}
