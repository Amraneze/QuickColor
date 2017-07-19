package fr.justgame.quickcolor.common;

/**
 * Created by aaitzeouay on 19/07/2017.
 */

public abstract class AbsInjectableFactory<T> {

    private T instance;

    protected abstract T createInstance();

    public T getInstance(){
        if(instance == null){
            instance = createInstance();
        }
        return instance;
    }

    public void injectInstance(T instance){
        this.instance = instance;
    }

}
