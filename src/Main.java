import java.util.HashMap;
import java.util.Map;

public class Main{

    public static void main(String[] args){

        String str = "three point one four";
        String[] strArr = str.split(" ");
        StringBuilder sb = new StringBuilder();
        Map<String,String> map = new HashMap<>();
        map.put("three","3");
        map.put("point", ".");
        map.put("one", "1");
        map.put("four", "4");



        for (String s : strArr) {
            if (map.containsKey(s)) {
               String res=  map.get(s);
               sb.append(res);
               }
            }
        System.out.println(sb);
    }
}