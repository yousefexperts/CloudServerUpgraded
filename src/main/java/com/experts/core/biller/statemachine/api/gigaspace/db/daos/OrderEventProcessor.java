
package com.experts.core.biller.statemachine.api.gigaspace.db.daos;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.AccountData;
import org.openspaces.events.adapter.SpaceDataEvent;

public class OrderEventProcessor {

    private long workDuration = 100;

    private IAccountDataDAO accountDataDAO;
    
    public void setAccountDataDAO(IAccountDataDAO accountDataDAO) {
		this.accountDataDAO = accountDataDAO;
	}

    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }


    @SpaceDataEvent
    public OrderEvent processOrderEvent(OrderEvent orderEvent) {

        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {

        }
        System.out.println("---[Processor: Processing orderEvent:"+orderEvent+" ]---");
        

        AccountData accountData = accountDataDAO.getAccountData(orderEvent.getUserName());
            	
        if (accountData != null) {
        	System.out.println("---[Processor: Found accountData matching the orderEvent: "+accountData+ "]---");	
        
        	if (orderEvent.getOperation() == OrderEvent.BUY_OPERATION) { 

        		
        		if (accountData.getBalance() >= orderEvent.getPrice()){	

        			accountData.setBalance(accountData.getBalance()-orderEvent.getPrice());
        			orderEvent.setStatus("Processed");
        			System.out.println("---[Processor: OrderEvent PROCESSED successfully!]---");

        			accountDataDAO.updateAccountData(accountData);
        		}
        		else {

        			orderEvent.setStatus("InsufficientFunds");
        			System.out.println("---[Processor: OrderEvent rejected due to INSUFFICIENT FUNDS]---");
        		}
        	}
        	else {

        		accountData.setBalance(accountData.getBalance()+orderEvent.getPrice());
        		orderEvent.setStatus("Processed");
        		System.out.println("---[Processor: OrderEvent PROCESSED successfully!]---");

        		accountDataDAO.updateAccountData(accountData);
        	}
        }

    	orderEvent.setOrderID(null);       
        
    	return orderEvent;
    }

    public void sayData(OrderEvent orderEvent) {
        System.out.println("+++[Saying: "+orderEvent);
    }
}
