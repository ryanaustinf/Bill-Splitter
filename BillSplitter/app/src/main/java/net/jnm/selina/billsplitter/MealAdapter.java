package net.jnm.selina.billsplitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryana on 3/4/2016.
 */
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealHolder> {
    private List<Meal> meals;
    private ManageMealsActivity.OnClickListener listener;

    public MealAdapter() {
        meals = new ArrayList<Meal>();
    }

    public void setOnClickListener(ManageMealsActivity.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MealHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_view,null);

        return new MealHolder(v);
    }

    @Override
    public void onBindViewHolder(MealHolder holder, int position) {
        holder.setMeal(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void addMeal(String name, double price, int quantity) {
        for(int i = 0; i < meals.size();i++) {
            if( meals.get(i).getName().equals(name)) {
                return;
            }
        }
        meals.add(new Meal(name, quantity, price));
        notifyItemInserted(meals.size() - 1);
    }

    public void removeMeal(String name) {
        for(int i = 0; i < meals.size();i++) {
            if( meals.get(i).getName().equals(name)) {
                meals.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public Meal get(int index) {
        return meals.get(index);
    }

    public void updateGUI() {
        for(int i = 0; i < getItemCount(); i++) {
            notifyItemChanged(i);
        }
    }

    public class MealHolder extends RecyclerView.ViewHolder {
        private LinearLayout mealPanel;
        private TextView mealLabel;
        private TextView unitPriceLabel;
        private TextView quantityLabel;
        private TextView totalPriceLabel;
        private TextView peopleLabel;

        private Meal m;

        public MealHolder(View itemView) {
            super(itemView);
            mealPanel = (LinearLayout)itemView.findViewById(R.id.mealPanel);
            mealLabel = (TextView)itemView.findViewById(R.id.mealLabel);
            unitPriceLabel = (TextView)itemView.findViewById(R.id.unitPriceLabel);
            quantityLabel = (TextView)itemView.findViewById(R.id.quantityLabel);
            totalPriceLabel = (TextView)itemView.findViewById(R.id.totalPriceLabel);
            peopleLabel = (TextView)itemView.findViewById(R.id.peopleLabel);
            mealPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click(m);
                }
            });
        }

        public String formatPeso(double value) {
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            return "Php " + df.format(value);
        }

        public void setMeal(Meal m) {
            this.m = m;
            mealLabel.setText(m.getName());
            unitPriceLabel.setText(formatPeso(m.getPrice()));
            quantityLabel.setText(" x " + m.getQuantity());
            totalPriceLabel.setText(" = " + formatPeso(m.getTotalPrice()));
            String peopleStr = "";
            Person[] people = MainActivity.instance.getPersonsFor(m);
            for(int i = 0; i < people.length; i++) {
                if( i > 0 ) {
                    peopleStr += "\n";
                }
                peopleStr += people[i].getName();
            }
            peopleLabel.setText(peopleStr);
        }
    }
}
