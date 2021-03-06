package com.example.embeddedprogrammingassignment.fragments.trace;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.adapter.QrHistoryAdapter;
import com.example.embeddedprogrammingassignment.modal.HistoryItem;
import com.example.embeddedprogrammingassignment.modal.QrHistory;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;


public class QrHistoryFragment extends Fragment {

    ArrayList<QrHistory> qrHistories = new ArrayList<>();

    RecyclerView qrHistoryRv;
    LottieAnimationView lottieEmpty;
    TextView emptyTv;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    User user;

    public QrHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace_qr_history, container, false);

        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        Bundle bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));

        qrHistoryRv = view.findViewById(R.id.rvCheckInHistory);
        lottieEmpty = view.findViewById(R.id.lottieQRHistEmpty);
        emptyTv = view.findViewById(R.id.tvQrHisotryNoRecord);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("history").child(user.getNric());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists()) {
                    lottieEmpty.playAnimation();
                    lottieEmpty.setRepeatCount(LottieDrawable.INFINITE);
                    lottieEmpty.setVisibility(View.VISIBLE);
                    emptyTv.setVisibility(View.VISIBLE);
                } else {
                    lottieEmpty.pauseAnimation();
                    lottieEmpty.setVisibility(View.GONE);
                    emptyTv.setVisibility(View.GONE);
                }

                int i = 1;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                        ArrayList<HistoryItem> historyItems = new ArrayList<>();

                        for (DataSnapshot child2DataSnapshot : childDataSnapshot.getChildren()) {

                            HistoryItem historyItem = child2DataSnapshot.getValue(HistoryItem.class);
                            historyItems.add(historyItem);
                        }

                        if(childDataSnapshot.getKey().equals("details")){
                            QrHistory qrHistory = new QrHistory(
                                    dataSnapshot.child("date").getValue(String.class),
                                    dataSnapshot.child("location").getValue(String.class),
                                    historyItems
                            );
                            qrHistories.add(qrHistory);
                        }
                    }

                    if (i == snapshot.getChildrenCount()) {
                        Collections.reverse(qrHistories);
                        QrHistoryAdapter qrHistoryAdapter = new QrHistoryAdapter(qrHistories, user);
                        qrHistoryRv.setAdapter(qrHistoryAdapter);
                        qrHistoryRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        

//        historyItems.add(new HistoryItem(true,"Xiamen University Malaysia", "28-March-2022 07:00:24"));
//        qrHistories.add(new QrHistory("28-March-2022","Xiamen University Malaysia",historyItems));
//        historyItems2.add(new HistoryItem(true,"Pavilion, Kuala Lumpur", "27-March-2022 12:03:14"));
//        historyItems2.add(new HistoryItem(false,"Pavilion, Kuala Lumpur", "27-March-2022 12:38:41"));
//        qrHistories.add(new QrHistory("27-March-2022","Pavilion, Kuala Lumpur",historyItems2));
//        historyItems3.add(new HistoryItem(true,"Lot 88, Kuala Lumpur", "27-March-2022 09:13:13"));
//        historyItems3.add(new HistoryItem(false,"Lot 88, Kuala Lumpur", "27-March-2022 10:07:01"));
//        qrHistories.add(new QrHistory("27-March-2022","Lot 88, Kuala Lumpur",historyItems3));

        return view;
    }
}