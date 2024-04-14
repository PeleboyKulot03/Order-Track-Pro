package com.example.ordertrackpro.ui.controller;

import com.example.ordertrackpro.utils.ShowReceiptModel;

import java.util.ArrayList;

public interface IShowReceipt {
    void getReceipt(ArrayList<ShowReceiptModel> models, double money, String date, String time);
}
