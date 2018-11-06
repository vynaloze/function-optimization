package com.vynaloze.fo;

import com.vynaloze.fo.functions.TestFunction;
import java.io.Serializable;

public class Response implements Serializable {
    private final Status status;
    private final Results results;
    private final TestFunction testFunction;

    public Response(final Status status, final Results results, final TestFunction testFunction) {
        this.status = status;
        this.results = results;
        this.testFunction = testFunction;
    }

    public Status getStatus() {
        return status;
    }

    public Results getResults() {
        return results;
    }

    public TestFunction getTestFunction() {
        return testFunction;
    }

    public enum Status implements Serializable {
        DROP("DROP"), INVALID("INVALID"), OK("OK");

        private final String status;

        Status(final String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
