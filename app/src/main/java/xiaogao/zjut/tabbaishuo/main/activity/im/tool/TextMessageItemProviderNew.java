package xiaogao.zjut.tabbaishuo.main.activity.im.tool;

import android.view.View;

import java.lang.reflect.Field;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AutoLinkTextView;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;
import xiaogao.zjut.tabbaishuo.R;

/**
 * Created by Administrator on 2017/11/30.
 */

@ProviderTag(
        messageContent = TextMessage.class,
        showReadState = true
)

public class TextMessageItemProviderNew extends TextMessageItemProvider{
    @Override
    public void bindView(View v, int position, TextMessage content, UIMessage data) {
        super.bindView(v, position, content, data);

        try {
            Class clazz = TextMessageItemProvider.class;
            Class cls[] = clazz.getDeclaredClasses();
            Field field1 = cls[0].getDeclaredField("message");
            field1.setAccessible(true);
            Object t = v.getTag();
            AutoLinkTextView autoLinkTextView = (AutoLinkTextView) field1.get(t);
            if(data.getMessageDirection() == Message.MessageDirection.SEND) {
                autoLinkTextView.setTextColor(v.getContext().getResources().getColor(R.color.white));
            }else{
                autoLinkTextView.setTextColor(v.getContext().getResources().getColor(R.color.color_282828));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }  catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
