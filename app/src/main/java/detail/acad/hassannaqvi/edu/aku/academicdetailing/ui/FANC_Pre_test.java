package detail.acad.hassannaqvi.edu.aku.academicdetailing.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import detail.acad.hassannaqvi.edu.aku.academicdetailing.JSON.GeneratorClass;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.R;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.DatabaseHelper;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.databinding.ActivityFancPreTestBinding;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.util.Data;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.validation.validatorClass;

import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.isComplete;
import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.slides;
import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.type;

public class FANC_Pre_test extends AppCompatActivity {

    ActivityFancPreTestBinding bi;
    String currentDateTime = new SimpleDateFormat(" dd/MM/yyyy HH:mm:ss").format(new Date().getTime());
    private static final String TAG = "FANC_Pre_test";
    String[] ans = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_fanc__pre_test);
        bi.setCallback(this);

        if (!isComplete) {
            type = getIntent().getStringExtra("type");
            slides = getIntent().getIntArrayExtra("slides");
            if (type.equals("pre")) {
                MainApp.fc.setPreTestStartTime(MainApp.getCurrentTime());
            } else {
                MainApp.fc.setPostTestStartTime(MainApp.getCurrentTime());
            }
            bi.btnOk.setVisibility(View.GONE);
            bi.btnContinue.setVisibility(View.VISIBLE);

        } else {
            GeneratorClass.comparingResult(bi.fldGrpPreFanc);
            bi.btnOk.setVisibility(View.VISIBLE);
            bi.btnContinue.setVisibility(View.GONE);

        }

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
                    if (type.equals("pre")) {
                        if (MainApp.isSlideStart) {
                            startActivity(new Intent(this, FANC_Pre_test.class));
                            isComplete = true;
                            finish();
                        } else {
                            Toast.makeText(this, "Training Completed", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        MainApp.endActivity(this, "Are You Sure You want to Continue?", true);
                    }

                } else {
                    Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void BtnOk() {
        if (type.equals("pre")) {
            if (MainApp.isSlideStart) {
                startActivity(new Intent(this, ViewPagerActivity.class).putExtra("slides", slides));
                finish();
            } else {
                Toast.makeText(this, "Training Completed", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            MainApp.endActivity(this, "Are You Sure You want to Continue?", true);
        }
    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);
        int count;
        if (type.equals("pre")) {
            count = db.updatePreTest();
        } else {
            count = db.updatePostTest();
        }
        if (count == 1) {
            return true;
        } else {
            Toast.makeText(this, "Error in update DB", Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    private void SaveDraft() {

        if (type.equals("pre")) {
            MainApp.fc.setPreTestEndTime(MainApp.getCurrentTime());
            JSONObject json = GeneratorClass.getContainerJSON(bi.fldGrpPreFanc, true, type);
            MainApp.fc.setPre_test(String.valueOf(json));
            Data.fanc_ans = GeneratorClass.getAnswers(bi.fldGrpPreFanc, true);


        } else {
            MainApp.fc.setPostTestEndTime(MainApp.getCurrentTime());
            JSONObject json = GeneratorClass.getContainerJSON(bi.fldGrpPreFanc, true, type);
            MainApp.fc.setPost_test(String.valueOf(json));
        }
    }

    private boolean formValidation() {

        if (!validatorClass.EmptyRadioButton(this, bi.fanc01, bi.fanc01a, getString(R.string.fanc_01))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fanc02, bi.fanc02a, getString(R.string.fanc_02))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fanc03, bi.fanc03a, getString(R.string.fanc_03))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fanc04, bi.fanc04a, getString(R.string.fanc_04))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.fanc05, bi.fanc05a, getString(R.string.fanc_05))) {
            return false;
        }
        return validatorClass.EmptyCardCheckBox(this, bi.fanc06, bi.fanc06a, getString(R.string.fanc_06));
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
