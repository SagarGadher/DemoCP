package com.example.sagar.democp;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicsFragment extends Fragment implements BasicsItemAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private BasicsItemAdapter mBasicsItemAdapter;
    private ArrayList<BasicsItem> mBasicsItemList;

    public BasicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.basics_item_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBasicsItemList = new ArrayList<>();

//       mBasicsItemList.add(new BasicsItem("Introduction"));
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray mBasicsArry = obj.getJSONArray("topic");

            for (int i = 0; i < mBasicsArry.length(); i++) {
                String mTopicName =mBasicsArry.getString(i);
                mBasicsItemList.add(new BasicsItem(mTopicName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mBasicsItemAdapter = new BasicsItemAdapter(getActivity(),mBasicsItemList);
        mRecyclerView.setAdapter(mBasicsItemAdapter);
        mBasicsItemAdapter.setOnItemClickListener(this);
        setHasOptionsMenu(true);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.list_animation_left_to_right);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(),Detail_Activity.class);
        BasicsItem clickItem = mBasicsItemList.get(position);
        intent.putExtra("List_Item_Name",clickItem.getTopic());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_no_animation);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mBasicsItemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("Basics/basicsData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
