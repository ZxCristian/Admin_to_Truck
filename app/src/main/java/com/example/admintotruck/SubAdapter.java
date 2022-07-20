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

public class SubAdapter extends FirebaseRecyclerAdapter<Ordered,SubAdapter.myViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SubAdapter(@NonNull FirebaseRecyclerOptions<Ordered> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Ordered model) {
        holder.CName.setText(model.getName());
        holder.CPrice.setText(model.getPrice());
        holder.CQuantity.setText(model.getQuantity());
        holder.CSubtotal.setText(model.getSubtotal());


        Glide.with(holder.CimageName.getContext())
                .load(model.getImageurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark_normal)
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.CimageName);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmation,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView CimageName;
        TextView CName,CPrice,CQuantity,CSubtotal;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            CimageName = (ImageView) itemView.findViewById(R.id.CimageName);
            CName = (TextView) itemView.findViewById(R.id.CName);
            CPrice = (TextView) itemView.findViewById(R.id.CPrice);
            CQuantity = (TextView) itemView.findViewById(R.id.CQuantity);
            CSubtotal = (TextView) itemView.findViewById(R.id.CSubtotal);

        }
    }

}
