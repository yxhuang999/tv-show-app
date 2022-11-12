package edu.northeastern.fall22_team34.sticker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.sticker.models.User;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<User> muser;

    public ImageAdapter(Context context, List<User> imageReceived) {
        mContext = context;
        muser = imageReceived;
        muser = new ArrayList<>();
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_stickers_received, parent, false);
        return new ImageViewHolder(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        // get image out of uploaded items into cardview
        User currentUserInfo = muser.get(position);
        // set text view - our view holder
//        Intent received = ((Activity)mContext).getIntent();
//        String getSenders = received.getStringExtra("senders list");
//        String getImages = received.getStringExtra("RECEIVED");
//        String getTimes = received.getStringExtra("time list");

//        holder.textViewName.setText("Senders: " + currentUserInfo.getSenderReceived() + "/n"
//                + "Time received: " + currentUserInfo.getTimeReceived());


        // get image to imageView, use picasso because loads image from url easy

//        Picasso.get()
//                .load(String.valueOf(currentUserInfo.getImgReceived()))
//                .fit()
//                .centerCrop()
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return muser.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.sender_received_time);
            imageView = itemView.findViewById(R.id.image_view_receive);
        }
    }

}
