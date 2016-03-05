package net.jnm.selina.billsplitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ManageMealsActivity extends AppCompatActivity {
    public static ManageMealsActivity instance;
    private MealAdapter ma;

    private Button backButton;
    private Button addButton;
    private RecyclerView mealView;
    private TextView totalPriceLabel;

    private Meal activeMeal;
    private PersonCheckAdapter pca;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_meals);
        instance = this;

        ma = MainActivity.instance.getMealAdapter();
        ma.setOnClickListener(new OnClickListener() {
            @Override
            public void click(Meal m) {
                //send intent to EditMEalActivity
                Log.i("ManageMealsActivity", "Edit " + m.getName());
                activeMeal = m;
                Intent editMeal = new Intent(getBaseContext(),EditMealActivity.class);
                startActivity(editMeal);
            }
        });

        backButton = (Button)findViewById(R.id.backButton);
        addButton = (Button)findViewById(R.id.addButton);
        mealView = (RecyclerView)findViewById(R.id.mealView);
        totalPriceLabel = (TextView)findViewById(R.id.totalPriceLabel);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMealDialog amd = new AddMealDialog();
                amd.show(getFragmentManager(), "");
            }
        });

        mealView.setAdapter(ma);
        mealView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        setTotal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ma.updateGUI();
    }

    public Meal getActive() {
        return activeMeal;
    }

    public void deleteActive() {
        ma.removeMeal(activeMeal.getName());
        MainActivity.instance.clearMeal(activeMeal);
    }

    public void addMeal(String name, double unitPrice, int quantity) {
        ma.addMeal(name, unitPrice, quantity);
        setTotal();
    }

    private void setTotal() {
        double total = 0;
        for(int i = 0; i < ma.getItemCount(); i++) {
            total += ma.get(i).getTotalPrice();
        }
        totalPriceLabel.setText("Php " + df.format(total));
    }

    public interface OnClickListener {
        public void click(Meal m);
    }
}
