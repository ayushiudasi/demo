package com.example.calendar_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomCalendarView extends LinearLayout {
    ImageButton NextButton,PreviousButton;
    TextView CurrentDate;
    GridView gridview;
    private static final int MAX_CALENDAR_DAYS=42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    @SuppressLint("NewApi")
    SimpleDateFormat dateformat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    @SuppressLint("NewApi")
    SimpleDateFormat monthformat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    @SuppressLint("NewApi")
    SimpleDateFormat yearformat = new SimpleDateFormat("yyyy",Locale.ENGLISH);



    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();


    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitializeLayout();
        SetUpCalendar();

        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalendar();

            }
        });
        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                SetUpCalendar();
            }
        });

    }
    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout,this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate = view.findViewById(R.id.current_Date);
        gridview = view.findViewById(R.id.gridview);

    }
    private void SetUpCalendar(){
        @SuppressLint({"NewApi", "LocalSuppress"})
        String currentDate = dateformat.format(calendar.getTime());
        CurrentDate.setText(currentDate);

    }
}
