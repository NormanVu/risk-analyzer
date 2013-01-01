package com.danielpacak.riskanalyzer.frontend.repository.google;

public interface Converter<F, T> {

	T convert(F f);

}
