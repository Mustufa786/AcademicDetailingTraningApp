package detail.acad.hassannaqvi.edu.aku.academicdetailing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends PagerAdapter {

    Context context;
    List<String> list = new ArrayList<>();

    public Adapter(Context context, List<String> mList) {

        this.context = context;
        this.list = mList;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        TextView txt = view.findViewById(R.id.text);
        container.addView(view, 0);
        txt.setText(list.get(position));
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        //See if object from instantiateItem is related to the given view
        //required by API
        return arg0==(View)arg1;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
        object=null;
    }
}
