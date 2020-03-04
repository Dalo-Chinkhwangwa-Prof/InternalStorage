package com.bb.birthdayapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bb.birthdayapp.R;
import com.bb.birthdayapp.model.Birthday;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBirthdayActivity extends AppCompatActivity {

    public static final String BIRTHDAY_KEY = "birthday.key";

    @BindView(R.id.gender_imageview)
    ImageView genderImageView;

    @BindView(R.id.name_edittext)
    EditText nameEditText;

    @BindView(R.id.birthdate_edittext)
    EditText birthdateEditText;

    @BindView(R.id.phone_number_edittext)
    EditText phoneNumberEditText;


    @BindView(R.id.gender_select_radio_group)
    RadioGroup genderRadioGroup;

    private String gender = "F";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);
        ButterKnife.bind(this);


        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_radio_button:
                        gender = "M";
                        genderImageView.setImageDrawable(getDrawable(R.drawable.ic_male));
                        break;
                    case R.id.female_radio_button:
                        gender = "F";
                        genderImageView.setImageDrawable(getDrawable(R.drawable.ic_female));
                        break;
                }
            }
        });
    }


    @OnClick(R.id.save_birthday_button)
    public void saveBirthday(View view) {

        if (checkFields()) {
            String name = nameEditText.getText().toString().trim();
            String date = birthdateEditText.getText().toString().trim();
            String phone = phoneNumberEditText.getText().toString().trim();

            Birthday birthday = new Birthday(name, gender, phone, date);

            Intent birthdayIntent = new Intent();
            birthdayIntent.putExtra(BIRTHDAY_KEY, birthday);

            setResult(MainActivity.REQUEST_CODE, birthdayIntent);
            finish();
        }
    }

    private boolean checkFields() {

        if (phoneNumberEditText.getText().toString().trim().length() == 0 ||
                birthdateEditText.getText().toString().trim().length() == 0 ||
                nameEditText.getText().toString().trim().length() == 0
        ) {
            Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;

    }

}
