package com.zkteam.discover.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionUtil {

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {

        return collection == null || collection.isEmpty();
    }

    /**
     * 返回集合的大小,null集合返回0
     *
     * @param collection
     * @return
     */
    public static int size(Collection<?> collection) {

        return collection == null ? 0 : collection.size();
    }

    /**
     * 返回map大小
     * @param map
     * @return
     */
    public static int size(Map map){

        return map == null ? 0 : map.size();
    }

    /**
     * 判断map是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map){

        return map == null ? true : map.isEmpty();
    }

    /**
     * 判断position是否在集合长度范围内
     *
     * @param collection
     * @param position
     * @return
     */
    public static boolean checkPosition(Collection<?> collection, int position) {

        return position >= 0 && position < size(collection);
    }

    /**
     * 获取过滤后的position
     *
     * @param collection
     * @param position
     * @return
     */
    public static int filterPosition(Collection<?> collection, int position) {

        if (isEmpty(collection))
            return 0;

        if (position < 0) {
            position = 0;
        } else if (position >= collection.size()) {
            position = collection.size() - 1;
        }

        return position;
    }

    /**
     * 做下标越界判断，如果越界返回null
     *
     * @param list
     * @param position
     * @param <T>
     * @return
     */
    public static <T> T getItem(List<T> list, int position) {

        return checkPosition(list, position) ? list.get(position) : null;
    }

    /**
     * 获取集合最后一个元素
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getLastItem(List<T> list) {

        return getItem(list, size(list) - 1);
    }

    /**
     * 清空集合
     *
     * @param list
     * @param <T>
     */
    public static <T> void clear(List<T> list) {

        if (!isEmpty(list))
            list.clear();
    }

    /**
     * 移除item
     *
     * @param list
     * @param item
     * @param <T>
     * @return
     */
    public static <T> boolean remove(List<T> list, T item) {

        if (!isEmpty(list) && item != null)
            return list.remove(item);
        else
            return false;
    }

    /**
     * 移除item
     *
     * @param list
     * @param position
     * @param <T>
     */
    public static <T> boolean remove(List<T> list, int position) {

        if (list != null && checkPosition(list, position)) {

            list.remove(position);
            return true;
        } else {

            return false;
        }
    }

    /**
     * 移除指定子集
     *
     * @param srcList
     * @param removeList
     * @param <T>
     */
    public static <T> void removeAll(List<T> srcList, List<T> removeList) {

        if (CollectionUtil.isEmpty(srcList) || CollectionUtil.isEmpty(removeList))
            return;

        srcList.removeAll(removeList);
    }

    /**
     * 将文本根据指定的字符串分割成字符串集合
     *
     * @param text
     * @param split
     * @return
     */
    public static ArrayList<String> splitText(String text, String split) {

        return splitText(text, split, false);
    }

    /**
     * 将文本根据指定的字符串分割成字符串集合
     *
     * @param text
     * @param split
     * @return
     */
    public static ArrayList<String> splitText(String text, String split, boolean filterEmpty) {

        ArrayList<String> list = new ArrayList<String>();

        if (TextUtil.isEmpty(text))
            return list;

        String[] array = text.split(TextUtil.filterNull(split));
        if (array == null || array.length == 0)
            return list;

        for (int i = 0; i < array.length; i++) {

            if (filterEmpty) {

                if (!TextUtil.isEmptyTrim(array[i]))
                    list.add(array[i]);
            } else {

                list.add(array[i]);
            }
        }

        return list;
    }

    /**
     * 根据集合拼成指定分隔符分隔的字符串
     *
     * @param texts
     * @param split
     * @return
     */
    public static String getText(List<String> texts, String split) {

        if (isEmpty(texts))
            return TextUtil.TEXT_EMPTY;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < texts.size(); i++) {

            if (i > 0)
                sb.append(split);

            sb.append(texts.get(i));
        }

        return sb.toString();
    }

    /**
     * 判断数组是否为空
     *
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {

        return array == null || array.length == 0;
    }

    /**
     * 返回数组的大小,null数组返回0
     *
     * @param array
     * @return
     */
    public static <T> int size(T[] array) {

        return array == null ? 0 : array.length;
    }

    /**
     * 判断position是否在数组长度范围内
     *
     * @param array
     * @param position
     * @return
     */
    public static <T> boolean checkPosition(T[] array, int position) {

        return position >= 0 && position < size(array);
    }

    /**
     * 做下标越界判断，如果越界返回null
     *
     * @param array
     * @param position
     * @param <T>
     * @return
     */
    public static <T> T getItem(T[] array, int position) {

        return checkPosition(array, position) ? array[position] : null;
    }

    /**
     * 截取子集，该函数会对集合和索引做边界判断
     * 如果endIndex超出长度，则会截取到末尾
     *
     * @param list
     * @param startIndex
     * @param endIndex
     * @param <T>
     * @return
     */
    public static <T> List<T> subList(List<T> list, int startIndex, int endIndex) {

        if (CollectionUtil.isEmpty(list))
            return list;

        if (startIndex < 0)
            startIndex = 0;

        if (startIndex > list.size())
            startIndex = list.size();

        if (endIndex < 0)
            endIndex = 0;

        if (endIndex > list.size())
            endIndex = list.size();

        if (startIndex > endIndex)
            startIndex = endIndex;

        return list.subList(startIndex, endIndex);
    }

    /**
     * 比较两个集合是否相同
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean equals(Collection c1, Collection c2) {

        if (c1 == null || c2 == null)
            return false;

        return c1.equals(c2);
    }

    /**
     * 循环
     *
     * @param data
     * @param target
     * @param handleAbort
     * @param lisn
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> boolean forLoop(List<? extends T> data, K target, boolean handleAbort, ForLoopListener<T, K> lisn) {

        if (isEmpty(data) || lisn == null)
            return false;

        boolean result = false;
        for (int i = 0; i < data.size(); i++) {

            if (lisn.onItem(i, data.get(i), target)) {

                result = true;
                if (handleAbort)
                    break;
            }
        }

        return result;
    }

    public static void addAll(List<Object> sources, List<?> addList) {

        if(CollectionUtil.isEmpty(addList))
            return;

        sources.addAll(addList);
    }

    public static interface ForLoopListener<T, K> {

        boolean onItem(int index, T item, K target);
    }
}
