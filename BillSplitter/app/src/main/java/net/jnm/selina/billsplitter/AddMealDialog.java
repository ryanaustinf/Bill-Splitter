package net.jnm.selina.billsplitter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ryana on 3/3/2016.
 */
public class AddMealDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.add_meal_dialog,null);

        final EditText nameField = (EditText)v.findViewById(R.id.nameField);
        final EditText unitPriceField = (EditText)v.findViewById(R.id.unitPriceField);
        final EditText quantityField = (EditText)v.findViewById(R.id.quantityField);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Meal")
                .setView(v)
                .setPositiveButton("Add Meal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameField.getText().toString();
                        try {
                            double unitPrice
                                    = Double.parseDouble(unitPriceField.getText().toString());
                            if( unitPrice > 0 ) {
                                try {
                                    int quantity = Integer.parseInt(quantityField.getText().toString());
                                    if (quantity <= 0) {
                                        Toast.makeText(getActivity(), "Quantity must be a positive integer."
                                                , Toast.LENGTH_LONG).show();
                                    } else {
                                        ((ManageMealsActivity)getActivity()).addMeal(name, unitPrice, quantity);
                                    }
                                } catch (NumberFormatException nfe) {
                                    Toast.makeText(getActivity(), "Quantity must be a positive integer."
                                            , Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(),"Unit Price must be a positive quantity."
                                        ,Toast.LENGTH_LONG).show();
                            }
                        } catch(NumberFormatException nfe) {
                            Toast.makeText(getActivity(),"Unit Price must be a positive quantity."
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}
