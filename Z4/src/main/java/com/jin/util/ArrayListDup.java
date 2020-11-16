package com.jin.util;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListDup {
    public static void main(String[] args) {
        ArrayList<String> strList = new ArrayList<>(
                Arrays.asList("1", "1", "2", "3", "3", "3", "4", "5", "6", "6", "6", "7", "8"));
        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8));
        removeDup1(numbersList);
        removeDup2(numbersList);
        removeDup3(strList);
        removeDup4(strList);
        removeDup5(strList);
        getDuplicateElements(numbersList);
    }
    public static void removeDup1(ArrayList<Integer> numbersList) {
        System.out.println(numbersList);
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(numbersList);
        ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);
        System.out.println(listWithoutDuplicates);
    }
    public static void removeDup2(ArrayList<Integer> numbersList) {
        System.out.println(numbersList);
        List<Integer> listWithoutDuplicates = numbersList.stream().distinct().collect(Collectors.toList());
        System.out.println(listWithoutDuplicates);
    }
    public static void removeDup3(List<String> list) {
        HashSet<String> set = new HashSet<String>(list.size());
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (set.add(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
        System.out.println(list);
    }
    public static void removeDup4(List<String> list) {
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
        System.out.println(list);
    }
    public static void removeDup5(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if(i!=j&&list.get(i)==list.get(j)) {
                    list.remove(list.get(j));
                }
            }
        }
        System.out.println(list);
    }
    public static <E> List<E> getDuplicateElements(List<E> list) {
        List<E> result =  list.stream() // list 对应的 Stream
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() // 所有 entry 对应的 Stream
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());  // 转化为 List
        System.out.println(result);
        return result;
    }
}
