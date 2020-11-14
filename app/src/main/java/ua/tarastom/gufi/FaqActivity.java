package ua.tarastom.gufi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.utils.FaqPagerAdapter;

public class FaqActivity extends AppCompatActivity {
    private int currentPage;
    private List<String[]> list_faq1_description_text;
    private String[] faq_text_header_detail;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faq_text_header_detail = getResources().getStringArray(R.array.faq_text_header_detail);

        String[] faq1_description_text = getResources().getStringArray(R.array.faq1_description_text);
        String[] faq2_description_text = getResources().getStringArray(R.array.faq2_description_text);
        String[] faq3_description_text = getResources().getStringArray(R.array.faq3_description_text);
        list_faq1_description_text = new ArrayList<>();
        list_faq1_description_text.add(faq1_description_text);
        list_faq1_description_text.add(faq2_description_text);
        list_faq1_description_text.add(faq3_description_text);

        createPages(currentPage);

    }

    private void createPages(int currentPage) {
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<>();
        ViewPager viewPager = new ViewPager(this);

        View page = inflater.inflate(R.layout.activity_faq, null);
        fill(page, 0, list_faq1_description_text);
        setRadioListener(page);
        pages.add(page);

        page = inflater.inflate(R.layout.activity_faq, null);
        fill(page, 1, list_faq1_description_text);
        setRadioListener(page);
        pages.add(page);

        page = inflater.inflate(R.layout.activity_faq, null);
        setRadioListener(page);
        fill(page, 2, list_faq1_description_text);

        buttonStart = page.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(view -> {
            Intent intent = new Intent(FaqActivity.this, ServiceCatalogActivity.class);
            startActivity(intent);
            finish();
        });

        pages.add(page);

        FaqPagerAdapter pagerAdapter = new FaqPagerAdapter(pages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(currentPage);

        setContentView(viewPager);
    }

    private void setRadioListener(View page) {
        final int[] pageNumber = new int[1];
        RadioGroup radio_group_faq = page.findViewById(R.id.radio_group_faq);
        radio_group_faq.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.radioButton1:
                    pageNumber[0] = 0;
                    break;
                case R.id.radioButton2:
                    pageNumber[0] = 1;
                    break;
                case R.id.radioButton3:
                    pageNumber[0] = 2;
                    break;
            }
            fill(page, pageNumber[0], list_faq1_description_text);
        });
    }


    private void fill(View page, int numberPage, List<String[]> list_faq1_description_text) {
        TextView textViewDescriptionFaq1 = page.findViewById(R.id.textViewDescriptionFaq1);
        TextView textViewDescriptionFaq2 = page.findViewById(R.id.textViewDescriptionFaq2);
        TextView textViewDescriptionFaq3 = page.findViewById(R.id.textViewDescriptionFaq3);
        TextView textViewDescriptionFaq4 = page.findViewById(R.id.textViewDescriptionFaq4);
        TextView textViewDescriptionFaq5 = page.findViewById(R.id.textViewDescriptionFaq5);
        TextView[] textViews = new TextView[]{textViewDescriptionFaq1, textViewDescriptionFaq2,
                textViewDescriptionFaq3, textViewDescriptionFaq4, textViewDescriptionFaq5};

        setTextViewDescriptionFaq(list_faq1_description_text.get(numberPage), textViews, numberPage);

        TextView textViewHeaderDetailFaq = page.findViewById(R.id.textViewHeaderDetailFaq);
        faq_text_header_detail = getResources().getStringArray(R.array.faq_text_header_detail);
        textViewHeaderDetailFaq.setText(faq_text_header_detail[numberPage]);

        RadioButton radioButton1 = page.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = page.findViewById(R.id.radioButton2);
        RadioButton radioButton3 = page.findViewById(R.id.radioButton3);
        buttonStart = page.findViewById(R.id.buttonStart);
        switch (numberPage) {
            case 0:
                radioButton1.setChecked(true);
                break;
            case 1:
                radioButton2.setChecked(true);
                break;
            case 2:
                radioButton3.setChecked(true);
                break;
        }
        if (numberPage == 2) {
            buttonStart.setVisibility(View.VISIBLE);
        } else {
            buttonStart.setVisibility(View.INVISIBLE);
        }
    }

    private void setTextViewDescriptionFaq(String[] strings, TextView[] textViews, int numberPage) {
        for (int i = 0; i < strings.length; i++) {
            CharSequence sequence = strings[i];
            SpannableString spannableString = new SpannableString(sequence);
            spannableString.setSpan(new BulletSpan(20), 0, sequence.length(), 0);
            textViews[i].setText(spannableString);
            if (numberPage == 1) {
                textViews[0].setText(strings[0]);
                textViews[0].setTypeface(Typeface.DEFAULT_BOLD);
                textViews[0].setGravity(Gravity.CENTER);
                textViews[4].setText(strings[4]);
                textViews[4].setTypeface(Typeface.DEFAULT_BOLD);
                textViews[4].setGravity(Gravity.CENTER);
            } else {
                textViews[0].setTypeface(Typeface.DEFAULT);
                textViews[0].setGravity(Gravity.LEFT);
                textViews[4].setTypeface(Typeface.DEFAULT);
                textViews[4].setGravity(Gravity.LEFT);
            }
        }
        if (strings.length < textViews.length) {
            textViews[4].setVisibility(View.INVISIBLE);
        } else {
            textViews[4].setVisibility(View.VISIBLE);
        }
    }
}