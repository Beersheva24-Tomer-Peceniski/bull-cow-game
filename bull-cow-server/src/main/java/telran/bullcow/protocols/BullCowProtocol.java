package telran.bullcow.protocols;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import telran.bullcow.services.*;

public class BullCowProtocol implements Protocol {

    BullCowService service;

    public BullCowProtocol(BullCowService service) {
        this.service = service;
    }

    @Override
    public Response getResponse(Request request) {
        String requestType = request.requestType();
        String requestData = request.requestData();
        Response response = null;
        try {
            Method method = BullCowProtocol.class.getDeclaredMethod(requestType, String.class);
            method.setAccessible(true);
            response = (Response) method.invoke(this, requestData);
        } catch (NoSuchMethodException e) {
            response = new Response(ResponseCode.WRONG_TYPE, requestType + " Wrong type");
        } catch (InvocationTargetException e) {
            Throwable causeExc = e.getCause();
            String message = causeExc == null ? e.getMessage() : causeExc.getMessage();
            response = new Response(ResponseCode.WRONG_DATA, message);
        } catch (Exception e) {
            // only for finishing Server and printing out Exception full stack
            throw new RuntimeException(e);
        }
        return response;
    }

}