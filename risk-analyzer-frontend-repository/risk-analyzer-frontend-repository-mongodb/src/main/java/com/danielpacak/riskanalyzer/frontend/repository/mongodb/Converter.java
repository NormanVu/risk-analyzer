package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

public interface Converter<F, T> {

	T convert(F f);

}
