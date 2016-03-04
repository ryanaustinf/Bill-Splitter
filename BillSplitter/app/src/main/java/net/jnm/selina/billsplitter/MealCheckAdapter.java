package net.jnm.selina.billsplitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

/**
 * Created by ryana on 3/4/2016.
 */
public class MealCheckAdapter extends RecyclerView.Adapter<MealCheckAdapter.MealCheckHolder> {
    private MealAdapter ma;
    private EditPersonActivity.OnClickListener listener;
    private boolean[] status;

    public MealCheckAdapter(Person p,MealAdapter ma, EditPersonActivity.OnClickListener listener) {
        this.ma = ma;
        this.listener = listener;
        status = new boolean[ma.getItemCount()];
        for(int i = 0; i < ma.getItemCount(); i++) {
            status[i] = p.hasMeal(ma.get(i).getName());
        }
    }
    @Override
    public MealCheckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_check,null);

        return new MealCheckHolder(v);
    }

    @Override
    public void onBindViewHolder(MealCheckHolder holder, int position) {
        holder.setMeal(ma.get(position),status[position]);
    }

    @Override
    public int getItemCount() {
        return ma.getItemCount();
    }

    public class MealCheckHolder extends RecyclerView.ViewHolder {
        private Meal m;
        private CheckBox mealCheckBox;

        public MealCheckHolder(View itemView) {
            super(itemView);
            mealCheckBox = (CheckBox)itemView.findViewById(R.id.mealCheckBox);
            mealCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(m,mealCheckBox.isChecked());
                }
            });
        }

        public void setMeal(Meal m, boolean selected) {
            this.m = m;
            mealCheckBox.setText(m.getName() + " x " + m.getQuantity());
            mealCheckBox.setChecked(selected);
        }
    }
}
