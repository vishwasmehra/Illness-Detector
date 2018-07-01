package index1.developer.acadview.com.illnessdetectorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 22-Jun-18.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listheader;
    private HashMap<String,List<String>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listheader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listheader = listheader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listheader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listheader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listheader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listheader.get(i)).get(i1);  // i = List Group items , i1 = List Child Item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headertitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group,null);
        }
        TextView tx = (TextView)view.findViewById(R.id.listheader);
        tx.setText(headertitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childtext = (String)getChild(i, i1);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_childitem,null);
        }
        TextView textchild = (TextView)view.findViewById(R.id.listchild);
        textchild.setText(childtext);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
