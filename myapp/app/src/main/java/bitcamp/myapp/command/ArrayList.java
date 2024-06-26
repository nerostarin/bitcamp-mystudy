package bitcamp.myapp.command;

import java.util.Arrays;

public class ArrayList {
    private static final int MAX_SIZE = 3;

    private Object[] list = new Object[MAX_SIZE];
    private int size = 0;

    public void add(Object obj) {
        //grow();//우리가 만든 증가 메서드
        //자바에서 제공하는 배열증가 메서드
        int oldSize = list.length;
        int newSize = oldSize + (oldSize >> 1);
        list = Arrays.copyOf(list, newSize);
        list[size++] = obj;
    }

    public Object remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Object deletedObj = list[index];
        for (int i = index + 1; i < size; i++) {
            list[i - 1] = list[i];
        }
        list[--size] = null;
        return deletedObj;
    }

    public Object[] toArray() {
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = list[i];
        }
        return arr;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < size; i++) {
            if (list[i] == obj) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return list[index];
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    private void grow() {
        if (size == list.length) {
            int oldSize = list.length;
            int newSize = oldSize + (oldSize >> 2);// 기존배열에 50%증가
            Object[] arr = new Object[newSize];
            for (int i = 0; i < list.length; i++) {
                arr[i] = list[i];
            }
            list = arr; //기존 주소를 버리고 새주소로 배열을변경
            System.out.println("배열의 크기가 증가되었습니다");
        }
    }
}
