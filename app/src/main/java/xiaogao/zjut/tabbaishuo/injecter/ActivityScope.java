package xiaogao.zjut.tabbaishuo.injecter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by huluzi on 2017/8/10.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}