package pl.jdev.oanda_rest_client.rest;

public interface IRestService {
    default Object get(){
        return null;
    }
    default Object[] getAll(){
        return null;
    }
}
