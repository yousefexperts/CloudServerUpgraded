package com.spring.insight.api.cases.service;


import co.insight.starter.firebase.creator.firebase.FirebaseTokenHolder;

public interface FirebaseService {

	FirebaseTokenHolder parseToken(String idToken);

}
