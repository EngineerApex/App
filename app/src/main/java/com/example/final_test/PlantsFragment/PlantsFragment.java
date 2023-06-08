package com.example.final_test.PlantsFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.final_test.R;

import java.util.ArrayList;

public class PlantsFragment extends Fragment {

    private ListView listview;
    private PlantsAdapter plantsAdapter;

    public PlantsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //TASK - To fix findviewbyid problem !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        View rootView = inflater.inflate(R.layout.fragment_plants, container, false);

        listview = rootView.findViewById(R.id.listview);
        ArrayList<Plant> arrayList = new ArrayList<>();

        arrayList.add(new Plant(R.drawable.indian_mustard, "Indian Mustard", "Brassica juncea or Brown Mustard is an annual herb in the cruciferous family."));
        arrayList.add(new Plant(R.drawable.pomogranate, "Pomogranate", "Its a fruit-bearing deciduous shrub in the family Lythraceae, subfamily Punicoideae"));
        arrayList.add(new Plant(R.drawable.peepal, "Peepal", "ficus religiosa is a species of fig native to the Indian subcontinent that belongs to Moraceae"));
        arrayList.add(new Plant(R.drawable.parijata, "Parijata", "Nyctanthes arbor-tristis, is a species of Nyctanthes native to Southeast Asia."));
        arrayList.add(new Plant(R.drawable.jackfruit, "Jackfruit", "Grown in Asia. It belongs to the same plant family as figs and mulberries."));
        arrayList.add(new Plant(R.drawable.jasmine, "Jasmine", "Jasmine is a genus of shrubs and vines in the olive family (Oleaceae)"));
        arrayList.add(new Plant(R.drawable.jamun, "Jamun", "Jamun, the highly nutritious, refreshing fruit has innumerable health benefits."));
        arrayList.add(new Plant(R.drawable.neem, "Neem", "Azadirachta indica, It is one of two species in the genus Azadirachta, and is native to the Indian subcontinent."));
        arrayList.add(new Plant(R.drawable.mango, "Mango", "A mango is an edible stone fruit produced by the tropical tree Mangifera indica"));
        arrayList.add(new Plant(R.drawable.mint, "Mint", "Mentha is a genus of plants in the family Lamiaceae (mint family)"));
        arrayList.add(new Plant(R.drawable.rose_apple, "Rose Apple", "Rose apples are one of the most common hosts for fruit flies like A."));
        arrayList.add(new Plant(R.drawable.lemon, "Lemon", "The lemon is a species of small evergreen trees in the flowering plant family Rutaceae, native to Asia, primarily India"));
        arrayList.add(new Plant(R.drawable.hibiscus, "Hibiscus", "Hibiscus is a genus of flowering plants in the mallow family, Malvaceae."));
        arrayList.add(new Plant(R.drawable.fenugreek, "Fenugreek", "Fenugreek is an annual plant in the family Fabaceae, with leaves consisting of three small obovate to oblong leaflets."));


        PlantsAdapter plantsAdapter = new PlantsAdapter(getContext(), R.layout.list_row, arrayList); //context: this

        listview.setAdapter(plantsAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0) {
                    startActivity(new Intent(getContext(), indian_mustard.class));
                }
                if(i==1) {
                    startActivity(new Intent(getContext(), pomogranate.class));
                }
                if(i==2) {
                    startActivity(new Intent(getContext(), peepal.class));
                }
                if(i==3) {
                    startActivity(new Intent(getContext(), parijata.class));
                }
                if(i==4) {
                    startActivity(new Intent(getContext(), jackfruit.class));
                }
                if(i==5) {
                    startActivity(new Intent(getContext(), jasmine.class));
                }
                if(i==6) {
                    startActivity(new Intent(getContext(), jamun.class));
                }
                if(i==7) {
                    startActivity(new Intent(getContext(), neem.class));
                }
                if(i==8) {
                    startActivity(new Intent(getContext(), mango.class));
                }
                if(i==9) {
                    startActivity(new Intent(getContext(), mint.class));
                }
                if(i==10) {
                    startActivity(new Intent(getContext(), rose_apple.class));
                }
                if(i==11) {
                    startActivity(new Intent(getContext(), lemon.class));
                }
                if(i==12) {
                    startActivity(new Intent(getContext(), hibiscus.class));
                }
                if(i==13) {
                    startActivity(new Intent(getContext(), fenugreek.class));
                }

            }
        });

        return rootView;
    }

    private ArrayList<Plant> arrayList;

}