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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.tarastom.gufi.model.Service;
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
//        uploadDB();
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

//Загрузка данных в БД
    public void uploadDB() {
        Map<String, String> listCategory = new LinkedHashMap<>();
        Set<String> category = new HashSet<>();
        ArrayList<Service> services = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Service service = new Service(
                    getCategory(), getName(), getItem(),
                    getSurName(), "женский", getNumberPhone(), new ArrayList<>(),
                    getPayment(), getBusinessHours(), "Есть выезд к клиенту на дом"
            );
            services.add(service);
            category.add(service.getCategory());
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (Service service : services) {
            db.collection("services2").add(service);
        }
        for (String s : category) {
            listCategory.put(s, "category");
        }
        db.collection("category").add(listCategory);
        Toast.makeText(this, "Данные успешно загружаются!", Toast.LENGTH_SHORT).show();
    }
    private static String getName() {
        String csv = "Августа,Авдотья,Аврора,Агата,Агапия,Агафья,Аглая,Агнесса,Агния,Агриппина,Агунда,Ада,Аделина,Аделаида,Адель,Адиля,Адриана,Аза,Азалия,Азиза,Айгуль,Айлин,Айнагуль,Аида,Айжан,Аксинья,Акулина,Алана,Алевтина,Александра,Алена,Алико,Алина,Алиса,Алия,Алла,Алсу,Альба,Альберта,Альбина,Альвина,Альфия,Альфреда,Аля,Амаль,Амелия,Амина,Амира,Анаит,Анастасия,Ангелина,Анеля,Анжела,Анжелика,Анисья,Анита,Анна,Антонина,Анфиса,Аполлинария,Арабелла,Ариадна,Ариана,Арина,Архелия,Асель,Асия,Ассоль,Астра,Астрид,Ася,Аурелия,Афанасия,Аэлита,Беатриса,Белинда,Белла,Берта,Бирута,Богдана,Божена,Борислава,Бронислава,Валентина,Валерия,Ванда,Ванесса,Варвара,Василина,Василиса,Венера,Вера,Вероника,Веселина,Весна,Веста,Вета,Вида,Викторина,Виктория,Вилена,Вилора,Виолетта,Виргиния,Виринея,Вита,Виталина,Влада,Владислава,Владлена,Габриэлла,Галина,Галия,Гаянэ,Гелена,Гаянэ,Гелена,Гелла,Генриетта,Георгина,Гера,Гертруда,Глафира,Глория,Гортензия,Гражина,Грета,Гузель,Гулия,Гульмира,Гульназ,Гульнара,Гульшат,Дайна,Далия,Дамира,Дана,Даниэла,Данута,Дара,Дарина,Дарья,Даяна,Дебора,Джамиля,Джемма,Дженнифер,Джессика,Джулия,Джульетта,Диана,Дилара,Дильназ,Дильнара,Диля,Дина,Динара,Диодора,Дионисия,Долорес,Доля,Доминика,Дора,Ева,Евангелина,Евгения,Евдокия,Екатерина,Елена,Елизавета,Есения,Ефимия,Жанна,Жасмин,Жозефина,Забава,Заира,Замира,Зара,Зарема,Зарина,Захария,Земфира,Зинаида,Зита,Злата,Зоряна,Зоя,Зульфия,Зухра,Иванна,Иветта,Ивона,Ида,Изабелла,Изольда,Илария,Илиана,Илона,Инара,Инга,Ингеборга,Индира,Инесса,Инна,Иоанна,Иоланта,Ираида,Ирина,Ирма,Искра,Ия,Калерия,Камилла,Капитолина,Карима,Карина,Каролина,Катарина,Кира,Клавдия,Клара,Кларисса,Климентина,Констанция,Кора,Корнелия,Кристина,Ксения,Лада,Лайма,Лана,Лара,Лариса,Лаура,Лейла,Лейсан,Леокадия,Леонида,Лера,Леся,Лиана,Лидия,Лиза,Лика,Лилиана,Лилия,Лина,Линда,Лиора,Лира,Лия,Лола,Лолита,Лора,Луиза,Лукерья,Любовь,Людмила,Ляля,Люция,Магда,Магдалина,Мадина,Майя,Малика,Мальвина,Мара,Маргарита,Марианна,Марика,Марина,Мария,Марселина,Марта,Маруся,Марфа,Марьям,Матильда,Мелания,Мелисса,Мика,Мила,Милада,Милана,Милена,Милица,Милолика,Милослава,Мира,Мирослава,Мирра,Моника,Муза,Мэри,Надежда,Назира,Наиля,Наима,Нана,Наоми,Наталья,Нателла,Нелли,Неонила,Ника,Николь,Нина,Нинель,Нонна,Нора,Нурия,Одетта,Оксана,Октябрина,Олеся,Оливия,Ольга,Офелия,Павла,Павлина,Памела,Патриция,Пелагея,Перизат,Полина,Прасковья,Рада,Радмила,Раиса,Ревекка,Регина,Рема,Рената,Римма,Рина,Рита,Рогнеда,Роберта,Роза,Роксана,Ростислава,Рузалия,Рузанна,Рузиля,Румия,Русалина,Руслана,Руфина,Сабина,Сабрина,Сажида,Саида,Саломея,Самира,Сандра,Сания,Санта,Сара,Сати,Светлана,Святослава,Севара,Северина,Селена,Серафима,Сильва,Сима,Симона,Слава,Снежана,Соня,София,Станислава,Стелла,Стефания,Сусанна,Таира,Таисия,Тала,Тамара,Тамила,Тара,Татьяна,Тереза,Тина,Тора,Ульяна,Урсула,Устина,Устинья,Фаиза,Фаина,Фания,Фаня,Фарида,Фатима,Фая,Фекла,Фелиция,Феруза,Физура,Флора,Франсуаза,Фрида,Харита,Хилари,Хильда,Хлоя,Христина,Цветана,Челси,Чеслава,Чулпан,Шакира,Шарлотта,Шейла,Шелли,Шерил,Эвелина,Эвита,Эдда,Эдита,Элеонора,Элиана,Элиза,Элина,Элла,Эллада,Элоиза,Эльвина,Эльвира,Эльга,Эльза,Эльмира,Эльнара,Эля,Эмилия,Эмма,Эмили,Эрика,Эрнестина,Эсмеральда,Этель,Этери,Юзефа,Юлия,Юна,Юния,Юнона,Ядвига,Яна,Янина,Ярина,Ярослава,Ясмина";
        String[] elements = csv.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);
        ArrayList<String> listOfString = new ArrayList<>(fixedLenghtList);
        int v = (int) (Math.random() * listOfString.size());
        return listOfString.get(v);
    }
    private static String getSurName() {
        String csv = "Чернова,Сметанина,Окунева,Шарапова,Чудопалова,Коновалова,Шпак,Никифорова,Олькова,Клинова,Спиридонова,Чупина,Сенцова,Москательникова,Золотавина,Власова,Сатинова,Смирнова,Саксина,Нежная,Милая,Красивая,Несносная,Влюбленная,Миролюбивая,Онисимова,Коваленко,Синицина,Нелюбимова,Юрченко,Зимина,Ким,Русакова,Анисимова,Соловьева,Никитина,Ковальчук,Мирная,Новая,Литвинова,Хрустальная,Гагарина,Кирова,Свободная,Отважная,Крупская,Тихая,Сильная,Вернер,Скиба,Троепольская,Бахман,Регеда,Бессмертная,Заточная,Королевская,Плохая,Ведьманова,Вингерт,Школьная,Гирха,Олейник,Крутая,Цвига,Воля,Турлайс,Королева,Задеринога,Киблер,Ризель,Вихт,Энгель,Ножка,Скирда,Лень,Богословская,Щебет,Рыбка,Солтус,Обида,Монтель,Курочка,Придуха,Поперечная,Амоша,Гурман,Бутылка,Сердючкина,Куколкина,Петроськина,Курдюмова,Выдерганожкина,Кукушкина,Маслякова,Девчулькина,Коротулькина,Симпатюлькина,Жестокая,Мусик,Винегредова,Сухая,Соловей,Вьюшкина,Колокольчик,Пупкина,Свистулькина,Пересистова,Толствая,Пушечка,Мусечка,Говорилкина,Нехотелкина,Кукарача,Семицветова,Алибаба,Курдюкова,Негина,Бабурова,Головченко,Сарнычева,Полушина,Журавлева,Шаврина,Курсалина,Николаенко,Ожегова,Ямщикова,Малютина,Карпова,Исламова,Никерова,Елисеева,Крутова,Жвикова,Грибалева,Тихонова,Кочиняна,Карибжанова,Анрепа,Жутова,Ябловская,Богуна,Яговенко,Есипова,Зимина,Усова,Обухова,Витвинская,Клима,Хватова,Фененко,Лоскутникова,Живкова,Пенкина,Квартальнова,Шульц,Саитова,Богоносцева,Климушина,Муленко,Федосеева,Челомей,Канадина,Жаворонкова,Янютина,Мышелова,Зиновьева,Щеголихина,Хабарова,Флёрова,Лещенко,Герасимова,Брынских,Турбина,Эллинская,Колвашева,Чебыкина,Яндукина,Разбойникова,Яцкова,Яглинцева,Пищальникова,Теплых,Лебедева,Мирохина,Коллерова,Андроникова,Яцкевича,Жарова,Евдокимова,Роговская,Пименова,Разуваева,Петрова,Пастух,Купревич,Цыганова,Чекмарёва,Сычёва,Супрунова,Арсеиньева,Брагина,Чана,Поникарова,Ячменева,Курочкина,Майсак,Бобкова,Листунова,Серпионова,Любова,Бабурина,Хабенская,Килессо,Швардыгула,Аникина";
        String[] elements = csv.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);
        ArrayList<String> listOfString = new ArrayList<>(fixedLenghtList);
        int v = (int) (Math.random() * listOfString.size());
        return listOfString.get(v);
    }
    private static String getItem() {
        int v = (int) (Math.random() * 2);
        String[] s = {"Мастер", "Студия"};
        return s[v];
    }
    private static String getCategory() {
        String csv = "Макияж, Депиляция, Шугаринг, Покрытие гель-лаком, Маникюр, Педикюр, Наращивание ногтей, SPA, Массаж, Покрытие шеллак, Архитектура бровей, Восковая депиляция, Пилинг, Лифтинг, Отбеливание зубов, Фотоэпиляция, PQ Age-пилинг, RECYTOS-Skin, Лазерная эпиляция";
        String[] elements = csv.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);
        ArrayList<String> listOfString = new ArrayList<>(fixedLenghtList);
        int v = (int) (Math.random() * listOfString.size());
        return listOfString.get(v);
    }
    private String getNumberPhone() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("067-");
        for (int i = 0; i < 7; i++) {
            if (i == 3 || i == 5) {
                stringBuilder.append("-");
            }
            int v = (int) (Math.random() * 10);
            stringBuilder.append(v);
        }
        return stringBuilder.toString();
    }
    private String getPayment() {
        int v = (int) (Math.random() * 3);
        String[] s = {"Полная оплата", "Оплата частями", "Кредит"};
        return s[v];
    }

    private String getBusinessHours() {
        int v = (int) (Math.random() * 4);
        String[] s = {"С 10:00 до 16:00 ежедневно", "С 08:00 до 18:00 ежедневно", "С 08:00 до 18:00 в будни", "С 09:00 до 16:00 в будни"};
        return s[v];
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}