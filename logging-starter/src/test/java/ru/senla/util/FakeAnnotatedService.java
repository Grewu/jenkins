package ru.senla.util;

import ru.senla.annotation.Logging;

public interface FakeAnnotatedService {
    void  fakeMethod(String s);

    class FakeAnnotatedMethodService implements FakeAnnotatedService{

        @Logging
        @Override
        public void fakeMethod(String s) {
        }
    }

    @Logging
    class FakeAnnotatedClassService implements FakeAnnotatedService{

        @Override
        public void fakeMethod(String s) {

        }
    }
}
