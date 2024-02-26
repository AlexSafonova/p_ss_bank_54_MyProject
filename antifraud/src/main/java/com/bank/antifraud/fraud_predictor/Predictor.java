package com.bank.antifraud.fraud_predictor;

import com.bank.antifraud.util.TransferMock;

//Здесь, по идее, должен быть Machine Learning сервис, предсказывающий успешность платежа на основе данных,
//полученных от различных микросервисов. Но, по бедности, будем использовать маленький скрипт.
public interface Predictor {
    boolean predict(TransferMock transferMock);
}