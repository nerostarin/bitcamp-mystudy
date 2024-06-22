package com.remind.command;

import com.remind.vo.Project;

public class ArrayList {
    private final int MAX_SIZE = 100;
    private  Object[] list = new Object[MAX_SIZE];
    private  int size = 0;

    public void add(Object object)
    {
        list[size++] = object;
    }

    public Object[] toArray()
    {
        Object[] arr = new Object[size];
        for (int i = 0 ; i < size; i++)
        {
            arr[i] = list[i];
        }
        return arr;
    }

    public Object remove(int index)
    {
        if(index < 0 || index >= size)
        {
            return null;
        }
        Object deleteObj = list[index];
        for (int i = index + 1 ; i < size; i++)
        {
            list[i-1] = list[i];
        }
        list[--size] = null;
        return deleteObj;
    }

    public int indexOf(Object obj)
    {
        for (int i = 0; i< size; i++)
        {
            if (obj == list[i])
            {
                return i;
            }
        }
        return -1;
    }

    public int size()
    {
        return size;
    }

    public Object get(int index)
    {
        if (index < 0 || index >= size) {
            return null;
        }
        return list[index];
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }
}
