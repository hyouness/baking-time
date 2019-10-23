package com.example.bakingtime.utilities;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Credit: https://github.com/udacity/AdvancedAndroid_TeaTime/blob/TESP.04-Solution-AddIdlingResourceMenuActivityTest/app/src/main/java/com/example/android/teatime/IdlingResource/SimpleIdlingResource.java
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback callback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        this.isIdleNow.set(isIdleNow);
        if (isIdleNow && callback != null) {
            Objects.requireNonNull(callback).onTransitionToIdle();
        }
    }
}
