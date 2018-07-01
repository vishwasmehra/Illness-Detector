package index1.developer.acadview.com.illnessdetectorapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Symptoms extends AppCompatActivity {

    private ArrayList<String> selecteditems;
    ListView ls;
    AlertDialog.Builder dialog;
    AlertDialog a;
    DataBaseHelper db;
    private ArrayList<String> symlist;
    private List<Set<String>> setList;
    private LinkedHashMap<String, Set<String>> hashMap;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        ls = (ListView) findViewById(R.id.sym_list);
        b = (Button) findViewById(R.id.buttonB);
        db = new DataBaseHelper(this);
        symlist = new ArrayList<>();
        selecteditems = new ArrayList<String>();
        setList = new ArrayList<Set<String>>();
        fetchall();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, symlist);
        ls.setAdapter(adapter);
        ls.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selecteditem = ((TextView) view).getText().toString();
                if (selecteditems.contains(selecteditem)) {
                    selecteditems.remove(selecteditem);
                } else {
                    selecteditems.add(selecteditem);
                }

            }
        });

            dialog = new AlertDialog.Builder(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator entries = hashMap.entrySet().iterator();
                while(entries.hasNext()) {
                    Map.Entry thisentry = (Map.Entry) entries.next();
                    for (int i = 0; i < selecteditems.size(); i++) {
                        if (String.valueOf(thisentry.getKey()).equals(selecteditems.get(i))) {
                            setList.add((Set<String>) thisentry.getValue());
                        }
                    }
                }
                final Set<String> interset = setList.get(0);
                for(int j=1 ;j < setList.size(); j++)
                {
                    interset.retainAll(setList.get(j));
                }
                ArrayList<String> list = new ArrayList<>();
                for (String nm:interset) {
                    list.add(nm);
                }
                dialog.setTitle(list.toString());
                dialog.setMessage("Based on your symptoms selected");
                a = dialog.create();
                a.show();
            }
        });
    }

    public void fetchall() {
        db = new DataBaseHelper(this);
        try {

            db.createDatabase();
            db.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        int i, j;
        int symnm;
        hashMap = new LinkedHashMap<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT symptoms FROM Symptoms_table ORDER BY symptoms", null);
        symnm = cursor.getColumnIndex("symptoms");
        symlist = new ArrayList<>();
        while (cursor.moveToNext()) {
            String nm = cursor.getString(symnm);
            symlist.add(nm);
        }
        cursor.close();
        Set<String> bp = new HashSet<String>();
        Set<String> cld = new HashSet<String>();
        Set<String> fev = new HashSet<String>();
        Set<String> heac = new HashSet<String>();
        Set<String> lomo = new HashSet<String>();
        Set<String> stoac = new HashSet<String>();
        Set<String> vo = new HashSet<String>();
        Set<String> wek = new HashSet<String>();
        Cursor cursor1 = database.rawQuery("SELECT symptoms, Disease FROM Symptoms_table a INNER JOIN " +
                "Map b on a.Sym_id=b.sym_id INNER JOIN Disease_table c ON b.dis_id=c.Dis_id ORDER BY symptoms", null);

        i = cursor.getColumnIndex("symptoms");
        j = cursor.getColumnIndex("Disease");
        while (cursor.moveToNext()) {
            if (cursor1.getString(i).equals(symlist.get(0))) {
                bp.add(cursor1.getString(j));
            } else if (cursor1.getString(i).equals(symlist.get(1))) {
                cld.add(cursor1.getString(j));
            } else if (cursor1.getString(i).equals(symlist.get(2))) {
                fev.add(cursor1.getString(j));
            } else if (cursor1.getString(i).equals(symlist.get(3))) {
                heac.add(cursor1.getString(j));
            } else if (cursor1.getString(i).equals(symlist.get(4))) {
                lomo.add(cursor1.getString(j));
            } else if (cursor1.getString(i).equals(symlist.get(5))) {
                stoac.add(cursor1.getString(j));
            } else if (cursor1.getString(i).equals(symlist.get(6))) {
                vo.add(cursor1.getString(j));
            } else if (cursor1.getString(i).equals(symlist.get(7))) {
                wek.add(cursor1.getString(j));
            }
        }
        cursor1.close();
        hashMap.put(symlist.get(0), bp);
        hashMap.put(symlist.get(1), cld);
        hashMap.put(symlist.get(2), fev);
        hashMap.put(symlist.get(3), heac);
        hashMap.put(symlist.get(4), lomo);
        hashMap.put(symlist.get(5), stoac);
        hashMap.put(symlist.get(6), vo);
        hashMap.put(symlist.get(7), wek);


    }
}

