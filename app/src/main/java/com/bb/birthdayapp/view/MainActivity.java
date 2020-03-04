package com.bb.birthdayapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bb.birthdayapp.R;
import com.bb.birthdayapp.adapter.BirthdayAdapter;
import com.bb.birthdayapp.model.Birthday;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BirthdayAdapter.BillionDollar {

    private String fileName = "Birthdays.txt";

    private String delimiter = ":";

    public static final int REQUEST_CODE = 707;

    private List<Birthday> birthdays = new ArrayList<>();

    @BindView(R.id.main_recyclerview)
    RecyclerView birthdayRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.add_birthday_button)
    public void addNewBirthday(View view) {
        Intent addBirthday = new Intent(this, AddBirthdayActivity.class);
        startActivityForResult(addBirthday, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            if (data != null) {

                Birthday birthday = data.getParcelableExtra(AddBirthdayActivity.BIRTHDAY_KEY);
                birthdays.add(birthday);
                try {
                    writeToInternalStorage(birthday);
                    readFromInternalStorage();
                } catch (IOException e) {
                    Log.d("TAG_X", e.getLocalizedMessage());
                    e.printStackTrace();
                }
                updateRecyclerView();

                Toast.makeText(this, birthday.getName(), Toast.LENGTH_LONG).show();
            }

        }
    }

    private void updateRecyclerView() {

        BirthdayAdapter adapter = new BirthdayAdapter(birthdays, this);

        birthdayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        birthdayRecyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG_X", "onDestroy");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("TAG_X", "onConfigChanged");
    }

    @Override
    public void getBirthday(Birthday birthday) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + birthday.getPhone()));
        startActivity(dialIntent);
    }


    private void writeToInternalStorage(Birthday birthday) throws IOException {

        FileOutputStream fileOS = openFileOutput(fileName, Context.MODE_APPEND);
        String birthdayText = "\n" + birthday.getName() + ":" + birthday.getGender() + ":" + birthday.getPhone() + ":" + birthday.getDate();

        Log.d("TAG_X", "Write started.");

        byte[] bytes = birthdayText.getBytes();
        fileOS.write(bytes);
        fileOS.close();
        Log.d("TAG_X", "Write complete.");
    }

    private void readFromInternalStorage() throws IOException {

        FileReader fileReader = new FileReader(getFilesDir().getPath() + "/" + fileName);
        BufferedReader reader = new BufferedReader(fileReader);

        String readIn = null;

        while ((readIn = reader.readLine()) != null) {

            Log.d("TAG_X", "Line from file : " + readIn);
        }

        reader.close();

    }
}
