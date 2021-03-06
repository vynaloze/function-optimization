package com.vynaloze.fo.dao;

import com.vynaloze.fo.Results;
import com.vynaloze.fo.functions.TestFunction;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    private final List<Results> resultsList = new ArrayList<>();

    public Results getResults(final Class<? extends TestFunction> function, final String algorithm) {
        for (final Results results : resultsList) {
            if (results.getFunction().equals(function)
                    && results.getAlgorithm().equals(algorithm)) {
                return results;
            }
        }
        return null;
    }

    public void putResults(final Results results) {
        resultsList.add(results);
    }
}
