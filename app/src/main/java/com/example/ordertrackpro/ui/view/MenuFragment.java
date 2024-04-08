package com.example.ordertrackpro.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ordertrackpro.R;
import com.example.ordertrackpro.utils.MenuModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;

public class MenuFragment extends Fragment {

    private MenuRecyclerviewAdapter alaCartAdapter;
    private MenuRecyclerviewAdapter drinksAndDessertAdapter;
    private MenuRecyclerviewAdapter riceMealAdapter;
    private MenuFragmentAdapter adapter;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        LinearLayout firstPhase = view.findViewById(R.id.firstPhase);
        LinearLayout secondPhase = view.findViewById(R.id.secondPhase);
        CardView dineIn = view.findViewById(R.id.dineIn);
        CardView takeOut = view.findViewById(R.id.takeOut);
//        AppBarLayout appBarLayout = view.findViewById(R.id.toolBar);

        dineIn.setOnClickListener(v -> {
            firstPhase.setVisibility(View.GONE);
            modeOfEating = "Dine in";
            secondPhase.setVisibility(View.VISIBLE);
            secondPhase.setAlpha(0.0f);
            secondPhase.animate().alpha(1.0f).setDuration(1000);
//            appBarLayout.setVisibility(View.VISIBLE);
        });
        takeOut.setOnClickListener(v -> {
            firstPhase.setVisibility(View.GONE);
            modeOfEating = "Take out";
            secondPhase.setVisibility(View.VISIBLE);
            secondPhase.setAlpha(0.0f);
            secondPhase.animate().alpha(1.0f).setDuration(1000);
//            appBarLayout.setVisibility(View.VISIBLE);
        });

        ViewPager2 pager = view.findViewById(R.id.pager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        ArrayList<MenuModel> alaCarts = getModels();
        ArrayList<MenuModel> drinks = getMenuModels();
        ArrayList<MenuModel> riceMealList = getMenuModelArrayList();

        alaCartAdapter = new MenuRecyclerviewAdapter(alaCarts, getContext(), getActivity());
        riceMealAdapter = new MenuRecyclerviewAdapter(riceMealList, getContext(), getActivity());
        drinksAndDessertAdapter = new MenuRecyclerviewAdapter(drinks, getContext(), getActivity());

        ArrayList<MenuRecyclerviewAdapter> adapters = new ArrayList<>();
        adapters.add(alaCartAdapter);
        adapters.add(riceMealAdapter);
        adapters.add(drinksAndDessertAdapter);

        adapter = new MenuFragmentAdapter(adapters, getContext());
        pager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> tab.setText(tabTexts[position])).attach();

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
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
        
        return view;
    }

    @NonNull
    private static ArrayList<MenuModel> getMenuModelArrayList() {
        MenuModel model22 = new MenuModel(R.drawable.bbq_inasal_3pcs_with_rice___99_pesos, "BBQ Inasal With Rice", 99, 3);
        MenuModel model23 = new MenuModel(R.drawable.buy1take1_chicken_inasal___159_pesos, "B1T1 Chicken With Rice", 159, 1);
        MenuModel model17 = new MenuModel(R.drawable.goto_bulalo_with_rice___60_pesos, "Goto Bulalo With Rice", 60, 1);
        MenuModel model21 = new MenuModel(R.drawable.wings_inasal_2pcs_with_rice___55_pesos, "Wings Insala With Rice", 55, 2);
        MenuModel model19 = new MenuModel(R.drawable.pork_inasal_with_rice___119_pesos, "Pork Inasal With Rice", 119, 1);
        MenuModel model20 = new MenuModel(R.drawable.sizzling_sisig_solo_with_rice___100_pesos, "Sizzling Sisig Solo With Rice", 100, 1);
        MenuModel model24 = new MenuModel(R.drawable.solo_chicken_inasal___89_pesos, "Solo Chicken Inasal", 89, 2);

        ArrayList<MenuModel> riceMealList = new ArrayList<>();
        riceMealList.add(model22);
        riceMealList.add(model23);
        riceMealList.add(model17);
        riceMealList.add(model21);
        riceMealList.add(model19);
        riceMealList.add(model20);
        riceMealList.add(model24);
        return riceMealList;
    }

    @NonNull
    private static ArrayList<MenuModel> getMenuModels() {
        MenuModel model8 = new MenuModel(R.drawable.blue_lemonade___20_pesos, "Blue Lemonade", 20, 1);
        MenuModel model9 = new MenuModel(R.drawable.coke___25_pesos, "Coke", 25, 1);
        MenuModel model10 = new MenuModel(R.drawable.halo_halo____59_pesos, "Halo-Halo", 59, 1);
        MenuModel model11 = new MenuModel(R.drawable.lemon_iced_tea___20_pesos, "Lemon Iced Tea", 20, 1);
        MenuModel model12 = new MenuModel(R.drawable.maiscon_caramel___45_pesos, "Maison Caramel", 45, 1);
        MenuModel model13 = new MenuModel(R.drawable.red_iced_tea___20_pesos, "Red Ice Tea", 20, 1);
        MenuModel model14 = new MenuModel(R.drawable.royal___25_pesos, "Royal", 25, 1);
        MenuModel model15 = new MenuModel(R.drawable.sprite___25_pesos, "Sprite", 25, 1);

        ArrayList<MenuModel> drinks = new ArrayList<>();
        drinks.add(model8);
        drinks.add(model9);
        drinks.add(model10);
        drinks.add(model11);
        drinks.add(model12);
        drinks.add(model13);
        drinks.add(model14);
        drinks.add(model15);
        return drinks;
    }

    @NonNull
    private static ArrayList<MenuModel> getModels() {
        MenuModel model = new MenuModel(R.drawable.bbq_inasal_3pcs___85_pesos, "BBQ Inasl", 300, 1);
        MenuModel model1 = new MenuModel(R.drawable.butterfly_chicken___300_pesos, "Butterfly Chicken", 300, 1);
        MenuModel model2 = new MenuModel(R.drawable.buy1take1_chicken_inasal___145_pesos, "B1T1 Chicken Inasal", 145, 2);
        MenuModel model3 = new MenuModel(R.drawable.goto_bulalo_overload___100_pesos, "Goto Bulalo Overload", 100, 1);
        MenuModel model4 = new MenuModel(R.drawable.goto_bulalo_regular___50_pesos, "Goto Bulalo Regular", 50, 1);
        MenuModel model5 = new MenuModel(R.drawable.pork_inasal____109_pesos, "Pork Inasal", 109, 1);
        MenuModel model6 = new MenuModel(R.drawable.sizzling_sisig_sharing___150_pesos, "Sizzling Sisig Sharing", 150, 1);
        MenuModel model7 = new MenuModel(R.drawable.wings_inasal_2pcs___45_pesos, "Wings Inasal", 45, 2);

        ArrayList<MenuModel> alaCarts = new ArrayList<>();
        alaCarts.add(model);
        alaCarts.add(model1);
        alaCarts.add(model2);
        alaCarts.add(model3);
        alaCarts.add(model4);
        alaCarts.add(model5);
        alaCarts.add(model6);
        alaCarts.add(model7);
        return alaCarts;
    }
}