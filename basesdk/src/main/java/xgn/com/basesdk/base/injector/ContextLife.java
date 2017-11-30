package xgn.com.basesdk.base.injector;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by huluzi on 2017/8/10.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {
    String value();
}