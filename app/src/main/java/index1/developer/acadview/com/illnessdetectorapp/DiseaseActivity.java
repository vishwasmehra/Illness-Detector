package index1.developer.acadview.com.illnessdetectorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DiseaseActivity extends AppCompatActivity {

    private List<String> listheader;
    private ExpandableListAdapter adapter;
    private ExpandableListView list;
    DataBaseHelper db;
    private LinkedHashMap<String,List<String>> listHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        list=(ExpandableListView)findViewById(R.id.exp_list);
        db = new DataBaseHelper(this);
        listheader = new ArrayList<>();
        initData();
        adapter = new ExpandableListAdapter(this,listheader,listHashMap);
        list.setAdapter(adapter);
    }

    public void initData()
    {
        db = new DataBaseHelper(this);
        try {

            db.createDatabase();
            db.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        int i, sym;
        int nm;
        listHashMap=new LinkedHashMap<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT Disease FROM Disease_table ORDER BY Disease", null);

         i = cursor.getColumnIndex("Disease");
         listheader = new ArrayList<>();
         while (cursor.moveToNext()) {
             String name = cursor.getString(i);
             listheader.add(name);
         }
         cursor.close();
        ArrayList<String> chic = new ArrayList<>();
        ArrayList<String> jaun = new ArrayList<>();
        ArrayList<String> mal = new ArrayList<>();
        ArrayList<String> ty = new ArrayList<>();
        Cursor cursor1=database.rawQuery("SELECT symptoms, Disease FROM Symptoms_table a INNER JOIN " +
                "Map b on a.Sym_id=b.sym_id INNER JOIN Disease_table c ON b.dis_id=c.Dis_id ORDER BY Disease", null);
            sym=cursor1.getColumnIndex("symptoms");
            nm=cursor1.getColumnIndex("Disease");
         while(cursor1.moveToNext())
         {
            if(cursor1.getString(nm).equals(listheader.get(0)))
            {
                chic.add(cursor1.getString(sym));
            } else if (cursor1.getString(nm).equals(listheader.get(1)))
            {
                jaun.add(cursor1.getString(sym));
            }
            else if(cursor1.getString(nm).equals(listheader.get(2)))
            {
                mal.add(cursor1.getString(sym));
            }
            else if(cursor1.getString(nm).equals(listheader.get(3)))
            {
                ty.add(cursor1.getString(sym));
            }
         }
        cursor1.close();
        listHashMap.put(listheader.get(0),chic);
        listHashMap.put(listheader.get(1),jaun);
        listHashMap.put(listheader.get(2),mal);
        listHashMap.put(listheader.get(3),ty);

    }
}
