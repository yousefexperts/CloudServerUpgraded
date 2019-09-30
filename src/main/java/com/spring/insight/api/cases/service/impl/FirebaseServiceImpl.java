package com.spring.insight.api.cases.service.impl;



import co.insight.starter.firebase.creator.firebase.FirebaseTokenHolder;

import org.springframework.stereotype.Service;

import com.spring.insight.api.cases.service.FirebaseService;
import com.spring.insight.api.cases.service.shared.FirebaseParser;

@Service
public class FirebaseServiceImpl implements FirebaseService {

	@Override
	public FirebaseTokenHolder parseToken(String firebaseToken) {
		return new FirebaseParser().parseToken(firebaseToken);
	}
}
