package com.experts.core.biller.statemachine.api.rovo.awsxray.config.mdc;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.TypeConversionException;
import org.apache.camel.impl.MDCUnitOfWork;
import org.apache.camel.spi.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.lang.invoke.MethodHandles;

public class CustomMDCUnitOfWork extends MDCUnitOfWork {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected final String[] headerNames;

    public CustomMDCUnitOfWork(Exchange exchange) {
        super(exchange);
        this.headerNames = new String[] { "SomeDefaultHeader" };
    }

    public CustomMDCUnitOfWork(Exchange exchange, final String[] headerNames) {
        super(exchange);
        this.headerNames = headerNames;
    }

    @Override
    public UnitOfWork newInstance(Exchange exchange) {
        return new CustomMDCUnitOfWork(exchange);
    }

    protected String[] getHeaderNames() {
        return headerNames;
    }

    private void addOptional(Exchange exchange, String headerName) {

        String optionalValue;
        try {
            optionalValue = exchange.getIn().getHeader(headerName, String.class);
            if (optionalValue != null) {
                MDC.put(headerName, optionalValue);
                LOG.trace("Adding header ({}={}) to MDC logging", headerName, optionalValue);
            }
        } catch (TypeConversionException e) {
            LOG.warn(e.getMessage(), e);
        }
    }


    @Override
    public AsyncCallback beforeProcess(Processor processor, Exchange exchange,
                                       AsyncCallback callback) {
        for (String headerName : getHeaderNames()) {
            addOptional(exchange, headerName);
        }

        return super.beforeProcess(processor, exchange, callback);
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (String headerName : getHeaderNames()) {
            strb.append(headerName);
            strb.append(";");
        }
        return "CustomMDCUnitOfWork [" + strb.toString() + "]";
    }

    @Override
    public void clear() {
        super.clear();
        for (String headerName : getHeaderNames()) {
            MDC.remove(headerName);
        }
    }
}
