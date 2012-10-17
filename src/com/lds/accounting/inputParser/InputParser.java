package com.lds.accounting.inputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class InputParser {

    private static final String SP = " ";

    public static Row parse(String input) {
        if (input == null) {
            return null;
        }

        String[] tmp = input.split(SP);
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(tmp));
        Row row = new Row();
        List<String> remain = new ArrayList<String>();
        for (int i = 0, l = list.size(); i < l; i++) {
            String str = list.get(i);
            if (str.startsWith("#")) {
                row.setCategory(new Category(str.substring(1)));
                continue;
            }
            if (str.startsWith("@")) {
                row.addContact(new Contact(str.substring(1)));
                continue;
            }
            Double d = toDouble(str);
            if (d != null) { // isNumeric
                row.setDirection((d >= 0));
                row.setPrice(Math.abs(d));
                continue;
            }
            Date date = toDate(str);
            if (date != null) {
                row.setDate(date);
                continue;
            }
            remain.add(str);
        }
        row.setSummary(listJoin(remain, SP));
        row.setContent(input);
        if (row.getDate() == null) {
            row.setDate(new Date());
        }
        return row;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static Double toDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            // do nothing
        }
        return null;
    }
    
    public static Date toDate(String str) {
        final String SP = "-";
        if (! str.contains(SP)) {
            return null;
        }
        String[] tmp = str.split(SP);
        Calendar c = Calendar.getInstance();
        if (tmp.length == 2) { // mm-dd
            System.out.println("mm-dd: " + tmp[0] + "-" + tmp[1] );
            c.set(Calendar.MONTH, Integer.parseInt(tmp[0]) - 1);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp[1]));
            return c.getTime();
        } else if (tmp.length == 3) { // yy-mm-dd
            System.out.println("yy-mm-dd: " + tmp[0] + "-" + tmp[1] + "-" + tmp[2]);
            c.set(Calendar.YEAR, Integer.parseInt(tmp[0]));
            c.set(Calendar.MONTH, Integer.parseInt(tmp[1]) - 1);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp[2]));
            return c.getTime();
        }
        return null;
    }
    
    
    public static String listJoin(List<String> list, String sp) {
        StringBuilder out = new StringBuilder();
        for (Object o : list)
        {
          out.append(o.toString());
          out.append(sp);
        }
        return out.toString();
    }

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.println(InputParser.parse(input));
        }
        
    }
}
