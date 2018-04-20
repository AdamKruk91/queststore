package model;

import java.util.ArrayList;
import java.util.List;

public class Iterator<T> implements IteratorInterface{

    List<T> list = new ArrayList<T>();
    int index;

    public Iterator(ArrayList<T> list){
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
