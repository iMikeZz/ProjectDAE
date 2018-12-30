/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author migue
 */
public enum State {
    ATIVA, INATIVA, SUSPENSA
    /*
    ATIVA("Ativa"), INATIVA("Inativa"), SUSPENSA("Suspensa");

    
    private final String state;

    private State(String state) {
        this.state = state;
    }

    public String getNameType() {
        return state;
    }

    public State getState(String state) {
        switch (state) {
            case "Ativa":
                return State.ATIVA;
            case "Inativa":
                return State.INATIVA;
            case "Suspensa":
                return State.SUSPENSA;
        }
        return null;
    }  
    */
}
