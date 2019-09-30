package com.spring.insight.api.cases.service.shared;


import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import com.spring.insight.api.cases.service.exception.FirebaseTokenException;
import com.spring.insight.api.cases.service.exception.FirebaseTokenExceptionMessages;
import com.spring.insight.api.cases.service.exception.FirebaseTokenInvalidException;
import com.spring.insight.api.cases.web.rest.util.StringUtil;

import co.insight.starter.firebase.creator.firebase.FirebaseTokenHolder;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.ExecutionException;


public class FirebaseParser {

    @Async
	public FirebaseTokenHolder parseToken(String idToken)  {
		if (StringUtil.isBlank(idToken)) {
			throw new FirebaseTokenException(FirebaseTokenExceptionMessages.TOKEN_HEADER_NOT_FOUND);
		}else{
            FirebaseToken decodedToken = null;
            try {
                if(idToken!=null)
                {
                    // todo catch token expired error
                    // TODO: 05.12.2017 catch token is not valid
                    decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get();
                }
                else
                {
                    throw new FirebaseException("Token is null");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (FirebaseException e) {
                e.printStackTrace();
            }
            return new FirebaseTokenHolder(decodedToken);
        }

	}
}
