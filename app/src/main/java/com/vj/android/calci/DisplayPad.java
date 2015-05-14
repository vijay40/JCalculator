package com.vj.android.calci;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

public class DisplayPad extends EditText {
    private final int CUT = 0;
    private final int COPY = 1;
    private final int PASTE = 2;
    private String[] menuItems;


    public DisplayPad(Context context) {
        super(context);
        setUp();
    }

    public DisplayPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public DisplayPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    public void setUp() {
//        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        remove under bar from EditText
        if (Build.VERSION.SDK_INT < 16)
            setBackgroundDrawable(null);
        else
            setBackground(null);

//        disable spell check
        setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        setCustomSelectionActionModeCallback(new NoTextSelection());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        View parentScrollView = (View) getParent();

        if (parentScrollView != null) {
            if (parentScrollView instanceof HorizontalScrollView) {
                widthMeasureSpec = parentScrollView.getMeasuredWidth();
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);

        MenuHandler handler = new MenuHandler();
        String currentText = getText().toString();

        if (menuItems == null) {
            menuItems = new String[3];
            Resources resources = getResources();
            menuItems[0] = resources.getString(R.string.cut);
            menuItems[1] = resources.getString(R.string.copy);
            menuItems[2] = resources.getString(R.string.paste);

        }

        for (int i = 0; i < menuItems.length; i++)
            menu.add(Menu.NONE, i, i, menuItems[i]).setOnMenuItemClickListener(handler);

        if (currentText.length() == 0) {
            menu.getItem(CUT).setVisible(false);
            menu.getItem(COPY).setVisible(false);
        }

        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip == null || clip.getItemCount() == 0)
            menu.getItem(PASTE).setVisible(false);
    }

    private void Cut() {
        String text = getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
        ((MainActivity) getContext()).performClear();
    }

    private void Copy() {
        String text = getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
        String toastText = getResources().getString(R.string.text_copied);
        Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
    }

    private void Paste() {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String text = item.getText().toString();
            String currentText = getText().toString();
            int position = getSelectionStart();
            Utility.EditEntryText(text, position);
            (new Utility((Activity) getContext())).setDisplayText(MainActivity.entryText, position + text.length());
        }
    }

    private class MenuHandler implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String function = (String) item.getTitle();
            if (function.equals("Cut")) {
                Cut();
                return true;
            } else if (function.equals("Copy")) {
                Copy();
                return true;
            } else if (function.equals("Paste")) {
                Paste();
                return true;
            }
            return false;
        }
    }
}

class NoTextSelection implements ActionMode.Callback {

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}