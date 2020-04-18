package com.lazytester.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;

import java.util.Map;
import java.util.function.Function;

public interface BodyValidator extends Function<String, Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> {
}
