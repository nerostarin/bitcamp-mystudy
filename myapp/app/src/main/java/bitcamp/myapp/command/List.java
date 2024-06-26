package bitcamp.myapp.command;

//데이터 목록을 다루는 일을 할 객체의 사용법
// => 즉 객체에게 일을 시킬 때 다음의 메서드를 호출하여 일을 시켜라 라는 문법
public interface List {
    abstract void add(Object value);

    abstract Object remove(int index);

    abstract Object get(int index);

    abstract int indexOf(Object value);

    abstract Object[] toArray();

    abstract int size();
}
