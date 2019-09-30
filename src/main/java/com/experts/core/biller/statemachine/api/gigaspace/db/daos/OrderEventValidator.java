package com.experts.core.biller.statemachine.api.gigaspace.db.daos;

import org.openspaces.events.adapter.SpaceDataEvent;
import org.springframework.beans.factory.annotation.Required;

public class OrderEventValidator {

    private long workDuration = 100;

    private IAccountDataDAO accountDataDAO;
    
    @Required
    public void setAccountDataDAO(IAccountDataDAO accountDataDAO) {
		this.accountDataDAO = accountDataDAO;
	}


    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }


    @SpaceDataEvent
    public OrderEvent validatesOrderEvent(OrderEvent orderEvent) {

        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {

        }
     
        System.out.println("---[Validator: Validating orderEvent:"+orderEvent+" ]---");
        

        boolean isAccountFound = accountDataDAO.isAccountFound(orderEvent.getUserName()); 
        if (isAccountFound == true){

        	orderEvent.setStatus("Pending");
        	System.out.println("---[Validator: OrderEvent approved, status set to PENDING]---");	
        }
        else {

        	orderEvent.setStatus("AccountNotFound");
        	System.out.println("---[Validator: OrderEvent rejected, ACCOUNT NOT FOUND]---");
        }
        

    	orderEvent.setOrderID(null);
    	
        return orderEvent;
    }

    public void sayData(OrderEvent orderEvent) {
        System.out.println("+++[Saying: "+orderEvent+"]+++");
    }
}
