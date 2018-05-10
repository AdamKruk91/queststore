package model;

import java.util.ArrayList;
import java.util.List;

public class IteratorImpl<T> implements Iterator {

    List<T> list = new ArrayList<T>();
    int index;

    public IteratorImpl(ArrayList<T> list){
        this.list = list;
    }
    public boolean hasNext(){

        if(index < list.size()){
            return true;
        }
        return false;

    }
    public T next(){
        if(this.hasNext()){
            return list.get(index++);
        }
        return null;
    }

}
