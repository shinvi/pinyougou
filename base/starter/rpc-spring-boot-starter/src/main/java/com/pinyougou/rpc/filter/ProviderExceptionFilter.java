package com.pinyougou.rpc.filter;


import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 邱长海
 */
public class ProviderExceptionFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                Throwable exception = result.getException();
                logger.error("Got exception which called by " + RpcContext.getContext().getRemoteHost()
                        + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                        + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);

                if (exception instanceof RpcException) {
                    return result;
                }
                String msg = exception.getCause() == null ? exception.getMessage() : exception.getCause().getMessage();
                return new RpcResult(new RpcException(msg));
            }
            return result;
        } catch (Exception e) {
            logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                    + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e;
        }
    }
}
