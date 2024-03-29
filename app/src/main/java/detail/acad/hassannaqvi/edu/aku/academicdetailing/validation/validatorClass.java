package detail.acad.hassannaqvi.edu.aku.academicdetailing.validation;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.edittextpicker.aliazaz.textpicker.TextPicker;

import java.lang.reflect.Field;

import detail.acad.hassannaqvi.edu.aku.academicdetailing.R;

/**
 * Created by ali.azaz on 12/04/17.
 * modified by ramsha.seed on 7/8/2018
 */

public abstract class validatorClass {

    public static boolean EmptyTextBox(Context context, EditText txt, String msg) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            txt.setError("This data is Required! ");    // Set Error on last radio button
            txt.setFocusableInTouchMode(true);
            txt.requestFocus();
            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(txt.getId()) + ": This data is Required!");
            return false;
        } else {
            txt.setError(null);
            txt.clearFocus();
            return true;
        }

    }

    public static boolean EmptyCardCheckBox(Context context, CardView container, CheckBox cbx, String msg) {

        Boolean flag = false;
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) v).getChildCount(); j++) {
                    View view = ((LinearLayout) v).getChildAt(j);
                    if (view instanceof CheckBox) {
                        CheckBox cb = (CheckBox) view;
                        if (cb.isChecked()) {
                            flag = true;
                            break;
                        }
                    }

                }
            }
        }
        if (flag) {
            return true;
        } else {
            Toast.makeText(context, "ERROR(empty): " + msg, Toast.LENGTH_LONG).show();
            cbx.setError("This data is Required!");    // Set Error on last radio button

            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(cbx.getId()) + ": This data is Required!");
            return false;
        }
    }

    public static boolean EmptyTextBox(Context context, TextView txt, String msg) {
        if (TextUtils.isEmpty(txt.getText().toString())) {

            txt.setError("This data is Required! ");    // Set Error on last radio button
            txt.setFocusableInTouchMode(true);
            txt.requestFocus();
            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(txt.getId()) + ": This data is Required!");
            return false;
        } else {
            txt.setError(null);
            txt.clearFocus();
            return true;
        }

    }

    public static boolean RangeTextBox(Context context, EditText txt, int min, int max, String msg, String type) {

        if (Integer.valueOf(txt.getText().toString()) < min || Integer.valueOf(txt.getText().toString()) > max) {
            Toast.makeText(context, "ERROR(invalid): " + msg, Toast.LENGTH_SHORT).show();
            txt.setError("Range is " + min + " to " + max + type + " ... ");    // Set Error on last radio button
            txt.setFocusableInTouchMode(true);
            txt.requestFocus();
            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(txt.getId()) + ": Range is " + min + " to " + max + " times...  ");
            return false;
        } else {
            txt.setError(null);
            txt.clearFocus();
            return true;
        }
    }


    public static boolean RangeTextBox(Context context, EditText txt, double min, double max, String msg, String type) {

        if (Double.valueOf(txt.getText().toString()) < min || Double.valueOf(txt.getText().toString()) > max) {
            txt.setError("Range is " + min + " to " + max + type + " ... ");    // Set Error on last radio button
            txt.setFocusableInTouchMode(true);
            txt.requestFocus();
            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(txt.getId()) + ": Range is " + min + " to " + max + " times...  ");
            return false;
        } else {
            txt.setError(null);
            txt.clearFocus();
            return true;
        }
    }

    public static boolean EmptySpinner(Context context, Spinner spin, String msg) {
        if (spin.getSelectedItemPosition() == 0) {
            ((TextView) spin.getSelectedView()).setText("This Data is Required");
            ((TextView) spin.getSelectedView()).setTextColor(Color.RED);
            spin.setFocusableInTouchMode(true);
            spin.requestFocus();
            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(spin.getId()) + ": This data is Required!");
            return false;
        } else {
            ((TextView) spin.getSelectedView()).setError(null);
            return true;
        }
    }

    public static boolean EmptyRadioButton(Context context, RadioGroup rdGrp, RadioButton rdBtn, String msg) {
        if (rdGrp.getCheckedRadioButtonId() == -1) {
            rdBtn.setError("This data is Required!");    // Set Error on last radio button
            rdBtn.setFocusable(true);
            rdBtn.setFocusableInTouchMode(true);
            rdBtn.requestFocus();
            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(rdGrp.getId()) + ": This data is Required!");
            return false;
        } else {
            rdBtn.setError(null);
            rdBtn.clearFocus();
            return true;
        }
    }

    public static boolean EmptyRadioButton(Context context, RadioGroup rdGrp, RadioButton rdBtn, EditText txt, String msg) {
        if (rdGrp.getCheckedRadioButtonId() == -1) {
            rdBtn.setError("This data is Required!");    // Set Error on last radio button
            rdBtn.setFocusable(true);
            rdBtn.setFocusableInTouchMode(true);
            rdBtn.requestFocus();
            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(rdGrp.getId()) + ": This data is Required!");
            return false;
        } else {
            rdBtn.setError(null);
            rdBtn.clearFocus();
            if (rdBtn.isChecked()) {
                return EmptyTextBox(context, txt, msg);
            } else {
                txt.clearFocus();
                txt.setError(null);
                return true;
            }
        }
    }

    public static boolean EmptyCheckBox(Context context, LinearLayout container, CheckBox cbx, String msg) {

        Boolean flag = false;
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            return true;
        } else {
            cbx.setError("This data is Required!");    // Set Error on last radio button

            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(cbx.getId()) + ": This data is Required!");
            return false;
        }
    }

    public static boolean EmptyCheckBox(Context context, LinearLayout container, CheckBox cbx, EditText txt, String msg) {

        Boolean flag = false;
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            cbx.setError(null);
            //Changed According to J2ME Lint
            return !cbx.isChecked() || EmptyTextBox(context, txt, msg);
        } else {
            cbx.setError("This data is Required!");    // Set Error on last radio button

            Log.i(context.getClass().getName(), context.getResources().getResourceEntryName(cbx.getId()) + ": This data is Required!");
            return false;
        }
    }

    public static void setScrollViewFocus(ScrollView scrollView) {
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    public static boolean EmptyCheckingContainer(Context context, LinearLayout lv) {

        for (int i = 0; i < lv.getChildCount(); i++) {
            View view = lv.getChildAt(i);

            if (view.getVisibility() == View.GONE || !view.isEnabled())
                continue;

            if (view instanceof CardView) {
                for (int j = 0; j < ((CardView) view).getChildCount(); j++) {
                    View view1 = ((CardView) view).getChildAt(j);
                    if (view1 instanceof LinearLayout) {
                        if (!EmptyCheckingContainer(context, (LinearLayout) view1)) {
                            return false;
                        }
                    }
                }
            } else if (view instanceof RadioGroup) {

                View v = ((RadioGroup) view).getChildAt(0);
                if (v != null) {

                    String asNamed = getString(context, getIDComponent(view));

                    if (!EmptyRadioButton(context, (RadioGroup) view, (RadioButton) v, asNamed)) {
                        return false;
                    }
                }
            } else if (view instanceof Spinner) {
                if (!EmptySpinner(context, (Spinner) view, getString(context, getIDComponent(view)))) {
                    return false;
                }
            } else if (view instanceof EditText) {

                if (view instanceof TextPicker) {

                    if (((TextPicker) view).getType() == 1) {
                        if (!((TextPicker) view).isEmptyTextBox()) {
                            return false;
                        }
                    } else {
                        if (!((TextPicker) view).isRangePickerValidate()) {
                            return false;
                        }
                    }

                } else if (!EmptyTextBox(context, (EditText) view, getString(context, getIDComponent(view)))) {
                    return false;
                }
            } else if (view instanceof LinearLayout) {
                if (!EmptyCheckingContainer(context, (LinearLayout) view)) {
                    return false;
                }
            }

        }
        return true;
    }
    public static boolean EmptyCheckingContainerForButtons(Context context, LinearLayout lv) {


        for (int i = 0; i < lv.getChildCount(); i++) {
            View view = lv.getChildAt(i);

          /*  if (view.getVisibility() == View.GONE || !view.isEnabled())
                continue;*/
            if (view.getVisibility() == View.GONE )
                continue;

            if (view instanceof CardView) {
                for (int j = 0; j < ((CardView) view).getChildCount(); j++) {
                    View view1 = ((CardView) view).getChildAt(j);
                    if (view1 instanceof LinearLayout) {
                        if (!EmptyCheckingContainerForButtons(context, (LinearLayout) view1)) {
                            return false;
                        }
                    }
                }
            } else if (view instanceof EditText) {
                if (!EmptyTextBox(context, (EditText) view, getString(context, getIDComponent(view)))) {
                    return false;
                }
            }

        }
        return true;
    }


    public static String getIDComponent(View view) {
        String[] idName = (view).getResources().getResourceName((view).getId()).split("id/");

        return idName[1];
    }

    private static String getString(Context context, String idName) {

        Field[] fields = R.string.class.getFields();
        for (final Field field : fields) {

            if (field.getName().split("R$string.")[0].equals(idName)) {
                try {
                    int id = field.getInt(R.string.class); //id of string

                    return context.getString(id);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

}
