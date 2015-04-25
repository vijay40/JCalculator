package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vj.android.calci.AdvancePad;
import com.vj.android.calci.BasicPad;
import com.vj.android.calci.HexPad;

/**
 * Created by I310588 on 3/22/2015.
 * Tab view adapter
 */
public class TabView extends FragmentPagerAdapter {
    public TabView(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new BasicPad();
        } else if (i == 1) {
            return new AdvancePad();
        } else {
            return new HexPad();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
