package com.digdes.school;


import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String... args){
        Date currentTime = new Date();   // берем дату до метода.


        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {
            //Вставка строки в коллекцию
            List<Map<String,Object>> result1 = starter.execute("INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=0, 'active'=true");
            List<Map<String,Object>> result1_1 = starter.execute("INSERT VALUES 'lastName' = 'Петров' ,'cost'=5.4, 'id'=1, 'age'=30, 'active'=true");
            List<Map<String,Object>> result1_2 = starter.execute("INSERT VALUES 'lastName' = 'Иванов' ,'cost'=14.3, 'id'=1, 'age'=25, 'active'=false");
            List<Map<String,Object>> result1_3 = starter.execute("INSERT VALUES 'lastName' = 'Томилова' , 'age'=7, 'active'=true");
            List<Map<String,Object>> result1_4 = starter.execute("INSERT VALUES 'lastName' = 'Егорова' ,'cost'=56.4, 'id'=7, 'age'=30, 'active'=true");
            List<Map<String,Object>> result1_5 = starter.execute("INSERT VALUES 'lastName' = 'Светляков' ,'cost'=47.3, 'id'=1, 'age'=14, 'active'=false");
            List<Map<String,Object>> result1_6 = starter.execute("INSERT VALUES 'lastName' = 'Дружжинников' ,'cost'=5.4, 'id'=8,  'active'=true");
            List<Map<String,Object>> result1_7 = starter.execute("INSERT VALUES 'lastName' = 'Соловьев' ,'cost'=4.35, 'id'=12, 'age'=275, 'active'=false");
            List<Map<String,Object>> result1_8 = starter.execute("INSERT VALUES 'lastName' = 'Плом' , 'id'=63, 'age'=40, ");
            List<Map<String,Object>> result1_9 = starter.execute("INSERT VALUES 'lastName' = 'Гробатов' ,'cost'=5.4,  'age'=340, 'active'=true");
            List<Map<String,Object>> result1_0 = starter.execute("INSERT VALUES 'cost'=0.3, 'id'=51, 'age'=25, 'active'=false");

       //     System.out.println("result1: " + result1);
         //   System.out.println("result1_1: " + result1_1);
        //    System.out.println("result1_2: " + result1_2);
         //   System.out.println("result1: " + result1);
            //Изменение значения которое выше записывали
           /*List<Map<String,Object>> result2 = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 where 'age'!=0");
            System.out.println(("result2: " +result2));*/

        /*  List<Map<String,Object>> result2_2 = starter.execute("UPDATE VALUES 'active'=true  where 'age'>0");
          System.out.println("result2_2: " + result2_2);*/
         /*   List<Map<String,Object>> result2_3 = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1, 'id'=3");
            System.out.println("result2_3: " + result2_3);*/

          /*  List<Map<String,Object>> result4_1 = starter.execute("DELETE WHERE 'id'=3");
            System.out.println("result4_1: " + result4_1);*/

         /*   List<Map<String,Object>> result4_2 = starter.execute("delete  ");
            System.out.println("result4_2: " + result4_2);*/

            //Получение всех данных из коллекции (т.е. в данном примере вернется 1 запись)
          //  List<Map<String, Object>> result3 = starter.execute("delete");

            /*List<Map<String, Object>> result2 = starter.execute("delete VALUES    where 'cost'<10 or 'age'=30");
            System.out.println(("result2: " + result2));*/

         /* List<Map<String, Object>> result2_2 = starter.execute("delete VALUES   where 'age'<10");
            System.out.println("result2_2: " + result2_2);*/

          /*  List<Map<String, Object>> result2_3 = starter.execute("delete VALUES 'active'=false, 'cost'=222.1 where 'active'=false");
            System.out.println("result2_3: " + result2_3);*/

            List<Map<String, Object>> result4_1 = starter.execute("delete WHERE 'lastName'ilike '%т%'");
            System.out.println("result4_1: " + result4_1);




       //    System.out.println("array"+starter.data);
           System.out.println("");
int i=0;
            for (Map<String, Object> datum : starter.data) {
                if(i==0){
                System.out.println(datum.keySet());
                i++;}
                System.out.println(datum.values());
            }





            Date newTime = new Date();     // берем время после метода.

            long longs =  newTime.getTime()-currentTime.getTime(); // считаем разницу. в этой переменной будет время работы метода.
            System.out.println("time: "+longs+ " ms");
            //    for (Map<String, Object> iter: data.get(1)) System.out.println("map?: " + iter);

        }catch (Exception ex){
            ex.printStackTrace();
        }




    }
}
