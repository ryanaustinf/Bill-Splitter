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
 * Created by ryana on 3/3/2016.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {
    private List<Person> persons;
    private MainActivity.OnClickListener listener;

    public PersonAdapter(MainActivity.OnClickListener onClickListener) {
        this.persons = new ArrayList<Person>();
        listener = onClickListener;
    }

    @Override
    public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_view,null);
        return new PersonHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonHolder holder, int position) {
        holder.setPerson(persons.get(position));
    }

    public Person[] getPersonsFor(Meal m) {
        ArrayList<Person> ps = new ArrayList<Person>();
        for(Person p: persons) {
            if( p.hasMeal(m.getName())) {
                ps.add(p);
            }
        }
        return ps.toArray(new Person[0]);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(String name) {
        for(int i = 0; i < persons.size(); i++ ) {
            if( persons.get(i).getName().equals(name) ) {
                return;
            }
        }
        persons.add(new Person(name));
        notifyItemInserted(persons.size() - 1);
    }

    public void updatePerson(String name, String newName, double cash) {
        for(int i = 0; i < persons.size(); i++ ) {
            if( persons.get(i).getName().equals(name) ) {
                persons.get(i).setName(newName);
                persons.get(i).setCashTendered(cash);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void deletePerson(String name) {
        for(int i = 0; i < persons.size(); i++ ) {
            if( persons.get(i).getName().equals(name) ) {
                persons.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public Person get(int position) {
        return persons.get(position);
    }

    public void updateGUI() {
        for(int i = 0; i < persons.size(); i++ ) {
            notifyItemChanged(i);
        }
    }

    public class PersonHolder extends RecyclerView.ViewHolder {
        private TextView nameLabel;
        private TextView cashLabel;
        private TextView tenderLabel;
        private TextView changeLabel;
        private LinearLayout personPanel;
        private Person p;

        public PersonHolder(View itemView) {
            super(itemView);

            nameLabel = (TextView)itemView.findViewById(R.id.nameLabel);
            cashLabel = (TextView)itemView.findViewById(R.id.cashLabel);
            tenderLabel = (TextView)itemView.findViewById(R.id.tenderLabel);
            changeLabel = (TextView)itemView.findViewById(R.id.changeLabel);
            personPanel = (LinearLayout)itemView.findViewById(R.id.personPanel);

            personPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click(p);
                }
            });
        }

        public String formatPeso(double value) {
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            return "Php " + df.format(value);
        }

        public void setPerson(Person p) {
            this.p = p;
            nameLabel.setText(p.getName());
            cashLabel.setText(formatPeso(p.getPayment()));
            tenderLabel.setText(formatPeso(p.getCashTendered()));
            changeLabel.setText(formatPeso(p.getChange()));
        }
    }
}
