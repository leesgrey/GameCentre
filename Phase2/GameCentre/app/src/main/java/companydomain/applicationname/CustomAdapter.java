package companydomain.applicationname;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    /**
     * A list of buttons.
     */
    private ArrayList<Button> mButtons;

    /**
     * The width and height of each button TODO: i dont really know what these do
     */
    private int mColumnWidth, mColumnHeight;

    /**
     *
     *
     * @param buttons dsfsfs
     * @param columnWidth sdfsd
     * @param columnHeight dsfdsfsd
     */
    CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    /**
     * Return the number of buttons.
     *
     * @return the number of buttons.
     */
    @Override
    public int getCount() {
        return mButtons.size();
    }

    /**
     * Return the button at the given position.
     *
     * @param position the button position to return.
     * @return the number of buttons.
     */
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    /**
     * Return the position of the position specified.
     * @param position the position to return
     * @return position of the position specified
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Return the current view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
