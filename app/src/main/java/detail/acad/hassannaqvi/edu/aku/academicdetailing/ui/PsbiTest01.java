package detail.acad.hassannaqvi.edu.aku.academicdetailing.ui;

import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.isComplete;
import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.post_result;
import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.pre_result;
import static detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp.type;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.json.JSONObject;

import detail.acad.hassannaqvi.edu.aku.academicdetailing.JSON.GeneratorClass;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.R;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.CONSTANTS;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.DatabaseHelper;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.core.MainApp;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.databinding.ActivityPsbiTest01Binding;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.util.Data;
import detail.acad.hassannaqvi.edu.aku.academicdetailing.validation.validatorClass;

public class PsbiTest01 extends AppCompatActivity implements RadioButton.OnCheckedChangeListener {

    ActivityPsbiTest01Binding bi;
    Data.SubMenu subMenuDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_psbi_test01);
        bi.setCallback(this);

        events_call();
        setupViews();

    }

    private void setupViews() {

        type = getIntent().getStringExtra(CONSTANTS.URI_FORM_TYPE);
        subMenuDT = (Data.SubMenu) getIntent().getSerializableExtra(CONSTANTS.URI_SUBMENU_DT);

        this.setTitle(subMenuDT.getName());
        if (type.equals("pre") && !isComplete) {
            bi.heading.setText("PRETEST");
//            slides = getIntent().getIntArrayExtra("slides");
//            Data.correctAnswers = getIntent().getStringArrayListExtra("ans");
            MainApp.forms.setPreTestStartTime(MainApp.getCurrentTime());
            bi.btnOk.setVisibility(View.GONE);
            bi.btnContinue.setVisibility(View.VISIBLE);
        } else if (type.equals("pre") && isComplete) {
            bi.heading.setText("PRETEST RESULT");
            GeneratorClass.comparingResult(bi.llPsbiTestA, true, subMenuDT.getAnswers());
            pre_result = GeneratorClass.getResults("pre",subMenuDT.getAnswers());
            bi.btnOk.setVisibility(View.VISIBLE);
            bi.btnOk.setText("Start Training");
            bi.btnContinue.setVisibility(View.GONE);
        } else if (type.equals("post") && !isComplete) {
            bi.heading.setText("POST TEST");
            MainApp.forms.setPostTestStartTime(MainApp.getCurrentTime());
            bi.btnOk.setVisibility(View.GONE);
            bi.btnContinue.setVisibility(View.VISIBLE);
        } else if (type.equals("post") && isComplete) {
            bi.heading.setText(" POST TEST & PRETEST RESULT");
            GeneratorClass.comparingPostTestAndPretestResult(bi.llPsbiTestA, true, subMenuDT.getAnswers());
            post_result = GeneratorClass.getResults("post",subMenuDT.getAnswers());
            bi.btnOk.setVisibility(View.VISIBLE);
            bi.btnOk.setText("Finish Training");
            bi.btnContinue.setVisibility(View.GONE);
        }


    }

    public void btnOk() {
        if (type.equals("pre")) {
            MainApp.showDialog(this, getString(R.string.readyForTrain), "pre", null, subMenuDT);
        } else {
            MainApp.showDialogeWithResult(this, post_result, subMenuDT);
//            MainApp.showDialog(this, getString(R.string.areYouSure), "end", true, subMenuDT);
        }
    }


    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
                if (UpdateDB()) {
                    if (type.equals("pre")) {
                        if (MainApp.isSlideStart) {
                            startActivity(new Intent(this, PsbiTest01.class)
                                    .putExtra(CONSTANTS.URI_FORM_TYPE, type)
                                    .putExtra(CONSTANTS.URI_SUBMENU_DT, subMenuDT)
                            );
                            isComplete = true;
                            finish();
                        } else {
                            Toast.makeText(this, "Training Completed", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else if (type.equals("post")) {
                        startActivity(new Intent(this, PsbiTest01.class)
                                .putExtra(CONSTANTS.URI_FORM_TYPE, type)
                                .putExtra(CONSTANTS.URI_SUBMENU_DT, subMenuDT)
                        );
                        isComplete = true;
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        //PsbiTest01-Q1
        if (compoundButton.getId() == R.id.PsbiTestA01a
                || compoundButton.getId() == R.id.PsbiTestA01b
                || compoundButton.getId() == R.id.PsbiTestA01c
                || compoundButton.getId() == R.id.PsbiTestA01d) {

            if (bi.PsbiTestA01a.isChecked()) {
                bi.tvPsbiTestA01.clearComposingText();
                String styledText = "Skin pustules among young infants are the sign of <font color='yellow'><b><i>Jaundice</i></b></font> .";
                bi.tvPsbiTestA01.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
            } else if (bi.PsbiTestA01b.isChecked()) {
                bi.tvPsbiTestA01.clearComposingText();
                String styledText = "Skin pustules among young infants are the sign of <font color='yellow'><b><i>Local Bacterial Infection</i></b></font> .";
                bi.tvPsbiTestA01.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
            } else if (bi.PsbiTestA01c.isChecked()) {
                bi.tvPsbiTestA01.clearComposingText();
                String styledText = "Skin pustules among young infants are the sign of <font color='yellow'><b><i>Thrush</i></b></font> .";
                bi.tvPsbiTestA01.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
            } else if (bi.PsbiTestA01d.isChecked()) {
                bi.tvPsbiTestA01.clearComposingText();
                String styledText = "Skin pustules among young infants are the sign of <font color='yellow'><b><i>Diarrhea</i></b></font> .";
                bi.tvPsbiTestA01.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
            }
        }

    }


    void events_call() {

        bi.PsbiTestA01a.setOnCheckedChangeListener(this);
        bi.PsbiTestA01b.setOnCheckedChangeListener(this);
        bi.PsbiTestA01c.setOnCheckedChangeListener(this);
        bi.PsbiTestA01d.setOnCheckedChangeListener(this);
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
            MainApp.forms.setPreTestEndTime(MainApp.getCurrentTime());
            JSONObject json = GeneratorClass.getContainerJSON(bi.llPsbiTestA, true, type);
            MainApp.forms.setPre_test(String.valueOf(json));
            Data.pretestAnswers = GeneratorClass.getAnswers(bi.llPsbiTestA, true);
        } else {
            MainApp.forms.setPostTestEndTime(MainApp.getCurrentTime());
            JSONObject json = GeneratorClass.getContainerJSON(bi.llPsbiTestA, true, type);
            MainApp.forms.setPost_test(String.valueOf(json));
            Data.posttestAnswers = GeneratorClass.getAnswers(bi.llPsbiTestA, true);
        }

    }


    private boolean formValidation() {

        return validatorClass.EmptyCheckingContainer(this, bi.llPsbiTestA);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }


}
