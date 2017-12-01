package xiaogao.zjut.tabbaishuo.main.activity.im.tool.textcolor;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AutoLinkTextView;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.imkit.widget.provider.VoiceMessageItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.VoiceMessage;
import xiaogao.zjut.tabbaishuo.R;

/**
 * Created by Administrator on 2017/12/1.
 */

@ProviderTag(
        messageContent = VoiceMessage.class,
        showReadState = true
)

public class VoiceMessageItemProviderNew extends VoiceMessageItemProvider {
    public VoiceMessageItemProviderNew(Context context) {
        super(context);
    }

    @Override
    public void bindView(View v, int position, VoiceMessage content, UIMessage message) {
        super.bindView(v, position, content, message);
        try {
            Class clazz = VoiceMessageItemProvider.class;
            Class innerClazz[] = clazz.getDeclaredClasses();
            for (Class cls : innerClazz) {
                int mod = cls.getModifiers();
                String modifier = Modifier.toString(mod);
                if (modifier.contains("static")) {
                    //构造静态内部类实例
                    Field field1 = cls.getDeclaredField("left");
                    Field field2 = cls.getDeclaredField("right");
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    Object t = v.getTag();
                    TextView left = (TextView) field1.get(t);
                    TextView right = (TextView) field2.get(t);
                    left.setTextColor(v.getContext().getResources().getColor(R.color.white));
                    right.setTextColor(v.getContext().getResources().getColor(R.color.color_282828));
                    TextPaint leftP = left.getPaint();
                    TextPaint rightP = right.getPaint();
                    leftP.setFakeBoldText(false);
                    rightP.setFakeBoldText(false);
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }  catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


}
