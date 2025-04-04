package br.edu.ifmg.produto.services.exceptions;

public class ResourceNotFound extends RuntimeException { //Nao obriga a tratar a excessao

    public ResourceNotFound(){
        super();
    }

    public ResourceNotFound(String message){
        super(message);
    }
}
