package net.jnm.selina.billsplitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ryana on 3/4/2016.
 */
public class MealCheckAdapter extends RecyclerView.Adapter<MealCheckAdapter.MealCheckHolder> {
    private MealAdapter ma;
    private EditPersonActivity.OnClickListener listener;
    private int[] counts;
    private boolean[] status;

    public MealCheckAdapter(Person p,MealAdapter ma, EditPersonActivity.OnClickListener listener) {
        this.ma = ma;
        this.listener = listener;
        counts = new int[ma.getItemCount()];
        status = new boolean[ma.getItemCount()];
        for(int i = 0; i < ma.getItemCount(); i++) {
            counts[i] = ma.get(i).getCount();
        }
    }

    public void setMeals(ArrayList<Meal> meals) {
        for(int i = 0; i < ma.getItemCount(); i++) {
            status[i] = false;
            for(Meal m : meals) {
                if (ma.get(i) == m) {
                    status[i] = true;
                    break;
                }
            }
            notifyItemChanged(i);
        }
    }

    @Override
    public MealCheckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_check,null);

        return new MealCheckHolder(v);
    }

    @Override
    public void onBindViewHolder(MealCheckHolder holder, int position) {
        holder.setMeal(ma.get(position),counts[position],status[position]);
    }

    @Override
    public int getItemCount() {
        return ma.getItemCount();
    }

    public double getTotal() {
        double total = 0;
        for(int i = 0; i < status.length; i++ ) {
            if( status[i] ) {
                total += ma.get(i).getTotalPrice() / counts[i];
            }
        }
        return total;
    }

    public class MealCheckHolder extends RecyclerView.ViewHolder {
        private Meal m;
        private CheckBox mealCheckBox;
        private TextView priceLabel;
        private DecimalFormat df;

        public MealCheckHolder(View itemView) {
            super(itemView);
            df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            mealCheckBox = (CheckBox)itemView.findViewById(R.id.mealCheckBox);
            priceLabel = (TextView)itemView.findViewById(R.id.priceLabel);
            mealCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mealCheckBox.isChecked()) {
                        counts[getAdapterPosition()]++;
                        status[getAdapterPosition()] = true;
                        priceLabel.setText("Php " + df.format(m.getTotalPrice()
                                            / counts[getAdapterPosition()]));
                    } else {
                        counts[getAdapterPosition()]--;
                        status[getAdapterPosition()] = false;
                        priceLabel.setText("Php 0.00");
                    }
                    listener.onClick(m,mealCheckBox.isChecked());
                }
            });
        }

        public void setMeal(Meal m, int count, boolean selected) {
            this.m = m;
            mealCheckBox.setText(m.getName() + " x " + m.getQuantity());
            mealCheckBox.setChecked(selected);
            if( selected ) {
               priceLabel.setText("Php " + df.format(m.getTotalPrice() / count));
            } else {
                priceLabel.setText("Php 0.00");
            }
        }
    }
}
