package com.example.sagar.democp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatternProgramFragment extends Fragment implements PatternProgramAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private PatternProgramAdapter mPatternProgramItemAdapter;
    private ArrayList<PatternProgramItem> mPatternProgramItemList;

    GridLayoutManager gridLayoutManager;
    CardView cardView;

private int[] mBasicsArry = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p14,R.drawable.p15,R.drawable.p16,R.drawable.p17,R.drawable.p18,R.drawable.p19,R.drawable.p20,R.drawable.p21,R.drawable.p22,R.drawable.p23,R.drawable.p24,R.drawable.p25,R.drawable.p26,R.drawable.p27,R.drawable.p28,R.drawable.p29};
    public PatternProgramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pattern_program, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.program_item_rv);
        mRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(),1);

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int viewWidth = mRecyclerView.getMeasuredWidth();
                        //float cardViewWidth = getActivity().getResources().getDimension(R.dimen.cardview_layout_width);
                       /* View inflatedView = getLayoutInflater().inflate(R.layout.pattern_program_list_item, null);
                        CardView cardView = inflatedView.findViewById(R.id.cvPPLI);
                        int cardViewWidth = cardView.getWidth();*/
                        int newSpanCount = (int) Math.floor(viewWidth / 350);
                        gridLayoutManager.setSpanCount(newSpanCount);
                        gridLayoutManager.requestLayout();
                    }
                });

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mPatternProgramItemList = new ArrayList<>();
        /*try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray mBasicsArry = obj.getJSONArray("topic");

            for (int i = 0; i < mBasicsArry.length(); i++) {
                String mImage = mBasicsArry.getString(i);
                Toast.makeText(getActivity(), ""+mImage, Toast.LENGTH_SHORT).show();
                mPatternProgramItemList.add(new PatternProgramItem(mImage));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        for (int i = 0; i < mBasicsArry.length; i++) {
            int mImage = mBasicsArry[i];
            mPatternProgramItemList.add(new PatternProgramItem(mImage));
        }
        mPatternProgramItemAdapter = new PatternProgramAdapter(getActivity(), mPatternProgramItemList);
        mRecyclerView.setAdapter(mPatternProgramItemAdapter);
        mPatternProgramItemAdapter.setOnItemClickListener(this);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.list_animation_left_to_right);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.scheduleLayoutAnimation();
        setHasOptionsMenu(true);
    }
    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("PatternProgram/PatternProgramData.json");
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
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), Pattern_Program_Detail_Activity.class);
        intent.putExtra("List_Item_Position", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_no_animation);
    }
}
