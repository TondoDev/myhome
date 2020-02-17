package org.tondo.myhome.svc;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceUtils {

	
	public static <T, S>  List<T> iterableToDataObjectList(Iterable<S> iterable, Function<S, T> func) {
		return StreamSupport.stream(iterable.spliterator(), false)
			.map(func)
			.collect(Collectors.toList());
	}
	
	
	public static Double doubleNullAsZero(Double value) {
		return value == null ? 0.0  : value;
	}
}
