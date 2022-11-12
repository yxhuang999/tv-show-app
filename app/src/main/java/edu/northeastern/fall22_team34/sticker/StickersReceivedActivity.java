package edu.northeastern.fall22_team34.sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.sticker.models.Information;
import edu.northeastern.fall22_team34.sticker.models.User;

public class StickersReceivedActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter imageAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Information> mUser; // User is the class created for single user


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers_received);

        mRecyclerView = findViewById(R.id.receiveList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUser = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Information user = snapshot.getValue(Information.class);
                    mUser.add(user);
                }
                //Log.i("mUser list", String.valueOf(mUser));
                imageAdapter = new ImageAdapter(StickersReceivedActivity.this, mUser);
                mRecyclerView.setAdapter(imageAdapter);

                //personList.add(new Person("John Doe", 18));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StickersReceivedActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}