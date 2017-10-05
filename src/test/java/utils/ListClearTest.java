package utils;

import java.util.ArrayList;
import java.util.List;

public class ListClearTest{

    public static void main(String[] args){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        System.out.println(list.size());

        list.clear();
        System.out.println(list.size());
        System.out.println(list == null);
    }
}
