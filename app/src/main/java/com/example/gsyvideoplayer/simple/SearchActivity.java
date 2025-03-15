// SearchActivity.java
package com.example.gsyvideoplayer.simple;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsyvideoplayer.R;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearch = findViewById(R.id.editTextSearch);
    }

    public void onSearch(View view) {
        String query = editTextSearch.getText().toString();
        Toast.makeText(this, "搜索: " + query, Toast.LENGTH_SHORT).show();
        // 在这里可以执行你的搜索逻辑
    }
}
