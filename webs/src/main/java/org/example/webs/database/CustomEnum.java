package org.example.webs.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomEnum {
    public Map<String, Integer> enums;

    CustomEnum(ArrayList<String> keys, ArrayList<Integer> values) {
        enums = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            if (values.get(i) < 0)
                enums.put(keys.get(i), values.get(i));

            i++;
        }
    }
}