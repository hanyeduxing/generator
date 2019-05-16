package com.sf.gis.cds.common.util;

import java.util.*;

/**
 * 
 * 描述：
 *
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2016年8月9日      01174604         Create
 * ****************************************************************************
 * </pre>
 * @author 01174604
 * @since 1.0
 */
public class CollectionUtil {

	public static <T> boolean isEmpty(T[] arr) {
		if (arr == null || arr.length <= 0) {
			return true;
		}
		return false;
	}

	public static <T> boolean isNotEmpty(T[] arr) {
		return !isEmpty(arr);
	}

	/**
	 * 判断collection 不为空
	 *
	 * @param collection collection
	 * @return
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection) {
		if (collection != null && collection.size() > 0) {
			return true;
		}
		return false;
	}

	public static <E> boolean isEmpty(Collection<E> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	public static <K, V> boolean isEmpty(Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}
	public static <K, V> boolean isNotEmpty(Map<K, V> map) {
		return !isEmpty(map);
	}


	public static <T> List<T> array2List(T[] array) {
		if (CollectionUtil.isEmpty(array)) {
			return null;
		}
		return Arrays.asList(array);
	}

	public static String getKeySetString(Map<?, ?> map) {
		int i = 0;
		int last = map.keySet().size() - 1;
		StringBuilder sb = new StringBuilder();
		for (Object key : map.keySet()) {
			if (i != last) {
				sb.append(key + ",");
			} else {
				sb.append(key);
			}
			i++;
		}
		return sb.toString();
	}

	public static <T> void printArr(T[] arr) {
		if (arr == null || arr.length <= 0) {
			return;
		}
		System.out.println("Array:");
		for (int i = 0; i < arr.length; i++) {
			System.out.println(String.format("[%s] %s", i, arr[i]));
		}
	}

	public static <T> String collection2string(Collection<T> arr) {
		if (arr == null || arr.size() <= 0) {
			return "empty collection";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Collection size:").append(arr.size()).append("\n");
		int i=0;
		for(T o:arr){
			sb.append(String.format("[%s] %s %n",i,o));
			i++;
		}
		return sb.toString();
	}

		/**
         * remove filterKeys from map,
         * thread-safe depend on map
         *
         * @param map
         * @param filterKeys void
         */
	public static <T> void filterMap(Map<String, T> map, String[] filterKeys) {
		if (isEmpty(filterKeys) || isEmpty(map)) {
			return;
		}
		for (String key : filterKeys) {
			map.remove(key);
		}
	}

	public static <T> Set<T> array2Set(T[] array) {
		if (CollectionUtil.isEmpty(array)) {
			return null;
		}
		Set<T> s = new HashSet<T>();
		for (T item : array) {
			s.add(item);
		}
		return s;
	}

	/**
	 * remove list 's first n element
	 *
	 * @param list list
	 * @param n    n to remove
	 * @return after removed list
	 */
	public static <T> List<T> removeListFirstN(List<T> list, int n) {
		if (CollectionUtil.isEmpty(list)) {
			return list;
		}
		if (list.size() < n) {
			return list;
		}
		Iterator<T> iterator = list.iterator();
		int i = 0;
		while (iterator.hasNext() && i < n) {
			iterator.next();
			iterator.remove();
			i++;
		}
		return list;

	}


	/**
	 * get two list's same element
	 * @param mainList
	 * @param otherList
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getDuplicateItemUseHash(List<T> mainList, List<T> otherList) {
		if (CollectionUtil.isEmpty(mainList) || CollectionUtil.isEmpty(otherList)) {
			return new LinkedList<>();
		}
		Set<T> otherSet = new HashSet<T>();
		List<T> res = new LinkedList<>();
		for (T item : otherList) {
			otherSet.add(item);
		}
		for (T item : mainList) {
			if (otherSet.contains(item)) {
				res.add(item);
			}
		}
		return res;
	}

	/**
	 * get the first duplicate item
	 * @param mainList
	 * @param otherList
	 * @param <T>
	 * @return
	 */
	public static <T> T getOneDuplicateItemUseHash(List<T> mainList, List<T> otherList){
		List<T> list = getDuplicateItemUseHash(mainList,otherList);
		if(CollectionUtil.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}


	public static List<String> castInt2String(List<Integer> list){
		List<String> res = new LinkedList<>();
		for(Integer i:list){
			res.add(String.valueOf(i));
		}
		return res;
	}

    public static Set<String> string2Set(String str){
        Set<String> s = new HashSet<>();
        if(StringUtil.isEmpty(str)){
            return s;
        }
        s.add(str);
        return s;
    }



}
