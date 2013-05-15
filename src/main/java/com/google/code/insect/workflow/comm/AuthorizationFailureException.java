package com.google.code.insect.workflow.comm;

public class AuthorizationFailureException extends InternalWorkflowException {
    public AuthorizationFailureException(){
    	super("权限错误！");
    }
    
    public AuthorizationFailureException(String s){
    	super(s);
    }
}
