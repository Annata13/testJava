package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSchoolStarter {
    Long id;
    String lastName;
    Long age;
    Double cost;
    Boolean active;
    Pattern patternId;
    Pattern patternAge;
    Pattern patternLastName;
    Pattern patternCost;
    Pattern patternActive;
    public List<Map<String, Object>> data = new ArrayList<>();

    //Дефолтный конструктор

    public JavaSchoolStarter() {

    }

    //На вход запрос, на выход результат выполнения запроса
    public List<Map<String, Object>> execute(String request) throws Exception {
        id = null;
        lastName = null;
        age = null;
        cost = null;
        active = null;

        assingVariable(request); // назначаем переменным знвчения из строки request

        Pattern patternInsert = Pattern.compile("INSERT VALUES", Pattern.CASE_INSENSITIVE);
        Matcher matcherInsert = patternInsert.matcher(request);
        if (matcherInsert.find()) { // есть оператор INSERT VALUES
            // добавляем новую строку
            return insert();
        }

        Pattern patternUpdate = Pattern.compile("UPDATE", Pattern.CASE_INSENSITIVE);
        Matcher matcherUpdate = patternUpdate.matcher(request);
        if (matcherUpdate.find()) { //есть оператор UPDATE
            List<Map<String, Object>> result = new ArrayList<>();
            List<Map<String, Object>> resultTwo = new ArrayList<>();

            Pattern patternWhere = Pattern.compile("WHERE .*", Pattern.CASE_INSENSITIVE);
            Matcher matcherWhere = patternWhere.matcher(request);
            if (matcherWhere.find()) { // оператор where обнаружен

                String stringWhere = matcherWhere.group().replaceAll("where", "").trim(); //строка после оператора where

                Pattern patternAnd = Pattern.compile(".*AND.*", Pattern.CASE_INSENSITIVE);
                Pattern patternAND = Pattern.compile("AND", Pattern.CASE_INSENSITIVE);
                Matcher matcherAnd = patternAnd.matcher(stringWhere);
                Matcher matcherAND = patternAND.matcher(stringWhere);
                if (matcherAnd.find()) {
                    // есть логический оператор And в Where строке
                    matcherAND.find();
                    // есть логический оператор OR в Where строке
                    String[] stringOr = matcherAnd.group().split(matcherAND.group());
                    stringOr[0] = stringOr[0].trim();
                    stringOr[1] = stringOr[1].trim();
                    result = select(stringOr[0], data);
                    result = update(stringOr[1], result);
                    return result;
                }

                Pattern patternOr = Pattern.compile(".*OR.*", Pattern.CASE_INSENSITIVE);
                Pattern patternOR = Pattern.compile("OR", Pattern.CASE_INSENSITIVE);
                Matcher matcherOr = patternOr.matcher(stringWhere);
                Matcher matcherOR = patternOR.matcher(stringWhere);
                if (matcherOr.find()) {
                    matcherOR.find();
                    // есть логический оператор OR в Where строке
                    String[] stringOr = matcherOr.group().split(matcherOR.group());
                    stringOr[0] = stringOr[0].trim();
                    stringOr[1] = stringOr[1].trim();
                    resultTwo = update(stringOr[0], data);
                    result = update(stringOr[1], data);

                    for (int i = result.size() - 1; i >= 0; i--) {
                        //   System.out.println("two: "+result.get(i));
                        for (int j = resultTwo.size() - 1; j >= 0; j--) {
                            //   System.out.println("result: " + resultTwo.get(j));
                            if ((result.get(i).equals(resultTwo.get(j)))) {
                                //  System.out.println("add" + resultTwo.get(j));
                                resultTwo.remove(j);
                            }
                        }
                    }

                    for (int j = resultTwo.size() - 1; j >= 0; j--) {
                        result.add(resultTwo.get(j));
                    }
                    return result;
                }

                if ((!matcherOr.find()) && (!matcherAnd.find())) {
                    return update(stringWhere, data);
                }
            }

            if (!matcherWhere.find()) { // оператор where отсутствует
                for (int i = 0; i < data.size(); i++) {

                    if (age != null) {
                        data.get(i).put("age", age);
                    }
                    if (lastName != null) {
                        data.get(i).put("lastName", lastName);
                    }
                    if (cost != null) {
                        data.get(i).put("cost", cost);
                    }
                    if (active != null) {
                        data.get(i).put("active", active);
                    }
                    if (id != null) {
                        data.get(i).put("id", id);
                    }
                    result.add((data.get(i)));
                }
            }
            return result;
        }

        Pattern patternDelete = Pattern.compile("DELETE", Pattern.CASE_INSENSITIVE);
        Matcher matcherDelete = patternDelete.matcher(request);
        if (matcherDelete.find()) { //есть оператор Delete
            List<Map<String, Object>> result = new ArrayList<>();
            List<Map<String, Object>> resultTwo = new ArrayList<>();
            List<Map<String, Object>> resultOne = new ArrayList<>();

            Pattern patternWhere = Pattern.compile("WHERE .*", Pattern.CASE_INSENSITIVE);
            Matcher matcherWhere = patternWhere.matcher(request);
            if (matcherWhere.find()) { // оператор where обнаружен

                String stringWhere = matcherWhere.group().replaceAll("where", "").trim(); //строка после оператора where
                Pattern patternAnd = Pattern.compile(".*AND.*", Pattern.CASE_INSENSITIVE);
                Pattern patternAND = Pattern.compile("AND", Pattern.CASE_INSENSITIVE);
                Matcher matcherAnd = patternAnd.matcher(stringWhere);
                Matcher matcherAND = patternAND.matcher(stringWhere);
                if (matcherAnd.find()) {
                    // есть логический оператор And в Where строке
                    matcherAND.find();
                    // есть логический оператор OR в Where строке
                    String[] stringOr = matcherAnd.group().split(matcherAND.group());
                    stringOr[0] = stringOr[0].trim();
                    stringOr[1] = stringOr[1].trim();
                    result = select(stringOr[0], data);
                    result = delete(stringOr[1], result);
                    return result;
                }

                Pattern patternOr = Pattern.compile(".*OR.*", Pattern.CASE_INSENSITIVE);
                Pattern patternOR = Pattern.compile("OR", Pattern.CASE_INSENSITIVE);
                Matcher matcherOr = patternOr.matcher(stringWhere);
                Matcher matcherOR = patternOR.matcher(stringWhere);
                if (matcherOr.find()) {
                    matcherOR.find();
                    // есть логический оператор OR в Where строке
                    String[] stringOr = matcherOr.group().split(matcherOR.group());
                    stringOr[0] = stringOr[0].trim();
                    stringOr[1] = stringOr[1].trim();
                    resultTwo = delete(stringOr[0], data);
                    result = delete(stringOr[1], data);

                    for (int i = result.size() - 1; i >= 0; i--) {
                        //   System.out.println("two: "+result.get(i));
                        for (int j = resultTwo.size() - 1; j >= 0; j--) {
                            //   System.out.println("result: " + resultTwo.get(j));
                            if ((result.get(i).equals(resultTwo.get(j)))) {
                                //  System.out.println("add" + resultTwo.get(j));
                                resultTwo.remove(j);
                            }
                        }
                    }

                    for (int j = resultTwo.size() - 1; j >= 0; j--) {
                        result.add(resultTwo.get(j));
                    }
                    return result;
                }

                if ((!matcherOr.find()) && (!matcherAnd.find())) {
                    return delete(stringWhere, data);
                }
            }
            if (!matcherWhere.find()) { // оператор where отсутствует
                for (int i = data.size() - 1; i >= 0; i--) {
                    result.add((data.get(i)));
                    data.remove(i);
                }
            }
            return result;
        }

        Pattern patternSelect = Pattern.compile("SELECT", Pattern.CASE_INSENSITIVE);
        Matcher matcherSelect = patternSelect.matcher(request);
        if (matcherSelect.find()) { //есть оператор Delete
            List<Map<String, Object>> result = new ArrayList<>();
            List<Map<String, Object>> resultTwo = new ArrayList<>();
            //    List<Map<String, Object>> resultOne = new ArrayList<>();

            Pattern patternWhere = Pattern.compile("WHERE .*", Pattern.CASE_INSENSITIVE);
            Matcher matcherWhere = patternWhere.matcher(request);
            if (matcherWhere.find()) { // оператор where обнаружен
                String stringWhere = matcherWhere.group().replaceAll("where", "").trim(); //строка после оператора where
                Pattern patternAnd = Pattern.compile(".*AND.*", Pattern.CASE_INSENSITIVE);
                Pattern patternAND = Pattern.compile("AND", Pattern.CASE_INSENSITIVE);
                Matcher matcherAnd = patternAnd.matcher(stringWhere);
                Matcher matcherAND = patternAND.matcher(stringWhere);
                if (matcherAnd.find()) {
                    // есть логический оператор And в Where строке
                    matcherAND.find();
                    // есть логический оператор OR в Where строке
                    String[] stringOr = matcherAnd.group().split(matcherAND.group());
                    stringOr[0] = stringOr[0].trim();
                    stringOr[1] = stringOr[1].trim();
                    result = select(stringOr[0], data);
                    result = select(stringOr[1], result);
                    return result;
                }

                Pattern patternOr = Pattern.compile(".*OR.*", Pattern.CASE_INSENSITIVE);
                Pattern patternOR = Pattern.compile("OR", Pattern.CASE_INSENSITIVE);
                Matcher matcherOr = patternOr.matcher(stringWhere);
                Matcher matcherOR = patternOR.matcher(stringWhere);
                if (matcherOr.find()) {
                    matcherOR.find();
                    // есть логический оператор OR в Where строке
                    String[] stringOr = matcherOr.group().split(matcherOR.group());
                    stringOr[0] = stringOr[0].trim();
                    stringOr[1] = stringOr[1].trim();
                    resultTwo = select(stringOr[0], data);
                    result = select(stringOr[1], data);

                    for (int i = result.size() - 1; i >= 0; i--) {
                        //   System.out.println("two: "+result.get(i));
                        for (int j = resultTwo.size() - 1; j >= 0; j--) {
                            //   System.out.println("result: " + resultTwo.get(j));
                            if ((result.get(i).equals(resultTwo.get(j)))) {
                                //  System.out.println("add" + resultTwo.get(j));
                                resultTwo.remove(j);
                            }
                        }
                    }

                    for (int j = resultTwo.size() - 1; j >= 0; j--) {
                        result.add(resultTwo.get(j));
                    }
                    return result;
                }

                if ((!matcherOr.find()) && (!matcherAnd.find())) {
                    return select(stringWhere, data);
                }
            }
            if (!matcherWhere.find()) { // оператор where отсутствует
                for (int i = 0; i < data.size(); i++) {
                    result.add((data.get(i)));
                }
            }
            return result;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        return result;
    }

    public void assingVariable(String request) throws Exception {
        patternId = Pattern.compile("'id'\\s?=\\s?\\d+", Pattern.CASE_INSENSITIVE);
        Matcher matcherId = patternId.matcher(request);
        if (matcherId.find()) {
            id = Long.parseLong(matcherId.group().replaceAll("\\D", "").trim());
        }

        patternLastName = Pattern.compile("'lastName'\\s?=\\s?'[А-яЁё]+'", Pattern.CASE_INSENSITIVE);
        Matcher matcherLastName = patternLastName.matcher(request);
        if (matcherLastName.find()) {
            lastName = matcherLastName.group().replaceAll("['lastName' ' =]", "").trim();
        }

        patternAge = Pattern.compile("'age'\\s?=\\s?\\d+", Pattern.CASE_INSENSITIVE);
        Matcher matcherAge = patternAge.matcher(request);
        if (matcherAge.find()) {
            age = Long.parseLong(matcherAge.group().replaceAll("\\D", "").trim());
        }

        patternCost = Pattern.compile("'cost'\\s?=\\s?(\\d+.?\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcherCost = patternCost.matcher(request);
        if (matcherCost.find()) {
            cost = Double.parseDouble(matcherCost.group().replaceAll("'cost'\\s?=\\s?", "").trim());
        }

        patternActive = Pattern.compile("'active'\\s?=\\s?(\\w)+", Pattern.CASE_INSENSITIVE);
        Matcher matcherActive = patternActive.matcher(request);
        if (matcherActive.find()) {
            active = Boolean.parseBoolean(matcherActive.group().replaceAll("'active'\\s?=\\s?", "").trim());
        }

        Pattern patternNotName = Pattern.compile("('.+')\\s?=(.*)\\s?", Pattern.CASE_INSENSITIVE);       // Поиск лишних наименований
        Matcher matchernotName = patternNotName.matcher(request);
        if (matchernotName.find()) {
            String notName;
            notName = matchernotName.group();
            Matcher matcherNotName = patternId.matcher(notName);
            if (matcherNotName.find()) {
                String str = matcherNotName.group();
                notName = notName.replaceAll(str, "");
            }
            matcherNotName = patternAge.matcher(notName);
            if (matcherNotName.find()) {
                String str = matcherNotName.group();
                notName = notName.replaceAll(str, "");
            }
            matcherNotName = patternActive.matcher(notName);
            if (matcherNotName.find()) {
                String str = matcherNotName.group();
                notName = notName.replaceAll(str, "");
            }
            matcherNotName = patternCost.matcher(notName);
            if (matcherNotName.find()) {
                String str = matcherNotName.group();
                notName = notName.replaceAll(str, "");
            }
            matcherNotName = patternLastName.matcher(notName);
            if (matcherNotName.find()) {
                String str = matcherNotName.group();
                notName = notName.replaceAll(str, "");
            }
            matcherNotName = patternNotName.matcher(notName);
            if (matcherNotName.find()) {
                System.out.println("Наименование отсутствует в таблице: " + matcherNotName.group());
                throw new Exception();
            }
        }
    }

    public List<Map<String, Object>> insert() throws Exception { // добавить новый элемент
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();

        row.put("id", id);
        row.put("lastName", lastName);
        row.put("age", age);
        row.put("cost", cost);
        row.put("active", active);

        this.data.add(row);

        if ((id == null) && (lastName == null) && (age == null) && (cost == null) && (active == null)) {
            throw new Exception();
        }
        int i = data.size() - 1;
        result.add(data.get(i));
        return result;
    }

    public List<Map<String, Object>> update(String stringWhere, List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();

        //   if ((!matcherOr.find()) && (!matcherAnd.find())) {
        //нет логических операторов
        Pattern patternEquals = Pattern.compile("\\w+'\\s?=.*"); // =
        Matcher matcherEquals = patternEquals.matcher(stringWhere);
        if (matcherEquals.find()) { // =
            String[] arrayEquals = matcherEquals.group().split("=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Object comparison = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparison = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("lastName")) {
                comparison = arrayEquals[1].toString();
            }
            if (arrayEquals[0].equals("cost")) {
                comparison = Double.parseDouble(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("active")) {
                comparison = Boolean.parseBoolean(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {

                if (comparison.equals(data.get(i).get(arrayEquals[0]))) { //изменение =
                    if (age != null) {
                        data.get(i).put("age", age);
                    }
                    if (lastName != null) {
                        data.get(i).put("lastName", lastName);
                    }
                    if (cost != null) {
                        data.get(i).put("cost", cost);
                    }
                    if (active != null) {
                        data.get(i).put("active", active);
                    }
                    if (id != null) {
                        data.get(i).put("id", id);
                    }
                    result.add((data.get(i)));
                }

            }
            return result;
        }

        Pattern patternNotquals = Pattern.compile("\\w+'\\s?!=.*"); // !=
        Matcher matcherNotEquals = patternNotquals.matcher(stringWhere);
        if (matcherNotEquals.find()) { // =
            String[] arrayEquals = matcherNotEquals.group().split("!=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");
            Object comparison = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparison = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("lastName")) {
                comparison = arrayEquals[1].toString();
            }
            if (arrayEquals[0].equals("cost")) {
                comparison = Double.parseDouble(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("active")) {
                comparison = Boolean.parseBoolean(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {

                if (!(comparison.equals(data.get(i).get(arrayEquals[0])))) { //изменение !=

                    if (age != null) {
                        data.get(i).put("age", age);
                    }
                    if (lastName != null) {
                        data.get(i).put("lastName", lastName);
                    }
                    if (cost != null) {
                        data.get(i).put("cost", cost);
                    }
                    if (active != null) {
                        data.get(i).put("active", active);
                    }
                    if (id != null) {
                        data.get(i).put("id", id);
                    }
                    result.add((data.get(i)));
                }

            }
            return result;
        }

        Pattern patternLike = Pattern.compile("\\w+'\\s?like.*", Pattern.CASE_INSENSITIVE); // like
        Matcher matcherLike = patternLike.matcher(stringWhere);
        if (matcherLike.find()) { // like
            String[] arrayEquals = matcherLike.group().split("like");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "").trim();
            String searhWord = arrayEquals[1].replaceFirst("'", "^").trim();
            searhWord = searhWord.replace("'", "$");
            searhWord = searhWord.replaceAll("%", "[а-яА-Я]*");
            Pattern patternSeahWork = Pattern.compile(searhWord);

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get(arrayEquals[0]) != null) {
                    Matcher matcherSeachWork = patternSeahWork.matcher(data.get(i).get(arrayEquals[0]).toString());
                    if (matcherSeachWork.find()) { //изменение  like
                        if (age != null) {
                            data.get(i).put("age", age);
                        }
                        if (lastName != null) {
                            data.get(i).put("lastName", lastName);
                        }
                        if (cost != null) {
                            data.get(i).put("cost", cost);
                        }
                        if (active != null) {
                            data.get(i).put("active", active);
                        }
                        if (id != null) {
                            data.get(i).put("id", id);
                        }
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternIlike = Pattern.compile("\\w+'\\s?ilike.*", Pattern.CASE_INSENSITIVE); // Ilike
        Matcher matcherIlike = patternIlike.matcher(stringWhere);
        if (matcherIlike.find()) { // ilike
            String[] arrayEquals = matcherIlike.group().split("ilike");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "").trim();
            String searhWord = arrayEquals[1].replaceFirst("'", "^").trim();
            searhWord = searhWord.toLowerCase();

            searhWord = searhWord.replace("'", "$");
            searhWord = searhWord.replaceAll("%", "[а-яА-Я]*");
            Pattern patternSeahWork = Pattern.compile(searhWord, Pattern.CASE_INSENSITIVE);

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get(arrayEquals[0]) != null) {

                    Matcher matcherSeachWork = patternSeahWork.matcher(data.get(i).get(arrayEquals[0]).toString().toLowerCase());
                    if (matcherSeachWork.find()) { //изменение !=
                        if (age != null) {
                            data.get(i).put("age", age);
                        }
                        if (lastName != null) {
                            data.get(i).put("lastName", lastName);
                        }
                        if (cost != null) {
                            data.get(i).put("cost", cost);
                        }
                        if (active != null) {
                            data.get(i).put("active", active);
                        }
                        if (id != null) {
                            data.get(i).put("id", id);
                        }
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternMoreRequal = Pattern.compile("\\w+'\\s?>=\\s?.*"); // >=
        Matcher matcherMoreRequal = patternMoreRequal.matcher(stringWhere);
        if (matcherMoreRequal.find()) { // >=
            String[] arrayEquals = matcherMoreRequal.group().split(">=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong <= longAge)) || ((comparisonDouble != null) && (comparisonDouble <= doubleCost))) { //изменение >=
                        if (age != null) {
                            data.get(i).put("age", age);
                        }
                        if (lastName != null) {
                            data.get(i).put("lastName", lastName);
                        }
                        if (cost != null) {
                            data.get(i).put("cost", cost);
                        }
                        if (active != null) {
                            data.get(i).put("active", active);
                        }
                        if (id != null) {
                            data.get(i).put("id", id);
                        }
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternMoreRequalUnder = Pattern.compile("\\w+'\\s?<=\\s?.*"); // <=
        Matcher matcherMoreRequalUnder = patternMoreRequalUnder.matcher(stringWhere);
        if (matcherMoreRequalUnder.find()) { // <=
            String[] arrayEquals = matcherMoreRequalUnder.group().split("<=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");
            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong >= longAge)) || ((comparisonDouble != null) && (comparisonDouble >= doubleCost))) { //изменение <=
                        if (age != null) {
                            data.get(i).put("age", age);
                        }
                        if (lastName != null) {
                            data.get(i).put("lastName", lastName);
                        }
                        if (cost != null) {
                            data.get(i).put("cost", cost);
                        }
                        if (active != null) {
                            data.get(i).put("active", active);
                        }
                        if (id != null) {
                            data.get(i).put("id", id);
                        }
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternMore = Pattern.compile("\\w+'\\s?>\\s?.*"); // >
        Matcher matcherMore = patternMore.matcher(stringWhere);
        if (matcherMore.find()) { // >
            String[] arrayEquals = matcherMore.group().split(">");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");
            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                //  System.out.println("type: "+type + " - " + data.get(i));
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong < longAge)) || ((comparisonDouble != null) && (comparisonDouble < doubleCost))) { //изменение >=
                        if (age != null) {
                            data.get(i).put("age", age);
                        }
                        if (lastName != null) {
                            data.get(i).put("lastName", lastName);
                        }
                        if (cost != null) {
                            data.get(i).put("cost", cost);
                        }
                        if (active != null) {
                            data.get(i).put("active", active);
                        }
                        if (id != null) {
                            data.get(i).put("id", id);
                        }
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternLess = Pattern.compile("\\w+'\\s?<\\s?.*"); // <
        Matcher matcherLess = patternLess.matcher(stringWhere);
        if (matcherLess.find()) { // <
            String[] arrayEquals = matcherLess.group().split("<");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");
            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong > longAge)) || ((comparisonDouble != null) && (comparisonDouble > doubleCost))) { //изменение <=
                        if (age != null) {
                            data.get(i).put("age", age);
                        }
                        if (lastName != null) {
                            data.get(i).put("lastName", lastName);
                        }
                        if (cost != null) {
                            data.get(i).put("cost", cost);
                        }
                        if (active != null) {
                            data.get(i).put("active", active);
                        }
                        if (id != null) {
                            data.get(i).put("id", id);
                        }
                        result.add((data.get(i)));
                    }
                }
            }

            return result;
        } else {
            return result;
        }
    }

    public List<Map<String, Object>> delete(String stringWhere, List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();

        //   if ((!matcherOr.find()) && (!matcherAnd.find())) {
        //нет логических операторов
        Pattern patternEquals = Pattern.compile("\\w+'\\s?=.*"); // =
        Matcher matcherEquals = patternEquals.matcher(stringWhere);
        if (matcherEquals.find()) { // =
            String[] arrayEquals = matcherEquals.group().split("=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Object comparison = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparison = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("lastName")) {
                comparison = arrayEquals[1].toString();
            }
            if (arrayEquals[0].equals("cost")) {
                comparison = Double.parseDouble(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("active")) {
                comparison = Boolean.parseBoolean(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                if (comparison.equals(data.get(i).get(arrayEquals[0]))) { //изменение =
                    result.add((data.get(i)));
                    data.remove(i);
                }
            }
            return result;
        }

        Pattern patternNotquals = Pattern.compile("\\w+'\\s?!=.*"); // !=
        Matcher matcherNotEquals = patternNotquals.matcher(stringWhere);
        if (matcherNotEquals.find()) { // =
            String[] arrayEquals = matcherNotEquals.group().split("!=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");
            Object comparison = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparison = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("lastName")) {
                comparison = arrayEquals[1].toString();
            }
            if (arrayEquals[0].equals("cost")) {
                comparison = Double.parseDouble(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("active")) {
                comparison = Boolean.parseBoolean(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {

                if (!(comparison.equals(data.get(i).get(arrayEquals[0])))) { //изменение !=
                    result.add((data.get(i)));
                    data.remove(i);
                }
            }
            return result;
        }

        Pattern patternLike = Pattern.compile("\\w+'\\s?like.*", Pattern.CASE_INSENSITIVE); // like
        Matcher matcherLike = patternLike.matcher(stringWhere);
        if (matcherLike.find()) { // like
            String[] arrayEquals = matcherLike.group().split("like");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "").trim();
            String searhWord = arrayEquals[1].replaceFirst("'", "^").trim();

            searhWord = searhWord.replace("'", "$");
            searhWord = searhWord.replaceAll("%", "[а-яА-Я]*");
            Pattern patternSeahWork = Pattern.compile(searhWord);

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get(arrayEquals[0]) != null) {
                    Matcher matcherSeachWork = patternSeahWork.matcher(data.get(i).get(arrayEquals[0]).toString());
                    if (matcherSeachWork.find()) { //изменение  like
                        result.add((data.get(i)));
                        data.remove(i);
                    }
                }
            }
            return result;
        }

        Pattern patternIlike = Pattern.compile("\\w+'\\s?ilike.*", Pattern.CASE_INSENSITIVE); // Ilike
        Matcher matcherIlike = patternIlike.matcher(stringWhere);
        if (matcherIlike.find()) { // ilike
            String[] arrayEquals = matcherIlike.group().split("ilike");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "").trim();
            String searhWord = arrayEquals[1].replaceFirst("'", "^").trim();
            searhWord = searhWord.toLowerCase();
            searhWord = searhWord.replace("'", "$");
            searhWord = searhWord.replaceAll("%", "[а-яА-Я]*");

            Pattern patternSeahWork = Pattern.compile(searhWord, Pattern.CASE_INSENSITIVE);

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get(arrayEquals[0]) != null) {
                    Matcher matcherSeachWork = patternSeahWork.matcher(data.get(i).get(arrayEquals[0]).toString().toLowerCase());
                    if (matcherSeachWork.find()) { //изменение !=
                        result.add((data.get(i)));
                        data.remove(i);
                    }
                }
            }
            return result;
        }

        Pattern patternMoreRequal = Pattern.compile("\\w+'\\s?>=\\s?.*"); // >=
        Matcher matcherMoreRequal = patternMoreRequal.matcher(stringWhere);
        if (matcherMoreRequal.find()) { // >=
            String[] arrayEquals = matcherMoreRequal.group().split(">=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");
            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }
            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong <= longAge)) || ((comparisonDouble != null) && (comparisonDouble <= doubleCost))) { //изменение >=
                        result.add((data.get(i)));
                        data.remove(i);
                    }
                }
            }
            return result;
        }

        Pattern patternMoreRequalUnder = Pattern.compile("\\w+'\\s?<=\\s?.*"); // <=
        Matcher matcherMoreRequalUnder = patternMoreRequalUnder.matcher(stringWhere);
        if (matcherMoreRequalUnder.find()) { // <=
            String[] arrayEquals = matcherMoreRequalUnder.group().split("<=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong >= longAge)) || ((comparisonDouble != null) && (comparisonDouble >= doubleCost))) { //изменение <=
                        result.add((data.get(i)));
                        data.remove(i);
                    }
                }
            }
            return result;
        }

        Pattern patternMore = Pattern.compile("\\w+'\\s?>\\s?.*"); // >
        Matcher matcherMore = patternMore.matcher(stringWhere);
        if (matcherMore.find()) { // >
            String[] arrayEquals = matcherMore.group().split(">");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong < longAge)) || ((comparisonDouble != null) && (comparisonDouble < doubleCost))) { //изменение >=
                        result.add((data.get(i)));
                        data.remove(i);
                    }
                }
            }
            return result;
        }

        Pattern patternLess = Pattern.compile("\\w+'\\s?<\\s?.*"); // <
        Matcher matcherLess = patternLess.matcher(stringWhere);
        if (matcherLess.find()) { // <
            String[] arrayEquals = matcherLess.group().split("<");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong > longAge)) || ((comparisonDouble != null) && (comparisonDouble > doubleCost))) { //изменение <=
                        result.add((data.get(i)));
                        data.remove(i);
                    }
                }
            }

            return result;
        } else {
            System.out.println("Exception?");
            return result;
        }
    }

    public List<Map<String, Object>> select(String stringWhere, List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();

        //   if ((!matcherOr.find()) && (!matcherAnd.find())) {
        //нет логических операторов
        Pattern patternEquals = Pattern.compile("\\w+'\\s?=.*"); // =
        Matcher matcherEquals = patternEquals.matcher(stringWhere);
        if (matcherEquals.find()) { // =
            String[] arrayEquals = matcherEquals.group().split("=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");


            Object comparison = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparison = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("lastName")) {
                comparison = arrayEquals[1].toString();
            }
            if (arrayEquals[0].equals("cost")) {
                comparison = Double.parseDouble(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("active")) {
                comparison = Boolean.parseBoolean(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {

                if (comparison.equals(data.get(i).get(arrayEquals[0]))) { //поиск =
                    result.add((data.get(i)));
                }

            }
            return result;
        }

        Pattern patternNotquals = Pattern.compile("\\w+'\\s?!=.*"); // !=
        Matcher matcherNotEquals = patternNotquals.matcher(stringWhere);
        if (matcherNotEquals.find()) { // =
            String[] arrayEquals = matcherNotEquals.group().split("!=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");
            Object comparison = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparison = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("lastName")) {
                comparison = arrayEquals[1].toString();
            }
            if (arrayEquals[0].equals("cost")) {
                comparison = Double.parseDouble(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("active")) {
                comparison = Boolean.parseBoolean(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                if (!(comparison.equals(data.get(i).get(arrayEquals[0])))) { //изменение !=
                    result.add((data.get(i)));
                }
            }
            return result;
        }

        Pattern patternLike = Pattern.compile("\\w+'\\s?like.*", Pattern.CASE_INSENSITIVE); // like
        Matcher matcherLike = patternLike.matcher(stringWhere);
        if (matcherLike.find()) { // like
            String[] arrayEquals = matcherLike.group().split("like");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "").trim();
            String searhWord = arrayEquals[1].replaceFirst("'", "^").trim();

            searhWord = searhWord.replace("'", "$");
            searhWord = searhWord.replaceAll("%", "[а-яА-Я]*");
            Pattern patternSeahWork = Pattern.compile(searhWord);

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get(arrayEquals[0]) != null) {

                    Matcher matcherSeachWork = patternSeahWork.matcher(data.get(i).get(arrayEquals[0]).toString());
                    if (matcherSeachWork.find()) { //изменение  like
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternIlike = Pattern.compile("\\w+'\\s?ilike.*", Pattern.CASE_INSENSITIVE); // Ilike
        Matcher matcherIlike = patternIlike.matcher(stringWhere);
        if (matcherIlike.find()) { // ilike
            String[] arrayEquals = matcherIlike.group().split("ilike");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "").trim();
            String searhWord = arrayEquals[1].replaceFirst("'", "^").trim();
            searhWord = searhWord.toLowerCase();
            searhWord = searhWord.replace("'", "$");
            searhWord = searhWord.replaceAll("%", "[а-яА-Я]*");
            Pattern patternSeahWork = Pattern.compile(searhWord, Pattern.CASE_INSENSITIVE);

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get(arrayEquals[0]) != null) {
                    Matcher matcherSeachWork = patternSeahWork.matcher(data.get(i).get(arrayEquals[0]).toString().toLowerCase());
                    if (matcherSeachWork.find()) { //изменение !=
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternMoreRequal = Pattern.compile("\\w+'\\s?>=\\s?.*"); // >=
        Matcher matcherMoreRequal = patternMoreRequal.matcher(stringWhere);
        if (matcherMoreRequal.find()) { // >=
            String[] arrayEquals = matcherMoreRequal.group().split(">=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }

            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong <= longAge)) || ((comparisonDouble != null) && (comparisonDouble <= doubleCost))) { //изменение >=
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternMoreRequalUnder = Pattern.compile("\\w+'\\s?<=\\s?.*"); // <=
        Matcher matcherMoreRequalUnder = patternMoreRequalUnder.matcher(stringWhere);
        if (matcherMoreRequalUnder.find()) { // <=
            String[] arrayEquals = matcherMoreRequalUnder.group().split("<=");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong >= longAge)) || ((comparisonDouble != null) && (comparisonDouble >= doubleCost))) { //изменение <=
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternMore = Pattern.compile("\\w+'\\s?>\\s?.*"); // >
        Matcher matcherMore = patternMore.matcher(stringWhere);
        if (matcherMore.find()) { // >
            String[] arrayEquals = matcherMore.group().split(">");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }
            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong < longAge)) || ((comparisonDouble != null) && (comparisonDouble < doubleCost))) { //изменение >=
                        result.add((data.get(i)));
                    }
                }
            }
            return result;
        }

        Pattern patternLess = Pattern.compile("\\w+'\\s?<\\s?.*"); // <
        Matcher matcherLess = patternLess.matcher(stringWhere);
        if (matcherLess.find()) { // <
            String[] arrayEquals = matcherLess.group().split("<");
            arrayEquals[0] = arrayEquals[0].replaceAll("'", "");

            Long comparisonLong = null;
            Double comparisonDouble = null;
            if (arrayEquals[0].equals("id") || arrayEquals[0].equals("age")) {
                comparisonLong = Long.parseLong(arrayEquals[1]);
            }

            if (arrayEquals[0].equals("cost")) {
                comparisonDouble = Double.parseDouble(arrayEquals[1]);
            }

            for (int i = 0; i < data.size(); i++) {
                Object type = data.get(i).get(arrayEquals[0]);
                //  System.out.println("type: "+type + " - " + data.get(i));
                Long longAge = null;
                Double doubleCost = null;
                if (type != null) {
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Long) {
                        longAge = (long) type;
                    }
                    if ((type != null) && data.get(i).get(arrayEquals[0]) instanceof Double) {
                        doubleCost = (double) type;
                    }
                    if (((comparisonLong != null) && (comparisonLong > longAge)) || ((comparisonDouble != null) && (comparisonDouble > doubleCost))) { //изменение <=
                        result.add((data.get(i)));
                    }
                }
            }

            return result;
        } else {
            System.out.println("Exception?");
            return result;
        }
    }
}
