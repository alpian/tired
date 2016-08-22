package com.ind.tired.matchers;

import com.ind.tired.functional.WebResponse;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;


public class HttpStatusMatcher extends TypeSafeDiagnosingMatcher<WebResponse> {
    private final int status;

    public HttpStatusMatcher(int status) {
        this.status = status;
    }

    public static HttpStatusMatcher hasStatus(int status) {
        return new HttpStatusMatcher(status);
    }

    @Override
    protected boolean matchesSafely(WebResponse actual, Description mismatchDescription) {
        int actualCode = actual.response.getStatusLine().getStatusCode();
        if (actualCode != status) {
            mismatchDescription.appendText("Expected status " + status + " not equal to " + actualCode);
            mismatchDescription.appendText("\nFull responseSchema:\n" + actual.content);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Status code " + status);
    }
}
