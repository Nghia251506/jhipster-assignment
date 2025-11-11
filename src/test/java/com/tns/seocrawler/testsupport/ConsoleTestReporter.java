package com.tns.seocrawler.testsupport;

import java.util.Optional;
import org.junit.jupiter.api.extension.*;

/**
 * Extension dùng để log trạng thái từng test case ra console với icon:
 * ▶️ START, ✅ PASSED, ❌ FAILED, ⚪ SKIPPED
 *
 * Gắn vào test bằng @ExtendWith(ConsoleTestReporter.class)
 */
public class ConsoleTestReporter implements TestWatcher, BeforeTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String name = context.getDisplayName();
        System.out.println("▶️  START : " + name);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        String name = context.getDisplayName();
        System.out.println("✅ PASSED: " + name);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String name = context.getDisplayName();
        System.out.println("❌ FAILED: " + name + " → " + cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String name = context.getDisplayName();
        System.out.println("⚪ SKIPPED: " + name + reason.map(r -> " (" + r + ")").orElse(""));
    }
}
