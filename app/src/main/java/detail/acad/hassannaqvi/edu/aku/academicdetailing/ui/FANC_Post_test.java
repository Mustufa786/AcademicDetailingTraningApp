package detail.acad.hassannaqvi.edu.aku.academicdetailing.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import detail.acad.hassannaqvi.edu.aku.academicdetailing.JSON.GeneratorClass;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.R;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.databinding.ActivityFancPostTestBinding;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.validation.validatorClass;

public class FANC_Post_test extends AppCompatActivity {

    ActivityFancPostTestBinding bi;
    String currentDateTime = new SimpleDateFormat(" dd/MM/yyyy HH:mm:ss").format(new Date().getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_fanc__post_test);
        bi.setCallback(this);

        MainApp.fc.setPostTestStartTime(currentDateTime);
        if (MainApp.isSlideStart) {
            bi.btnContinue.setText("Start Training");
        } else {
            bi.btnContinue.setText("Finish Training");
        }
    }


    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
                if (UpdateDB()) {
                    if (MainApp.isSlideStart) {
                        startActivity(new Intent(this, ViewPagerActivity.class).putExtra("slides", getIntent().getIntArrayExtra("slides")));
                        finish();
                    } else {
                        Toast.makeText(this, "Training Completed", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean UpdateDB() {

        return true;
    }

    private void SaveDraft() throws JSONException {

        String currentDateTime = new SimpleDateFormat(" dd/MM/yyyy HH:mm:ss").format(new Date().getTime());
        MainApp.fc.setPostTestEndTime(currentDateTime);
        JSONObject sVb = GeneratorClass.getContainerJSON(bi.fldGrpPostFanc, true);
        MainApp.fc.setPost_test(String.valueOf(sVb));
    }

    private boolean formValidation() {

        if (!validatorClass.EmptyRadioButton(this, bi.fancpost01, bi.fancpost01a, getString(R.string.fanc_01))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fancpost02, bi.fancpost02a, getString(R.string.fanc_02))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fancpost03, bi.fancpost03a, getString(R.string.fanc_03))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fancpost04, bi.fancpost04a, getString(R.string.fanc_04))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fancpost05, bi.fancpost05a, getString(R.string.fanc_05))) {
            return false;
        }if (!validatorClass.EmptyCardCheckBox(this, bi.fanc_06,bi.fancpost06, getString(R.string.fanc_06))) {
            return false;
        }
        return true;
    }

    public void BtnEnd() {


//        try {
//            SaveDraft();
//            if (UpdateDB()) {
////                MainApp.endActivity(this, this, EndingActivity.class, false, fc_4_5);
//            } else {
//                Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}