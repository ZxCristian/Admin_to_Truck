package com.example.admintotruck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SubAdapter1 extends FirebaseRecyclerAdapter<History,SubAdapter1.myViewHolder1>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SubAdapter1(@NonNull FirebaseRecyclerOptions<History> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder1 holder, int position, @NonNull History model) {
        holder.HName.setText(model.getName());
        holder.HPrice.setText(model.getPrice());
        holder.HQuantity.setText(model.getQuantity());
        holder.HSubtotal.setText(model.getSubtotal());


        Glide.with(holder.HimageName.getContext())
                .load(model.getImageurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark_normal)
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.HimageName);
    }

    @NonNull
    @Override
    public myViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history,parent,false);
        return new myViewHolder1(view);
    }

    class myViewHolder1 extends RecyclerView.ViewHolder{

        ImageView HimageName;
        TextView HName,HPrice,HQuantity,HSubtotal;

        public myViewHolder1(@NonNull View itemView) {
            super(itemView);

            HimageName = (ImageView) itemView.findViewById(R.id.HimageName);
            HName = (TextView) itemView.findViewById(R.id.HName);
            HPrice = (TextView) itemView.findViewById(R.id.HPrice);
            HQuantity = (TextView) itemView.findViewById(R.id.HQuantity);
            HSubtotal = (TextView) itemView.findViewById(R.id.HSubtotal);

        }
    }

}
