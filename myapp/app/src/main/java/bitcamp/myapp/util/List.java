package bitcamp.myapp.util;

//데이터 목록을 다루는 일을 할 객체의 사용법
// => 즉 객체에게 일을 시킬 때 다음의 메서드를 호출하여 일을 시켜라 라는 문법
public interface List {
    void add(Object value);

    Object remove(int index);

    Object get(int index);

    int indexOf(Object value);

    Object[] toArray();

    int size();
}
