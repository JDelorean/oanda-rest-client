package pl.jdev.opes.rest;

public interface IRestService {
    default Object get(){
        return null;
    }
    default Object[] getAll(){
        return null;
    }
}
