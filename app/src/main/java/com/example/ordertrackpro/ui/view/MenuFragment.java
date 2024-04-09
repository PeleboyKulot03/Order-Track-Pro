package com.example.ordertrackpro.ui.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.ui.controller.IMenuFragment;
import com.example.ordertrackpro.utils.CartModel;
import com.example.ordertrackpro.utils.MenuModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.Objects;
import ru.nikartm.support.ImageBadgeView;

public class MenuFragment extends Fragment implements IMenuFragment {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private ArrayList<CartModel> cartModels;
    private ImageBadgeView cart;
    private final int[] tabIcons = {
            R.drawable.lunch_dining_fill0_wght400_grad0_opsz24,
            R.drawable.fastfood_fill0_wght400_grad0_opsz24,
            R.drawable.water_full_fill0_wght400_grad0_opsz24
    };
    private final String[] tabTexts = {
            "Ala Carts",
            "Rice Meals",
            "Drinks and Deserts"
    };
    private String modeOfEating = "";
    private MenuModel menuModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        cart = view.findViewById(R.id.cart);
        cartModels = new ArrayList<>();
        LinearLayout firstPhase = view.findViewById(R.id.firstPhase);
        LinearLayout secondPhase = view.findViewById(R.id.secondPhase);
        CardView dineIn = view.findViewById(R.id.dineIn);
        CardView takeOut = view.findViewById(R.id.takeOut);

        menuModel = new MenuModel();
        menuModel.getProducts(this);
        menuModel.getNumOrders(this);

        dineIn.setOnClickListener(v -> {
            firstPhase.setVisibility(View.GONE);
            modeOfEating = "Dine in";
            secondPhase.setVisibility(View.VISIBLE);
            secondPhase.setAlpha(0.0f);
            secondPhase.animate().alpha(1.0f).setDuration(1000);
        });
        takeOut.setOnClickListener(v -> {
            firstPhase.setVisibility(View.GONE);
            modeOfEating = "Take out";
            secondPhase.setVisibility(View.VISIBLE);
            secondPhase.setAlpha(0.0f);
            secondPhase.animate().alpha(1.0f).setDuration(1000);
        });

        pager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tabLayout);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is triggered when there is any scrolling activity for the current page
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
            
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        cart.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CartActivity.class));
        });
        return view;
    }

    @Override
    public void getProducts(ArrayList<ArrayList<MenuModel>> models) {
        ArrayList<MenuRecyclerviewAdapter> adapters = getMenuRecyclerviewAdapters(models);

        MenuFragmentAdapter adapter = new MenuFragmentAdapter(adapters, getContext());
        pager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> tab.setText(tabTexts[position])).attach();

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
    }

    @Override
    public void addToCart(CartModel cartModel) {
        menuModel.addToCart(MenuFragment.this, cartModel);
    }

    @Override
    public void onAddToCart(boolean verdict, String cause) {
        if (verdict) {
            cart.setBadgeValue(cart.getBadgeValue() + 1);
        }
        Toast.makeText(getContext(), cause, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetItemCount(int count) {
        cart.setBadgeValue(count);
    }

    @NonNull
    private ArrayList<MenuRecyclerviewAdapter> getMenuRecyclerviewAdapters(ArrayList<ArrayList<MenuModel>> models) {
        MenuRecyclerviewAdapter alaCartAdapter = new MenuRecyclerviewAdapter(models.get(0), getContext(), getActivity(), MenuFragment.this);
        MenuRecyclerviewAdapter riceMealAdapter = new MenuRecyclerviewAdapter(models.get(2), getContext(), getActivity(), MenuFragment.this);
        MenuRecyclerviewAdapter drinksAndDessertAdapter = new MenuRecyclerviewAdapter(models.get(1), getContext(), getActivity(), MenuFragment.this);

        ArrayList<MenuRecyclerviewAdapter> adapters = new ArrayList<>();
        adapters.add(alaCartAdapter);
        adapters.add(riceMealAdapter);
        adapters.add(drinksAndDessertAdapter);
        return adapters;
    }
}