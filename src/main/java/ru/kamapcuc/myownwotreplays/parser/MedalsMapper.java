package ru.kamapcuc.myownwotreplays.parser;

import java.util.HashMap;
import java.util.Map;

public class MedalsMapper {

    private final static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(50, "Медаль Орлика");
        map.put(51, "Медаль Оськина");
        map.put(52, "Медаль Халонена");
        map.put(53, "Медаль Бурды");
        map.put(54, "Медаль Бийота");
        map.put(55, "Медаль Колобанова");
        map.put(56, "Медаль Фадина");
        map.put(73, "Медаль Рэдли-Уолтерса");
        map.put(74, "Медаль Пула");
        map.put(75, "Медаль Бруно");
        map.put(76, "Медаль Тарцая");
        map.put(77, "Медаль Паскуччи");
        map.put(78, "Медаль Думитру");
        map.put(106, "Медаль Лехвеслайхо");
        map.put(107, "Медаль Николса");
        map.put(110, "Медаль героев Расейняя");
        map.put(145, "Медаль де Ланглада");
        map.put(146, "Медаль Тамада Йошио");
        map.put(148, "Медаль Найдина");
        map.put(305, "Медаль «Поле боя: Арденнская операция 1944 года»");
        map.put(301, "Медаль «Поле боя: бои у озера Балатон»");
        map.put(303, "Медаль «Поле боя: битва на Курской дуге»");
        map.put(298, "Медаль Гора");
        map.put(300, "Медаль Старка");
        map.put(34, "Воин");
        map.put(35, "Захватчик");
        map.put(37, "Защитник");
        map.put(38, "Стальная стена");
        map.put(39, "Поддержка");
        map.put(40, "Разведчик");
        map.put(72, "Дозорный");
        map.put(227, "Танкист-снайпер");
        map.put(228, "Основной калибр");
        map.put(232, "Отличник боевой подготовки");
        map.put(230, "За отличную стрельбу");
        map.put(143, "Братья по оружию");
        map.put(144, "Решающий вклад");
        map.put(236, "Бронетанковый кулак");
        map.put(61, "Рейдер");
        map.put(62, "Коса смерти");
        map.put(64, "Камикадзе");
        map.put(147, "Бомбардир");
        map.put(237, "Тактик");
        map.put(306, "Уроки истории: Арденнская операция 1944 года");
        map.put(302, "Уроки истории: бои у озера Балатон");
        map.put(304, "Уроки истории: битва на Курской дуге");
        map.put(297, "За контрбатарейную стрельбу");
        map.put(299, "Хладнокровный");
        map.put(289, "За волю к победе");
        map.put(523, "Дуэлянт");
        map.put(522, "Боец");
        map.put(517, "Непробиваемый");
        map.put(521, "Огонь на поражение");
        map.put(150, "Спартанец");
        map.put(151, "Невозмутимый");
        map.put(152, "Счастливчик");
        map.put(233, "Царь горы");
        map.put(286, "Бог войны");
        map.put(296, "Монолит");
        map.put(479, "Бой до последнего");
        map.put(525, "Поджигатель");
        map.put(526, "Костолом");
        map.put(527, "Заговорённый");
        map.put(524, "Взрывотехник");
        map.put(528, "В расчёте!");
    }

    public String mapMedal(Integer id){
        return map.get(id);
    }

}