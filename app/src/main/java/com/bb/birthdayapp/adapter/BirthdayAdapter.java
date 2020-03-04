package com.bb.birthdayapp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.birthdayapp.R;
import com.bb.birthdayapp.model.Birthday;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder> {

    private List<Birthday> birthdayList;
    private BillionDollar billionDollar;

    public BirthdayAdapter(List<Birthday> birthdayList, BillionDollar billionDollar) {
        this.birthdayList = birthdayList;
        this.billionDollar = billionDollar;
    }

    public interface BillionDollar {
        void getBirthday(Birthday birthday);
    }

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.birthday_item_layout, parent, false);

        return new BirthdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayViewHolder holder, int position) {

        holder.itemView.setOnClickListener(view -> {
            billionDollar.getBirthday(birthdayList.get(position));
        });

        holder.date.setText(birthdayList.get(position).getDate());
        holder.nameTextView.setText(birthdayList.get(position).getName());

        Drawable drawable = null;

        switch (birthdayList.get(position).getGender()) {
            case "M":
                drawable = holder.itemView.getContext().getDrawable(R.drawable.ic_male);
                break;
            case "F":
                drawable = holder.itemView.getContext().getDrawable(R.drawable.ic_female);
                break;
        }

        Glide.with(holder.itemView.getContext())
                .load(drawable)
                .into(holder.genderImageView);
    }

    @Override
    public int getItemCount() {
        return birthdayList.size();
    }

    class BirthdayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gender_imageview)
        ImageView genderImageView;

        @BindView(R.id.name_textview)
        TextView nameTextView;

        @BindView(R.id.date_textview)
        TextView date;

        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
