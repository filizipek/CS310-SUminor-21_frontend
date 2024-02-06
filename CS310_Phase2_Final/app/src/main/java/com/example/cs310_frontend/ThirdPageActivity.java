package com.example.cs310_frontend;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.concurrent.ExecutorService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs310_frontend.databinding.ThirdPageActivityBinding;

import java.util.List;

public class ThirdPageActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<OnlyList> data = (List<OnlyList>) msg.obj;

        }
    };

    Handler handler1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg2) {
            List<OnlyList> data = (List<OnlyList>) msg2.obj;
        }
    };

    Handler handler2 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg3) {
            List<OnlyList> data = (List<OnlyList>) msg3.obj;

            CourseAdapter adp3 = new CourseAdapter(ThirdPageActivity.this, data);
            binding.mkrec.setAdapter(adp3);
        }
    };

    ThirdPageActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ThirdPageActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mkrec.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String selectedMajor = intent.getStringExtra("selectedMajor");
        String selectedMinor = intent.getStringExtra("selectedMinor");

        Repo repo = new Repo(this);
        ExecutorService srv = WebExample.getExecutorService();
        repo.searchMajor(srv, handler, selectedMajor);
        repo.searchMinor(srv, handler1, selectedMinor);
        repo.getCommonCourses(srv, handler2, selectedMajor, selectedMinor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            ((Window) window).addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));
        }

        binding.imageButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(ThirdPageActivity.this, SelectProgramsActivity.class);

            intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            startActivity(intent2);
        });
    }
}
