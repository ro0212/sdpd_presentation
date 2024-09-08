**DICE GAMES:**
------------------


**GROUP MEMBERS** :

_Rohit Pagar_    -   _2021B1A72139G_

_Unmesh Bhole_   -   _2021B1A71949G_

---------------------
**APPROACH TO SOLVE THE PROBLEM:** 


--------------------
**TESTING:**

A test-driven development approach was followed for building the application. We built the basic logic of our backend code first, then wrote local test cases in our `WalletTest.java` file to test our backend logic. For failing test cases, we continued to improve our code until all test cases passed.

_Local Tests:_

In the `WalletTest.java` class, we used Mockito. The Android `Log` class provides static methods like `Log.d()`, `Log.e()`, etc., for logging messages. When running unit tests on the JVM (locally), these static methods can cause issues because the Android framework is not available in this environment. Since `Log` is part of the Android SDK, calling its static methods in local tests can lead to crashes.

By using Mockito’s `mockStatic`, we can mock these static methods and prevent them from executing, while also ensuring that the tests do not depend on Android's logging framework. `logMock.close();` ensures that all calls to `Log` within the `WalletViewModel` are intercepted and do not interfere with the test results. After each test, you close the mock to prevent side effects in subsequent tests.

_Instrumented Tests:_

Once the logic of the backend was complete, we moved on to writing instrumented test cases to test our UI and backend integration. Instrumented tests were written in the `WalletInstrumentedTest.java` class. We started with basic tests, such as monitoring the number of clicks/rolls on the dice, and provided more than 30 predefined inputs to the model. We then checked for all required parameters such as total score, sixes scored, double sixes scored, etc.

We used `try (ActivityScenario<WalletActivity> scenario = ActivityScenario.launch(...))` to ensure that once the test completes (whether it passes or fails), the scenario is cleaned up correctly. `ActivityScenario` implements `AutoCloseable`, which means it can be used in a try-with-resources block to ensure that it is automatically closed when the block completes. This is important for managing the activity's lifecycle during the test and freeing up resources properly once the test is finished.

_Monkey Tests:_

Finally, after being satisfied with our model for local and instrumented tests, we stress-tested our model using the Monkey tool in the platform-tools of the SDK. Our model successfully handled 15,000 random inputs but crashed at 20,000 random inputs.

-------------------------------------
